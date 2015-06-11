<html>
    <head>
        <script language="JavaScript">
            window.onload = loadParameters;
            function newParameter()
            {
                tmp = 0;
                document.getElementById('params').innerHTML += "Tool: <input type='text' name='fld"+tmp+"' >";
                document.getElementById('params').innerHTML += "<br>";
                tmp = tmp+1;
                document.getElementById('params').innerHTML += "Location: <input type='text' name='fld"+tmp+"' >";
                document.getElementById('params').innerHTML += "<br>";
                tmp = tmp+1;
                document.getElementById('params').innerHTML += "File: <input type='text' name='fld"+tmp+"' >";
                document.getElementById('params').innerHTML += "<br>";
                tmp = tmp+1;
                document.getElementById('params').innerHTML += "Command: <input type='text' name='fld"+tmp+"' >";
                document.getElementById('params').innerHTML += "<br>";
                tmp = tmp+1;
                document.getElementById('params').innerHTML += "Regex: <input type='text' name='fld"+tmp+"' >";
                document.getElementById('params').innerHTML += "<br>";
                tmp = tmp+1;
                document.getElementById('params').innerHTML += "<br><br>";
            }
            function saveParameters() {

                <%@ page import=" org.w3c.dom.Document,org.w3c.dom.Element,javax.xml.parsers.DocumentBuilder,javax.xml.parsers.DocumentBuilderFactory,javax.xml.parsers.ParserConfigurationException,javax.xml.transform.Transformer,javax.xml.transform.TransformerException,javax.xml.transform.TransformerFactory,javax.xml.transform.dom.DOMSource,javax.xml.transform.stream.StreamResult,java.io.File" %>

                <%
                    try {
                        DocumentBuilderFactory docFactory = DocumentBuilderFactory.newInstance();
                        DocumentBuilder docBuilder = docFactory.newDocumentBuilder();
                        Document doc = docBuilder.newDocument();
                        Element rootElement = doc.createElement("parameter");
                        doc.appendChild(rootElement);
                        Element tool = doc.createElement("tool");
                        Element file = doc.createElement("file");
                        Element location = doc.createElement("location");
                        Element command = doc.createElement("command");
                        Element regex = doc.createElement("regex");

                        rootElement.appendChild(tool);
                        rootElement.appendChild(file);
                        rootElement.appendChild(location);
                        rootElement.appendChild(command);
                        rootElement.appendChild(regex);

                        TransformerFactory transformerFactory = TransformerFactory.newInstance();
                        Transformer transformer = transformerFactory.newTransformer();
                        DOMSource source = new DOMSource(doc);
                        StreamResult result = new StreamResult(new File("/plugins/parameterFinder/parameters.xml"));
                        transformer.transform(source, result);

                    } catch (ParserConfigurationException pce) {
                        pce.printStackTrace();
                    } catch (TransformerException tfe) {
                        tfe.printStackTrace();
                    }
                %>
            }
            function loadParameters()
            {
                var xmlDoc=loadXMLDoc("/plugins/parameterFinder/parameters.xml");
                var x = xmlDoc.getElementsByTagName("parameter");

                for (i=0;i<x.length;i++) {
                    document.getElementById('params').innerHTML += "Tool: <input type='text' name='input'"+i+" value='"+x[i].getElementsByTagName("tool")[0].childNodes[0].nodeValue+"' >";
                    document.getElementById('params').innerHTML += "<br>";
                    document.getElementById('params').innerHTML += "Path: <input type='text' name='path'"+i+" value='"+x[i].getElementsByTagName("location")[0].childNodes[0].nodeValue+"' >";
                    document.getElementById('params').innerHTML += "<br>";
                    document.getElementById('params').innerHTML += "File: <input type='text' name='file'"+i+" value='"+x[i].getElementsByTagName("file")[0].childNodes[0].nodeValue+"' >";
                    document.getElementById('params').innerHTML += "<br>";
                    document.getElementById('params').innerHTML += "Command: <input type='text' name='command'"+i+" value='"+x[i].getElementsByTagName("command")[0].childNodes[0].nodeValue+"' >";
                    document.getElementById('params').innerHTML += "<br>";
                    document.getElementById('params').innerHTML += "Regex: <input type='text' name='regex'"+i+" value='"+x[i].getElementsByTagName("regex")[0].childNodes[0].nodeValue+"' >";
                    document.getElementById('params').innerHTML += "<br><br>";
                }
            }

            function loadXMLDoc(filename)
            {
                if (window.XMLHttpRequest)
                {
                    xhttp=new XMLHttpRequest();
                }
                else // code for IE5 and IE6
                {
                    xhttp=new ActiveXObject("Microsoft.XMLHTTP");
                }
                xhttp.open("GET",filename,false);
                xhttp.send();
                return xhttp.responseXML;
            }
        </script>
    </head>
    <body>
        <div id="main">
            <div id="params" style="padding:30px;">

            </div>
            <br>
            <form name="form1" method="POST">
                <input type="button" name="newParameterBtn" value="New Parameter" onclick="newParameter()">
                <input type="button" value="Save" name="saveButton" onclick="saveParameters()">
            </form>
            <br>
        </div>
    </body>
</html>