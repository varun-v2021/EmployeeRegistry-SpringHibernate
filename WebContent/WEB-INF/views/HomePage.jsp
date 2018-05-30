<%@taglib uri="http://www.springframework.org/tags/form" prefix="form"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Home Page</title>
</head>
<body>
	<table>
		<tr>
			<td colspan="2">${message}</td>
		</tr>
		<tr>
		</tr>
		<tr>
		</tr>
		<tr>
		</tr>
		<tr>
			<td><a href="<%=request.getContextPath()%>/assign"> Assign
					Tasks</a></td>
		</tr>
		<tr>
			<td><a href="<%=request.getContextPath()%>/view">View Tasks</a></td>
		</tr>
	</table>
</body>
</html>