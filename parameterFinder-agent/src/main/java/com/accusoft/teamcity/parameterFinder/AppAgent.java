package com.accusoft.teamcity.parameterFinder;

import jetbrains.buildServer.agent.AgentLifeCycleAdapter;

import java.io.PrintWriter;
import java.util.ArrayList;

public class AppAgent extends AgentLifeCycleAdapter {
    public AppAgent() {

    }
    public void pluginsLoaded () {

        PrintWriter w = null;
        try {
            w = new PrintWriter("C:\\Users\\mjones\\Desktop\\file.txt", "UTF-8");
            w.println("HELLO");
        }
        catch (Exception e) {
            w.println(e.getStackTrace());
        }
        finally {
            w.close();
        }

        ArrayList<String> regexes = new ArrayList<String>();

        if (System.getProperty("os.name").contains("Windows")) {
            regexes.clear();
            regexes.add("Python ([\\d\\.]+)");
            new ParameterFinder(regexes, "C:\\Python", "-V", "python.exe");
        }
        else {
            regexes.clear();
            regexes.add("python([\\d\\.]+)");
            new ParameterFinder(regexes, "/usr/share/python", "-i", "pyversions.py");
        }
    }

}