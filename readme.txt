Accusoft Teamcity Plugin


Purpose

  * This plug-in's purpose is to search for user defined tools and make agent parameters for them

  * For example, if you want to know which versions of Maven you have on a build machine, you might specify the following parameters for the plugin:

    * tool: Maven
    * regex: "Maven ([\d\.]+)"
    * location: "C:\Program Files\Maven"
    * file: "mvn.cmd"
    * command: "-version"

    The tool is the name of the tool that is being searched for.

    The regex is the regular expression to extract the version number from the command output.

    The location is the base dir to search, for example, if you specify "C:\Program Files\Java", the plug-in will recursively search all subdirectories for multiple versions of Java. If you leave this value blank, the program assumes that you only want to run a command. This means that the program will NOT create a TC agent parameter for the Tool_Path, only for the version number of the tool. If you want to create a parameter for the path, you MUST specify the location to look.

    The file is the name of the file to search for.

    The command is the command to run to find the version number.


How to edit parameters to search for

  * To edit which tools are searched for or edit any of the above parameters, you can edit the parameters.xml file in the location:

    *   TEAMCITY_DATA_DIRECTORY\plugins\parameterFinder.zip\server\parameterFinder-server-1.0-SNAPSHOT.jar\buildServerResources\parameters.xml

  * You will then need to save the file and update the JAR file (this is done automatically with a tool like 7ZIP).

  * After this file has been modified, you need to restart the build agents that you want to pick up the edited parameters.