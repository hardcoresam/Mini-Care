<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://struts.apache.org/tags-html" prefix="html"%>
<%@taglib uri="http://struts.apache.org/tags-bean" prefix="bean"%>

<!DOCTYPE html>
<html>
<body>
<h1 align="center">Job Title - ${listOfApplications[0].job.title}</h1>
<hr><br><br>

<table border="1" align="center">

<tr>
<th>Sitter Name</th>
<th>Experience</th>
<th>Expected Pay</th>
<th>Message</th>
</tr>

<c:forEach items="${requestScope.listOfApplications}" var="application">

<tr>

<td>${application.sitter.firstName}</td>
<td>${application.sitter.experience}</td>
<td>${application.expectedPay}</td>

<form action="${pageContext.request.contextPath}/member/sendMessage.do" method="POST">
    <input type = "hidden" name = "sitterId" value = "${application.sitter.memberId}" >   <!-- Check if this should be sitterId or memberId only-->
    <td>
        <input type= "submit" value= "Message Him">
    </td>
</form>

</tr>

</c:forEach>

</table>
</body>
</html>