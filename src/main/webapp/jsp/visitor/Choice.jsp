<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<!DOCTYPE html>
<html lang="en">
<head>
<meta charset="utf-8">
<meta name="viewport" content="width=device-width, initial-scale=1">
<link rel="stylesheet" href="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/css/bootstrap.min.css">
  <script src="https://ajax.googleapis.com/ajax/libs/jquery/3.3.1/jquery.min.js"></script>
  <script src="https://maxcdn.bootstrapcdn.com/bootstrap/3.3.7/js/bootstrap.min.js"></script>
</head>

<body>

<div class="container">
  <div class="jumbotron">
    <h1 align="center">Welcome</h1>
    <h3 align="center">Choose your Type</h1>
  </div>
</div>

<form action="${pageContext.request.contextPath}/visitor/register.do" method="POST">

<div class="container">
<div class="row">
<div class="col-xs-4 col-xs-offset-4">

<br><br>
<font color="red"><div align="center">${errorType}</div></font>
<br><br>

<div align="center"><input type = "submit" name="type" value="seeker" class="btn btn-default"></div>
<br><br><br><br>

<div align="center"><input type = "submit" name="type" value="sitter" class="btn btn-default"></div>

</div>
</div>
</div>

</form>
</body>
</html>