
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.controller.LoginCtl"%>
<%@page import="in.co.rays.proj4.controller.UserRegistrationCtl"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="java.util.HashMap"%>
<%@page import="java.util.Map"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Login</title>
</head>
<body>
	<%@ include file="Header.jsp"%>
	<form action="<%=ORSView.LOGIN_CTL%>" method="post">

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.UserBean"
			scope="request"></jsp:useBean>

		<div align="center">
			<h1>Login</h1>
			<h2 style="color: green"><%=ServletUtility.getSuccessMessage(request)%></h2>
			<h4 style="color: red"><%=ServletUtility.getErrorMessage(request)%></h2>
			
			<input type="hidden" name="id" value="<%=bean.getId()%>"> 
			
			<input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>"> 
			<input type="hidden" name="createdDatetime" value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime" value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">
			<table>
				<tr>
					<th>Login ID:</th>
					<td><input type="text" name="login" placeholder="Enter Login Id"
						value="<%=DataUtility.getStringData(bean.getLogin())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("login", request)%></font></td>
				</tr>
				<tr>
					<th>Password:</th>
					<td><input type="text" name="password" placeholder="Enter Password"
						value="<%=DataUtility.getStringData(bean.getPassword())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("password", request)%></font></td>
				</tr>
				<tr>
					<td></td>
					<td><input type="submit" name="operation"
						value="<%=LoginCtl.OP_SIGN_IN%>"> <input type="submit"
						name="operation" value="<%=LoginCtl.OP_SIGN_UP%>"></td>
				</tr>
				<tr>
					<th></th>
					<td><a href="<%=ORSView.FORGET_PASSWORD_CTL%>"><b>Forget my password?</b></a>&nbsp;</td>
				</tr>
			</table>
		</div>
	</form>
</body>
</html>