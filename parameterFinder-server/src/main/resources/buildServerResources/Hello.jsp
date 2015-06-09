<html>
    <head>
        <script language="JavaScript">
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
            function saveParameter() {

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
                    var y = x[i].getElementsByTagName("regexList");
                    for (j=0;j<y.length;j++) {
                        document.getElementById('params').innerHTML += "<br>";
                        document.getElementById('params').innerHTML += "Regex: <input type='text' name='regex'"+j+" value='"+x[j].getElementsByTagName("regex")[0].childNodes[0].nodeValue+"' >";
                    }
                    document.getElementById('params').innerHTML += "<br><br>";
                }
            }
            function editParameter() {
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
                <input type="button" name="currentParameters" value="Current Parameters" onclick="loadParameters()">
                <input type="button" value="Save" name="saveButton" onclick="saveParameter()">
            </form>
        </div>
    </body>
</html>