<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.microsoft.samples.federation.*"%>
<%@ page import="com.microsoft.samples.waad.federation.*"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Index Page</title>
</head>
<body>
	<h3>Welcome <%=ConfigurableFederatedLoginManager.fromRequest(request).getPrincipal().getName()%>!</h3>
	
	<h2>Claim list:</h2>
	<ul>
<%
	for (Claim claim : ConfigurableFederatedLoginManager.fromRequest(request).getClaims()) {
%>
        <li><%= claim.toString()%></li>
<% 	} %>
</ul>
</body>
</html>