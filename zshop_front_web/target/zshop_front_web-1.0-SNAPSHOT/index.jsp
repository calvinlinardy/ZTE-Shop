<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<head>
    <title></title>
</head>
<body>
<%
    request.getRequestDispatcher("/front/product/findByParams").forward(request,response);
%>

</body>
</html>
