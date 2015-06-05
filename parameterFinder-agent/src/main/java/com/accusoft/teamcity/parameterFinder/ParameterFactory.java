package com.accusoft.teamcity.parameterFinder;

import java.io.PrintWriter;
import java.util.ArrayList;

public class ParameterFactory
{
    public ParameterFactory() {

    }
    public static void main(String[] args) {

        PrintWriter w = null;
        System.err.println("ASKDJNASKJASNDKJASNSAJNJDNASADSNKJSANSJKADSN");
        try {
            w = new PrintWriter("C:\\Users\\mjones\\Desktop\\file.txt", "UTF-8");
            w.println("hi");
        }
        catch (Exception e) {
            w.println(e.getStackTrace());
        }
        finally {
            w.close();
        }

        ArrayList<String> regexes = new ArrayList<String>();
/*
        regexes.clear();
        regexes.add("Gradle ([\\d\\.]+)");
        new ParameterFinder(regexes, "C:\\Program Files\\Gradle", "-version", "gradle.bat");
*/
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
