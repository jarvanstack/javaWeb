<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title>ajax01</title>
</head>
<script src="${pageContext.request.contextPath}/js/jquery-3.5.1.js"></script>
<script>
    <!--01失去焦点验证-->
    function a01() {
        let username = document.getElementById("username").value;
        console.log(username);
        $.ajax({
            url:" ${pageContext.request.contextPath}/a01.do",
            type:"post",
            data:{username:username},
             success:function (data) {
                console.log(data);
                console.log(data.message);
                if (data.message=="true"){
                    alert("true");
                }else {
                    alert("false");
                }
            }
        });
    }


</script>
<body>
<!--01失去焦点验证-->
用户名:<input id="username" type="text" name="username">
<input type="button" onclick="a01()" value="启动">
</body>
</html>
