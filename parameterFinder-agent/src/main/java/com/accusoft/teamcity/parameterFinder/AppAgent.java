package com.accusoft.teamcity.parameterFinder;

import jetbrains.buildServer.agent.AgentLifeCycleAdapter;
import jetbrains.buildServer.agent.AgentLifeCycleListener;
import jetbrains.buildServer.agent.BuildAgent;
import jetbrains.buildServer.agent.BuildAgentConfiguration;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.util.EventDispatcher;
import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
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
        log(s);
        conf.addEnvironmentVariable("Python", "3.4.3");
    }
    public void log(StringBuilder s) {
        Loggers.AGENT.info(s.toString());
    }
    public void buildLogString(String s) {
        this.s.append(s);
    }

}