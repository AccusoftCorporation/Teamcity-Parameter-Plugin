package com.accusoft.teamcity.parameterFinder;

import jetbrains.buildServer.agent.AgentLifeCycleAdapter;
import jetbrains.buildServer.agent.AgentLifeCycleListener;
import jetbrains.buildServer.agent.BuildAgent;
import jetbrains.buildServer.agent.BuildAgentConfiguration;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.util.EventDispatcher;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.*;
import org.w3c.dom.html.HTMLCollection;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.regex.Matcher;


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

            ArrayList<String> locations = new ArrayList<String>();

            for (int i = 0; i < nList.getLength(); i++) {
                Node nNode = nList.item(i);
                locations.clear();
                if (nNode.getNodeType() == Node.ELEMENT_NODE) {
                    Element eElement = (Element) nNode;
                    String toolName = (eElement.getElementsByTagName("tool").item(0).getTextContent());
                    NodeList list = eElement.getElementsByTagName("location");

                    for (int j = 0; j < list.getLength(); j++) {
                        String location = (list.item(j).getTextContent());
                        if (location != null && location.compareTo("") != 0 && location.compareTo(" ") != 0 && location.compareTo("null") != 0)
                            location = location.replaceAll("[/\\\\]+", Matcher.quoteReplacement(System.getProperty("file.separator")));
                        locations.add(location);
                    }
                    String file = (eElement.getElementsByTagName("file").item(0).getTextContent());
                    String command = (eElement.getElementsByTagName("command").item(0).getTextContent());
                    String regex = (eElement.getElementsByTagName("regex").item(0).getTextContent());

                    new ParameterFinder(toolName, regex, locations, command, file, this);

                }
            }
            log(s);
        }
        catch (Exception e) {
            buildLogString(e.toString());
            log(s);
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