<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>Home Page</title>
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js" integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM" crossorigin="anonymous"></script>

<body>
<div class="container mt-4">
    <h2>
        Hello ${username}
    </h2>
    <table class="table table-striped table-bordered">
        <thead>
        <tr>
            <th class="py-3">Id</th>
            <th class="py-3">Username</th>
            <th class="py-3">Display Name</th>
            <th class="py-3">Actions</th>

        </tr>
        </thead>
        <tbody>
        <c:forEach var="user" items="${users}">
            <tr>
                <td class="py-3">${user.id}</td>
                <td class="py-3">${user.username}</td>
                <td class="py-3">${user.displayName}</td>
                <td>
                    <button class="btn btn-warning btn-sm" type="button">Edit</button>
                    <button class="btn btn-danger btn-sm" type="button">Delete</button>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>