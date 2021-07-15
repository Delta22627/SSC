<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>Home Page</title>
<body>
<h2>
    Hello ${username}
</h2>
${users}
<%--// let get jstl to work--%>
<%--    <c:forEach var="user" begin="1" end="${users}">--%>
<%--        Item <c:out value="${user.username}"/><p></p>--%>
<%--    </c:forEach>--%>
</body>
</html>