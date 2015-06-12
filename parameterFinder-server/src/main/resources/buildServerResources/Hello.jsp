<html>
    <head>
        <script language="JavaScript">
            window.onload = loadParameters;

            function loadParameters()
            {
                var xmlDoc=loadXMLDoc("/plugins/parameterFinder/parameters.xml");
                var x = xmlDoc.getElementsByTagName("parameter");

                for (i=0;i<x.length;i++) {
                    document.getElementById('params').innerHTML += "Tool: "+x[i].getElementsByTagName("tool")[0].childNodes[0].nodeValue;
                    document.getElementById('params').innerHTML += "<br>";
                    document.getElementById('params').innerHTML += "Path: "+x[i].getElementsByTagName("location")[0].childNodes[0].nodeValue;
                    document.getElementById('params').innerHTML += "<br>";
                    document.getElementById('params').innerHTML += "File: "+x[i].getElementsByTagName("file")[0].childNodes[0].nodeValue;
                    document.getElementById('params').innerHTML += "<br>";
                    document.getElementById('params').innerHTML += "Command: "+x[i].getElementsByTagName("command")[0].childNodes[0].nodeValue;
                    document.getElementById('params').innerHTML += "<br>";
                    document.getElementById('params').innerHTML += "Regex: "+x[i].getElementsByTagName("regex")[0].childNodes[0].nodeValue;
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
        </div>
    </body>
</html>