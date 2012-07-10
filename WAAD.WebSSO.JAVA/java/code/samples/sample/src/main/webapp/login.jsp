<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ page import="com.microsoft.samples.waad.federation.*"%>

<!DOCTYPE html>
<html>
<head>
	<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
	<title>Login Page</title>
</head>
<body>
	<h3>Login Page</h3>
	<ul>
		<%
			TrustedIssuersRepository repository = new TrustedIssuersRepository();
			for (TrustedIssuer trustedIssuer : repository.getTrustedIdentityProviderUrls()) {
		%>
		<li><a href="<%=trustedIssuer.getLoginURL(request.getParameter("returnUrl"))%>"><%=trustedIssuer.getDisplayName()%></a></li>
		<%
			}
		%>
	</ul>
</body>
</html>