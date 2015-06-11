package com.accusoft.teamcity.parameterFinder;

import jetbrains.buildServer.agent.AgentLifeCycleAdapter;
import jetbrains.buildServer.agent.AgentLifeCycleListener;
import jetbrains.buildServer.agent.BuildAgent;
import jetbrains.buildServer.agent.BuildAgentConfiguration;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.util.EventDispatcher;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.net.URLConnection;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;


public class AppAgent extends AgentLifeCycleAdapter {
    private StringBuilder s = null;
    Map<String, String> values = new HashMap<String, String>();
    public AppAgent(@NotNull EventDispatcher<AgentLifeCycleListener> dispatcher) {
        s = new StringBuilder();
        dispatcher.addListener(this);
    }

    @Override
    public void agentInitialized(@NotNull final BuildAgent agent) {
        BuildAgentConfiguration conf = agent.getConfiguration();

        readXMLFile(conf.getServerUrl());
        createAgentParameters(conf);
    }
    private void readXMLFile(String serverURL) {
        try {
            URL url = new URL(serverURL + "/plugins/parameterFinder/parameters.xml");
            URLConnection connection = url.openConnection();

            DocumentBuilderFactory dbFactory = DocumentBuilderFactory.newInstance();
            DocumentBuilder dBuilder = dbFactory.newDocumentBuilder();
            Document doc = dBuilder.parse(connection.getInputStream());

            doc.getDocumentElement().normalize();

            NodeList nList = doc.getElementsByTagName("parameter");

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);

                if (nNode.getNodeType() == Node.ELEMENT_NODE) {

                    Element eElement = (Element) nNode;

                    String toolName = (eElement.getElementsByTagName("tool").item(0).getTextContent());
                    String location = (eElement.getElementsByTagName("location").item(0).getTextContent());
                    String file = (eElement.getElementsByTagName("file").item(0).getTextContent());
                    String command = (eElement.getElementsByTagName("command").item(0).getTextContent());
                    String regex = (eElement.getElementsByTagName("regex").item(0).getTextContent());

                    new ParameterFinder(toolName, regex, location, command, file, this);
                }
            }
        }
        catch (Exception e) {
            StringBuilder sb = new StringBuilder();
            sb.append(e.getMessage());
            log(sb);
        }
    }
    private void createAgentParameters(BuildAgentConfiguration conf) {
        Iterator it = values.entrySet().iterator();

        while (it.hasNext()) {
            Map.Entry pair = (Map.Entry)it.next();
            conf.addEnvironmentVariable(pair.getKey().toString(), pair.getValue().toString());
            it.remove();
        }
    }
    public void log(StringBuilder s) {
        Loggers.AGENT.info(s.toString());
    }
    public void buildLogString(String s) {
        this.s.append(s);
    }

}