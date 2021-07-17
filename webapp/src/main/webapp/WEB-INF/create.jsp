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
            <a class="navbar-brand" href="/">SSC- Login Webapp</a>
            <a class="btn btn-light pull-right" type="button" href="/logout">Logout <i
                    class="fa fa-times-rectangle-o"></i></a>

        </div>
    </nav>

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
    <div class="row justify-content-md-center h-100">
        <div class="col-sm-12 col-md-6 col-lg-4 mt-5">
                <h1 class="text-center">Create New User</h1>
                <p>${error}</p>
                <form action="/user/create" method="post" autocomplete="off">
                    <div class="input-group mb-3 input-group-md">
                    <span class="input-group-text" id="username" style="width: 40px">
                        <i class="fa fa-user"></i></span>
                        <input type="text" class="form-control" placeholder="Username" aria-label="Username" aria-describedby="username" name="username">
                    </div>
                    <div class="input-group mb-3 input-group-md">
                    <span class="input-group-text" id="displayName" style="width: 40px">
                        <i class="fa fa-user"></i></span>
                        <input type="text" class="form-control" placeholder="Display Name" aria-label="Display name" aria-describedby="displyName" name="displayName">
                    </div>
                    <div class="input-group mb-3 input-group-md">
                    <span class="input-group-text" id="password" style="width: 40px">
                        <i class="fa fa-key"></i></span>
                        <input type="password" class="form-control" placeholder="Password" aria-label="Password" aria-describedby="password" name="password">
                    </div>
                    <div class="input-group mb-3 input-group-md">
                    <span class="input-group-text" id="cpassword" style="width: 40px">
                        <i class="fa fa-key"></i></span>
                        <input type="password" class="form-control" placeholder="Confirm Password" aria-label="Confirm Password" aria-describedby="cpassword" name="cpassword">
                    </div>
                    <div class="d-grid gap-2">
                        <button class="btn btn-success" type="submit"><i class="fa fa-plus"></i> &nbsp; Create</button>
                    </div>
                </form>
        </div>
    </div>
</div>
</body>
</html>
