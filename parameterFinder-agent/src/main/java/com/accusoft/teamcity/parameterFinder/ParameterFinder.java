package com.accusoft.teamcity.parameterFinder;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParameterFinder {
    ArrayList<String> search = new ArrayList<String>();
    String file_Separator = null;
    AppAgent a = null;
    String tool = null;
    String fileFound = "";
    String command = null;
    String file = null;
    ArrayList<String> locations = new ArrayList<String>();

    public ParameterFinder(String tool, String regex, ArrayList<String> locations, String command, String file, AppAgent a) {

        this.a = a;
        this.tool = tool;
        this.file_Separator = File.separator;
        this.command = command;
        this.locations = locations;
        this.file = file;

        this.a.buildLogString("\n\t\tTOOL: " + tool + "\n");

        for (String location : this.locations) {
            if (location == null || location.compareTo("null") == 0) {
                if (runCommand(null, this.command, regex)) {
                    break;
                }
            } else {
                findSearches(location);
                logSearches();

                if (search.size() > 0)
                    searchForTool(search, this.file, this.command, regex);
                else {
                    this.a.buildLogString("\t\tCannot find tool in location provided: " + location + "\n");
                }
            }
        }
    }
    private void logSearches() {
        for (String s : search) {
            a.buildLogString("\t\tSearch location: " + s + "\n");
        }
    }
    protected boolean searchForTool(ArrayList<String> location, String f, String command, String regex) {
        ArrayList<String> filesToRun = new ArrayList<String>();
        boolean returnVal = false;

        for (String s : location) {
            findFiles(new File(s), f, filesToRun);
        }

        if (filesToRun.size() == 0) {
            a.buildLogString("\t\tCannot find file: " + f + " in search locations\n");
            returnVal = false;
        }
        else {
            for (String filename : filesToRun) {
                File file = new File(filename);
                if (command != null && command.compareTo("") != 0 && command.compareTo(" ") != 0 && command.compareTo("null") != 0) {
                    returnVal = runCommand(file, command, regex);
                } else {
                    try {
                        Scanner in = new Scanner(new FileReader(file));
                        StringBuilder output = new StringBuilder();
                        while (in.hasNext()) {
                            output.append(in.next());
                        }
                        fileFound = file.getParent();
                        returnVal = performRegex(regex, output);
                    } catch (FileNotFoundException e) {
                        e.printStackTrace();
                    }
                }
            }
        }
        return returnVal;
    }
    private boolean runCommand(File file, String command, String regex){
        boolean returnVal = false;
        try {
            String s;
            StringBuilder output = new StringBuilder();
            List<String> commands = new ArrayList<String>();
            BufferedReader stdInput;

            if (file != null) {
                fileFound = file.getParent();
                commands.add(file.getPath());
                commands.add(command);
                ProcessBuilder pb = new ProcessBuilder(commands);
                pb.redirectErrorStream(true);
                Process p = pb.start();
                stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));
            }
            else {
                Runtime rt = Runtime.getRuntime();
                Process pr = rt.exec(command);
                stdInput = new BufferedReader(new InputStreamReader(pr.getInputStream()));
            }
            while ((s = stdInput.readLine()) != null) {
                output.append(s);
            }
            a.buildLogString("\t\tCommand output: " + output + "\n");
            returnVal = performRegex(regex, output);
        }
        catch (IOException e) {
            a.buildLogString("\t\t" + e.getMessage() + "\n");
        }
        catch (Exception e) {
            a.buildLogString("\t\t" + e.getMessage() + "\n");
        }
        return returnVal;
    }
    private boolean performRegex(String regex, StringBuilder output) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(output.toString());
        if (m.find()) {
            a.buildLogString("\t\tVersion: " + m.group(1) + "\n");
            a.values.put(tool + m.group(1), m.group(1));
            if (fileFound.length() > 1)
                a.values.put(tool + m.group(1) + "_Path", fileFound);
            return true;
        }
        else {
            a.buildLogString("\t\tRegex: " + regex + " did not return any results from the command output. Please review the regex.\n");
            return false;
        }

    }
    private void findFiles(File current, String f, ArrayList<String> list) {

        if (current == null || f == null) return;

        if (current.isDirectory()) {
            for (File file : current.listFiles()) {
                findFiles(file, f, list);
            }
        }
        else if (current.isFile() && current.getName().equals(f)) {
            list.add(current.getPath());
        }
    }
    private void findSearches(String s) {

        String directory = s.substring(0, s.lastIndexOf(file_Separator) + 1);
        String value = s.substring(s.lastIndexOf(file_Separator) + 1);
        File f = new File(directory);
        if (f.exists()) {
            File[] files = f.listFiles();

            for (int i = 0; i < files.length; i++) {
                String val = "";
                String path = files[i].getPath();

                if (path.contains(value)) {
                    String end = path.substring(path.lastIndexOf(file_Separator) + 1);
                    for (int j = 0; j < end.length(); j++) {
                        val += end.charAt(j);
                    }
                    search.add(directory + val);
                }
            }
        }
    }
}