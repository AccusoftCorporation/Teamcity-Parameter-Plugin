<html>
    <body>
        <div id="main">
            <div id="params" style="padding:30px;">

            </div>
            <br>
            <form name="form1" method="POST">
                <input type="button" name="newParameterBtn" value="New Parameter" onclick="newParameter()">
                <input type="button" name="showParameters" value="Current Parameters" onclick="loadParameters()">

            </form>
        </div>
        <script language="JavaScript">
            function loadParameters()
            {
                XmlDocument doc = new XmlDocument();
                doc.lo
                xmlDoc=loadXMLDoc("./parameters.xml");
                x = xmlDoc.getElementsByTagName("tool");
                document.getElementById('params').innerHTML += "Toolabc: "+x+" >";
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
            function newParameter()
            {
                tmp = 0;
                document.getElementById('params').innerHTML += "Tool: <input type='text' name='fld"+tmp+"' >";
                tmp = tmp+1;
                document.getElementById('params').innerHTML += "&nbsp;&nbsp;Location: <input type='text' name='fld"+tmp+"' >";
                tmp = tmp+1;
                document.getElementById('params').innerHTML += "&nbsp;&nbsp;File: <input type='text' name='fld"+tmp+"' >";
                tmp = tmp+1;
                document.getElementById('params').innerHTML += "&nbsp;&nbsp;Command: <input type='text' name='fld"+tmp+"' >";
                tmp = tmp+1;
                document.getElementById('params').innerHTML += "&nbsp;&nbsp;Regex: <input type='text' name='fld"+tmp+"' >";
                tmp = tmp+1;
                document.getElementById('params').innerHTML += "&nbsp;&nbsp; <input type='button' value='Save' name='btn"+tmp+"' >"
                document.getElementById('params').innerHTML += "<br><br>"
            }
        </script>
    </body>
</html>