<html>
    <body>
        <div id="main">
            <div id="params" style="padding:30px;">

            </div>
            <br>
            <form name="form1" method="POST">
                <input type="button" name="newParameterBtn" value="New Parameter" onclick="newParameter()">
            </form>
        </div>
        <script language="JavaScript">
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