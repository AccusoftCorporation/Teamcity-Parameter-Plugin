package com.accusoft.teamcity.parameterFinder;

import jetbrains.buildServer.agent.AgentLifeCycleAdapter;
import jetbrains.buildServer.agent.AgentLifeCycleListener;
import jetbrains.buildServer.agent.BuildAgent;
import jetbrains.buildServer.log.Loggers;
import jetbrains.buildServer.util.EventDispatcher;
import org.jetbrains.annotations.NotNull;

import java.io.PrintWriter;
import java.util.ArrayList;

public class AppAgent extends AgentLifeCycleAdapter {
    StringBuilder s = null;
    public AppAgent(@NotNull EventDispatcher<AgentLifeCycleListener> dispatcher) {
        s = new StringBuilder();
        dispatcher.addListener(this);
    }

    @Override
    public void agentInitialized(@NotNull final BuildAgent agent) {
    }

    public void pluginsLoaded () {
        ArrayList<String> regexes = new ArrayList<String>();

        if (System.getProperty("os.name").contains("Windows")) {
            regexes.clear();
            regexes.add("Python ([\\d\\.]+)");
            new ParameterFinder("Python", regexes, "C:\\Python", "-V", "python.exe", this);
        }
        else {
            regexes.clear();
            regexes.add("python([\\d\\.]+)");
            new ParameterFinder("Python", regexes, "/usr/share/python", "-i", "pyversions.py", this);
        }
        log(s);
    }
    public void log(StringBuilder s) {
        Loggers.AGENT.info(s.toString());
    }
    public void buildLogString(String s) {
        this.s.append(s);
    }

}