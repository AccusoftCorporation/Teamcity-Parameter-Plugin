package com.accusoft.teamcity.parameterFinder;

import jetbrains.buildServer.agent.AgentLifeCycleAdapter;
import jetbrains.buildServer.agent.AgentLifeCycleListener;
import jetbrains.buildServer.agent.BuildAgent;
import jetbrains.buildServer.agent.BuildAgentConfiguration;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.util.EventDispatcher;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.w3c.dom.Document;
import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import java.io.File;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
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
        ArrayList<String> regexes = new ArrayList<String>();

        readXMLFile(regexes);

        /*
        if (System.getProperty("os.name").contains("Windows")) {
            regexes.clear();
            regexes.add("Python ([\\d\\.]+)");
            new ParameterFinder("Python", regexes, "C:\\Python", "-V", "python.exe", this);

            regexes.clear();
            regexes.add("version ([\\d\\.]+)");
            new ParameterFinder("Ant", regexes, "C:\\Program Files\\Ant", "-version", "ant.bat", this);

            regexes.clear();
            regexes.add("Maven ([\\d\\.]+)");
            new ParameterFinder("Maven", regexes, "C:\\Program Files\\Maven", "-version", "mvn.cmd", this);

            regexes.clear();
            regexes.add("Gradle ([\\d\\.]+)");
            new ParameterFinder("Gradle", regexes, "C:\\Program Files\\Gradle", "-version", "gradle.bat", this);

            regexes.clear();
            regexes.add("([a-z\\d]+)");
            new ParameterFinder("Android", regexes, "C:\\AndroidSDK", null, "RELEASE.TXT", this);
        }
        else {
            regexes.clear();
            regexes.add("python([\\d\\.]+)");
            new ParameterFinder("Python", regexes, "/usr/share/python", "-i", "pyversions.py", this);
        }
        */
        log(s);
        createAgentParameters(conf);
    }
    private void readXMLFile(ArrayList<String> regexes) {
        try {
            URL url = new URL("http://localhost:85/plugins/parameterFinder/parameters.xml");
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

                    String toolName = ("Tool : " + eElement.getElementsByTagName("tool").item(0).getTextContent());
                    String location = ("Location : " + eElement.getElementsByTagName("location").item(0).getTextContent());
                    String file = ("File : " + eElement.getElementsByTagName("file").item(0).getTextContent());
                    String command = ("Command : " + eElement.getElementsByTagName("command").item(0).getTextContent());
                    String regex = ("Regex : " + eElement.getElementsByTagName("regex").item(0).getTextContent());
                    regexes.add(regex);

                    StringBuilder sb = new StringBuilder();
                    sb.append(toolName + " " + location + " " + file + " " + command + " " + regex);
                    log(sb);
                    new ParameterFinder("Python", regexes, "C:\\Python", "-V", "python.exe", this);
                    //new ParameterFinder(toolName, regexes, location, command, file, this);
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