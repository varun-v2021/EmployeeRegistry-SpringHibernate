<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>View Tasks</title>
</head>
<body>
<c:set var="contextPath" value="${pageContext.request.contextPath}"/>
		<form:form method="GET"
		action="${contextPath}/viewSelectedTasks"
		modelAttribute="userForm">
		<table>
			<tr>
			<td><h4>View Tasks</h4></td>
			</tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr></tr>
			<tr>
				<td><form:label path="project">Filter by Project</form:label></td>
				<td><form:select path="project" name="project"
						onchange="this.form.submit()" multiple="single">
						<form:option value="" label="Select" />
						<form:options items="${projectNames}" />
					</form:select></td>
			</tr>
			<tr>
				<td>
					<ul>
						<c:forEach items="${taskList}" var="item">
							<tr>
								<td></td>
							<tr>
								<td></td>
							<tr>
							<tr>
								<td><h3>Project :</h3></td>
								<td><h3>${item.projectName}</h3></td>
							</tr>
							<tr>
								<td>Task Description :</td>
								<td>${item.description}</td>
							</tr>
							<tr>
								<td>Task Start Date :</td>
								<td>${item.startDate}</td>
							</tr>
							<tr>
								<td>Task End Date :</td>
								<td>${item.dueDate}</td>
							</tr>
							<tr>
								<td>
									<table border="1">
										<c:forEach items="${item.employeeList}" var="emp">
											<tr bordercolor="black" style="border: medium;">
												<td><h4>MID</h4></td>
												<td><h4>Employee Name</h4></td>
											</tr>
											<tr bordercolor="black" style="padding: 2px">
												<td>${emp.mid}</td>
												<td>${emp.employeeName}</td>
											</tr>
										</c:forEach>
									</table>
								</td>
							</tr>
						</c:forEach>
					</ul>
				</td>
			</tr>
			<tr>
				<td><a href="${contextPath}/Home"><h3>BACK</h3></a></td>
			</tr>
		</table>
	</form:form>
</body>
</html>