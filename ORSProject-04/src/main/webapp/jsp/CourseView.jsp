<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
pageEncoding="ISO-8859-1"%>

<%@page import="in.co.rays.proj4.util.*,in.co.rays.proj4.bean.*,in.co.rays.proj4.controller.*"%>

<!DOCTYPE html>
<html>
<head>
<title>Add Course</title>
</head>

<body>

	<%@ include file="Header.jsp"%>

	<%
		CourseBean bean = (CourseBean) request.getAttribute("bean");
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
			Course
		</h1>

		<!-- SUCCESS MESSAGE -->
		<h4 style="color: green;">
			<%=ServletUtility.getSuccessMessage(request)%>
		</h4>

		<!-- ERROR MESSAGE -->
		<h4 style="color: red;">
			<%=ServletUtility.getErrorMessage(request)%>
		</h4>

		<form action="<%=ORSView.COURSE_CTL%>" method="post">

			<input type="hidden" name="id"
				value="<%=bean != null ? bean.getId() : 0%>"> <input
				type="hidden" name="createdBy"
				value="<%=bean != null ? bean.getCreatedBy() : ""%>"> <input
				type="hidden" name="modifiedBy"
				value="<%=bean != null ? bean.getModifiedBy() : ""%>"> <input
				type="hidden" name="createdDatetime"
				value="<%=bean != null ? DataUtility.getTimestamp(bean.getCreatedDatetime()) : ""%>">
			<input type="hidden" name="modifiedDatetime"
				value="<%=bean != null ? DataUtility.getTimestamp(bean.getModifiedDatetime()) : ""%>">

			<table align="center">

				<!-- Course Name -->
				<tr>
					<th>Course Name :</th>
					<td><input type="text" name="name" placeholder="Enter Course Name"
						value="<%=bean != null ? DataUtility.getStringData(bean.getName()) : ""%>">
					</td>
					<td><font color="red"><%=ServletUtility.getErrorMessage("name", request)%></font></td>
				</tr>
				<!-- Duration -->
				<tr>
					<th>Duration :</th>
					<td><input type="text" name="duration" placeholder="Enter Duration"
						value="<%=bean != null ? DataUtility.getStringData(bean.getDuration()) : ""%>">
					</td>
					<td><font color="red"><%=ServletUtility.getErrorMessage("duration", request)%></font></td>
				</tr>
				<!-- Description -->
				<tr>
					<th>Description :</th>
					<td><input type="text" name="description" placeholder="Enter Description"
						value="<%=bean != null ? DataUtility.getStringData(bean.getDescription()) : ""%>">
					</td>
					<td><font color="red"><%=ServletUtility.getErrorMessage("description", request)%></font></td>
				</tr>

				<!-- Buttons -->
				<tr>
					<td colspan="3" align="center">
						<%
							if (bean != null && bean.getId() > 0) {
						%> <input type="submit" name="operation"
						value="<%=CourseCtl.OP_UPDATE%>"> <input type="submit"
						name="operation" value="<%=CourseCtl.OP_CANCEL%>"> <%
 					} else {
						 %> <input type="submit" name="operation" value="<%=CourseCtl.OP_SAVE%>">
						<input type="submit" name="operation"
						value="<%=CourseCtl.OP_RESET%>">
						 <%
 						}
						 %>
					</td>
				</tr>

			</table>
		</form>

	</div>

	<%@ include file="Footer.jsp"%>

</body>
</html>