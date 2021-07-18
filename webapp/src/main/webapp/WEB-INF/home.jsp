<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<html>
<title>Home Page</title>
<link href="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/css/bootstrap.min.css" rel="stylesheet"
      integrity="sha384-EVSTQN3/azprG1Anm3QDgpJLIm9Nao0Yz1ztcQTwFspd3yD65VohhpuuCOmLASjC" crossorigin="anonymous">
<link rel="stylesheet" href="https://cdnjs.cloudflare.com/ajax/libs/font-awesome/4.7.0/css/font-awesome.min.css">
<script src="https://cdn.jsdelivr.net/npm/bootstrap@5.0.2/dist/js/bootstrap.bundle.min.js"
        integrity="sha384-MrcW6ZMFYlzcLA8Nl+NtUVF0sA7MsXsP1UyJoMp4YLEuNSfAP+JcXn/tWtIaxVXM"
        crossorigin="anonymous"></script>

<body>
<div class="container mt-4">
    <nav class="navbar navbar-light bg-light">
        <div class="container-fluid">
            <a class="navbar-brand">SSC- Login Webapp</a>
            <a class="btn btn-light pull-right" type="button" href="/logout">Logout <i
                    class="fa fa-times-rectangle-o"></i></a>

        </div>
    </nav>
    <div class="row">
        <div class="col-12">
            <h2>
                Welcome ${username}
            </h2>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <h3>
                <c:if test="${not empty message}">
                    <c:choose>
                        <c:when test="${hasError}">
                            <div class="alert alert-danger" role="alert">
                                    ${message}
                            </div>
                        </c:when>
                        <c:otherwise>
                            <div class="alert alert-success" role="alert">
                                    ${message}
                            </div>
                        </c:otherwise>
                    </c:choose>


                </c:if>
            </h3>
        </div>
    </div>
    <div class="row">
        <div class="col-12">
            <h3>
                <a class="btn btn-success" type="button" href="/user/create">Create User <i
                        class="fa fa-plus"></i></a>
            </h3>
        </div>
    </div>
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
                    <a class="btn btn-warning btn-sm" type="button" href="/user/edit?username=${user.username}">Edit Display Name &nbsp;<i class="fa fa-pencil"></i>
                    </a>
                    <a class="btn btn-info btn-sm" type="button" href="/user/password?username=${user.username}">Edit Password &nbsp;<i class="fa fa-pencil"></i>
                    </a>
                    <c:if test="${currentUser.username != user.username}">
                    <button type="button"
                            class="btn btn-danger btn-sm"
                            data-bs-toggle="modal"
                            data-bs-target="#delete-modal-${user.id}"
                    >Delete &nbsp;<i class="fa fa-remove"></i>
                    </button>
                    <!-- Modal -->
                    <div class="modal fade" id="delete-modal-${user.id}" tabindex="-1" role="dialog"
                         aria-labelledby="exampleModalLabel" aria-hidden="true">
                        <div class="modal-dialog">
                            <div class="modal-dialog">
                                <div class="modal-content">
                                    <div class="modal-header">
                                        <h5 class="modal-title" id="exampleModalLabel">Confirm Delete?</h5>
                                        <button type="button" class="btn-close" data-bs-dismiss="modal"
                                                aria-label="Close"></button>
                                    </div>
                                    <div class="modal-body">
                                        Confirm deleting <b>${user.displayName} (${user.username})</b>?
                                    </div>
                                    <div class="modal-footer">
                                        <button type="button" class="btn btn-secondary" data-bs-dismiss="modal">Close
                                        </button>
                                        <a type="button" class="btn btn-danger"
                                           href="/user/delete?username=${user.username}"
                                        >Delete &nbsp;<i class="fa fa-remove"></i></a>
                                    </div>
                                </div>
                            </div>
                        </div>
                        </c:if>
                </td>
            </tr>
        </c:forEach>
        </tbody>
    </table>
</div>
</body>
</html>
