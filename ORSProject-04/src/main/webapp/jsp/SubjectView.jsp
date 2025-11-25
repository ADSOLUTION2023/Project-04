<%@page import="java.util.List"%>
<%@page import="in.co.rays.proj4.util.HTMLUtility"%>
<%@page import="in.co.rays.proj4.bean.SubjectBean"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.SubjectCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<title>Add Subject</title>

<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>
<body>

	<form action="<%=ORSView.SUBJECT_CTL%>" method="post">

		<%@ include file="Header.jsp"%>

		<jsp:useBean id="bean" class="in.co.rays.proj4.bean.SubjectBean"
			scope="request"></jsp:useBean>

		<%
			List<SubjectBean> courseList = (List<SubjectBean>) request.getAttribute("courseList");
		%>

		<div align="center">

			<h1 align="center" style="margin-bottom: -15; color: navy">
				<%
					if (bean != null && bean.getId() > 0) {
				%>
				Update
				<%
					} else {
				%>
				Add
				<%
					}
				%>
				Subject
			</h1>

			<div style="height: 15px; margin-bottom: 12px">
				<H3 align="center">
					<font color="red"> <%=ServletUtility.getErrorMessage(request)%>
					</font>
				</H3>

				<H3 align="center">
					<font color="green"> <%=ServletUtility.getSuccessMessage(request)%>
					</font>
				</H3>
			</div>

			<!-- Hidden fields -->
			<input type="hidden" name="id" value="<%=bean.getId()%>"> <input
				type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
			<input type="hidden" name="modifiedBy"
				value="<%=bean.getModifiedBy()%>"> <input type="hidden"
				name="createdDatetime"
				value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

			<table>

				<!-- Subject Name -->
				<tr>
					<th align="left">Subject Name<span style="color: red">*</span></th>
					<td><input type="text" name="name"
						placeholder="Enter Subject Name"
						value="<%=DataUtility.getStringData(bean.getName())%>"></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("name", request)%>
					</font></td>
				</tr>

				<!-- Description -->
				<tr>
					<th align="left">Description<span style="color: red">*</span></th>
					<td><input type="text" name="description"
						placeholder="Enter Description"
						value="<%=DataUtility.getStringData(bean.getDescription())%>">
					</td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("description", request)%>
					</font></td>
				</tr>

				<!-- Course Id -->
				<tr>
					<th align="left">Course<span style="color: red">*</span></th>
					<td><%=HTMLUtility.getList("courseId", String.valueOf(bean.getCourseId()), courseList)%></td>
					<td style="position: fixed;"><font color="red"> <%=ServletUtility.getErrorMessage("courseId", request)%></font></td>
				</tr>

				<!-- Form Buttons -->
				<tr>
					<th></th>
					<%
						if (bean != null && bean.getId() > 0) {
					%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=SubjectCtl.OP_UPDATE%>"> <input
						type="submit" name="operation" value="<%=SubjectCtl.OP_CANCEL%>">
					</td>
					<%
						} else {
					%>
					<td align="left" colspan="2"><input type="submit"
						name="operation" value="<%=SubjectCtl.OP_SAVE%>"> <input
						type="submit" name="operation" value="<%=SubjectCtl.OP_RESET%>">
					</td>
					<%
						}
					%>
				</tr>

			</table>

		</div>

	</form>

	<%@ include file="Footer.jsp"%>

</body>
</html>