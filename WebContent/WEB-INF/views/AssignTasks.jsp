<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<style>
.error {
	color: red;
	font-weight: bold;
}
</style>
<script
	src="http://ajax.googleapis.com/ajax/libs/jquery/2.0.2/jquery.min.js"></script>
<script type="text/javascript">
	function ajaxCallToBackend() {
		$.ajax({
			type : "post",
			url : "${pageContext.request.contextPath}" + "/populateEmployees",
			cache : false,
			data : 'description=' + $("#description").val() + "&projectName="
					+ $("#project").val(),
			success : function(response) {
				var dataLen = response.length;
				$("#employeeSelect option").remove();
				var options = '';

				$.each(response, function(index, item) {
					options += '<option value="' + item + '">' + item
							+ '</option>';
					$("#employeeSelect").html(options);
				});
			},
			error : function() {
				alert('Error in ajax response...');
			}
		});
	}
</script>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Assign Tasks</title>
</head>
<body>
	<c:set var="contextPath" value="${pageContext.request.contextPath}" />
	<h2>Assign Tasks</h2>
	<form:form method="POST" action="${contextPath}/addEmployee"
		modelAttribute="userForm">
		<table>
			<tr>
				<td class="error">${message}</td>
			</tr>
			<tr>
				<td><form:label path="project">Project*</form:label></td>
				<td><form:select path="project" name="project"
						multiple="single" onchange="ajaxCallToBackend();">
						<form:options items="${projectList}" />
					</form:select></td>
				<td><form:errors path="project" cssClass="error" /></td>
			</tr>
			<tr>
				<td><form:label path="description">Description*</form:label></td>
				<td><form:input path="description" name="description" /></td>
				<td><form:errors path="description" cssClass="error" /></td>
			</tr>
			<tr>
				<td><form:label path="startDate">Start Date of Task[dd-mm-yyyy]*</form:label></td>
				<td><form:input path="startDate" name="startDate" /></td>
				<td><form:errors path="startDate" cssClass="error" /></td>
			</tr>
			<tr>
				<td><form:label path="dueDate">Due Date of Task[dd-mm-yyyy]*</form:label></td>
				<td><form:input path="dueDate" name="dueDate" /></td>
				<td><form:errors path="dueDate" cssClass="error" /></td>
			</tr>
			<tr>
				<td><form:label path="employee">Who should do this?*</form:label></td>
				<td><form:select path="employee" name="employee"
						id="employeeSelect" multiple="multiple">
						<form:options items="${employeeList}" id="listOFEmployee" />
					</form:select></td>
				<td><form:errors path="employee" cssClass="error" /></td>
			</tr>
			<tr>
				<td><input type="submit" name="button" value="Add a task" /></td>
				<td><input type="submit" name="button" value="Cancel" /></td>
			</tr>
		</table>
	</form:form>
</body>
</html>