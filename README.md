Accusoft Teamcity Plugin


Purpose

  * This plug-in's purpose is to search for user defined tools and make agent parameters for them.

  * For example, if you want to know which versions of Maven you have on a build machine, you might specify the following parameters for the plugin:

    * tool: Maven
    * regex: "Maven ([\d\.]+)"
    * location: "C:\Program Files\Maven"
    * file: "mvn.cmd"
    * command: "-version"

    The tool is the name of the tool that is being searched for.

    The regex is the regular expression to extract the version number from the command output.

    The location is the base dir to search, for example, if you specify "C:\Program Files\Java", the plug-in will recursively search all subdirectories for multiple versions of Java. For example, if there was a location: "C:\Program Files\Java\jdk1.6" and "C:\Program Files\Java\jdk1.7", it would search both locations for a "java.exe" file to find both versions and add both parameters to the build agent.  If you leave this value blank, or "null", the program assumes that you only want to run a command. This means that the program will NOT create a TC agent parameter for the Tool_Path, only for the version number of the tool. If you want to create a parameter for the path, you MUST specify the location to look and a file to look for.

    The file is the name of the file to search for. This value is also to be left blank or "null" if the intention is to run a command only and not search for the files on the file system.

    The command is the command to run to find the version number. This will be added to the file to make the complete command "mvn.cmd -version" unless the file/location is null in the parameters.xml file.


How to edit parameters

  * To edit which tools are searched for or edit any of the above parameters, you can edit the parameters.xml file in the location:

    * TEAMCITY_DATA_DIRECTORY\plugins\parameterFinder.zip\server\parameterFinder-server-1.0-SNAPSHOT.jar\buildServerResources\parameters.xml

  * You will then need to save the file and update the JAR file (this is done automatically with a tool like 7ZIP).

  * After this file has been modified, you need to restart the build agents that you want to pick up the edited parameters and they will create the new parameters automatically.


How to build locally

  * Clone repository to local machine and navigate to the base directory of the project and run "mvn package". This will create a ZIP file in the "base/target/" directory that you drop into the server plugins folder.