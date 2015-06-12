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
    String location = null;

    public ParameterFinder(String tool, String regex, String s, String command, String file, AppAgent a) {
        this.a = a;
        this.tool = tool;
        this.file_Separator = File.separator;
        this.command = command;
        this.location = s.replaceAll("[/\\\\]+", Matcher.quoteReplacement(System.getProperty("file.separator")));
        this.file = file;

        this.a.buildLogString("\n\t\tTOOL: " + tool + "\n");
        findSearches(location);
        logSearches();

        if (search.size() > 0)
            searchForTool(search, this.file, this.command, regex);
        else {
            this.a.buildLogString("\t\tCannot find tool in location provided: " + location + "\n");
        }

    }
    private void logSearches() {
        for (String s : search) {
            a.buildLogString("\t\tSearch location: " + s + "\n");
        }
    }
    protected void searchForTool(ArrayList<String> location, String f, String command, String regex) {
        ArrayList<String> filesToRun = new ArrayList<String>();
        for (String s : location) {
            findFiles(new File(s), f, filesToRun);
        }

        for (String filename : filesToRun) {
            File file = new File(filename);
            if (command != null && command.compareTo("") != 0 && command.compareTo(" ") != 0 && command.compareTo("null") != 0) {
                runCommand(file, command, regex);
            }
            else {
                try {
                    Scanner in = new Scanner(new FileReader(file));
                    StringBuilder output = new StringBuilder();
                    while (in.hasNext()) {
                        output.append(in.next());
                    }
                    fileFound = file.getParent();
                    performRegex(regex, output);
                } catch (FileNotFoundException e) {
                    e.printStackTrace();
                }
            }
        }
    }
    private void runCommand(File file, String command, String regex){
        try {
            String s;
            fileFound = file.getParent();
            StringBuilder output = new StringBuilder();

            List<String> commands = new ArrayList<String>();
            commands.add(file.getPath());
            commands.add(command);
            ProcessBuilder pb = new ProcessBuilder(commands);
            pb.redirectErrorStream(true);
            Process p = pb.start();
            BufferedReader stdInput = new BufferedReader(new InputStreamReader(p.getInputStream()));

            while ((s = stdInput.readLine()) != null) {
                output.append(s);
            }
            a.buildLogString("\t\tCommand output: " + output + "\n");
            performRegex(regex, output);
        }
        catch (IOException e) {
            a.buildLogString(e.getMessage());
        }
        catch (Exception e) {
            a.buildLogString(e.getMessage());
        }
    }
    private void performRegex(String regex, StringBuilder output) {
        Pattern p = Pattern.compile(regex);
        Matcher m = p.matcher(output.toString());
        if (m.find()) {
            a.buildLogString("\t\tVersion: " + m.group(1) + "\n");
            a.values.put(tool + m.group(1), m.group(1));
            a.values.put(tool + m.group(1) + "_Path", fileFound);
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