<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="java.util.Collections"%>
<%@page import="java.text.DecimalFormat"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.MarkSheetListCtl"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.bean.MarkSheetBean"%>
<%@page import="java.util.List"%>
<%@page import="java.util.Iterator"%>
<html>
<head>
<title>MarkSheet List</title>
<link rel="icon" type="image/png"
	href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />
</head>
<body>
	<%@include file="Header.jsp"%>
	<jsp:useBean id="bean" class="in.co.rays.proj4.bean.MarkSheetBean" scope="request"></jsp:useBean>
	<div align="center">
		<h1 align="center" style="margin-bottom: -15; color: navy;">MarkSheet
			List</h1>

		<div style="height: 15px; margin-bottom: 12px">
			<h3>
				<font color="red"><%=ServletUtility.getErrorMessage(request)%></font>
			</h3>
			<h3>
				<font color="green"><%=ServletUtility.getSuccessMessage(request)%></font>
			</h3>
		</div>

		<form action="<%=ORSView.MARKSHEET_LIST_CTL%>" method="post">
			<%
				int pageNo = ServletUtility.getPageNo(request);
				int pageSize = ServletUtility.getPageSize(request);
				int index = ((pageNo - 1) * pageSize) + 1;
				int nextListSize = DataUtility.getInt(request.getAttribute("nextListSize").toString());

				List<MarkSheetBean> list = (List<MarkSheetBean>) ServletUtility.getList(request);
				Iterator<MarkSheetBean> it = list.iterator();

				if (list.size() != 0) {
			%>

			<input type="hidden" name="pageNo" value="<%=pageNo%>"> <input
				type="hidden" name="pageSize" value="<%=pageSize%>">

			<table style="width: 100%">
				<tr>
					<td align="center"><label><b>Name :</b></label> <input
						type="text" name="name" placeholder="Enter Student Name"
						value="<%=ServletUtility.getParameter("name", request)%>">&emsp;

						<label><b>Roll No :</b></label> <input type="text" name="rollNo"
						placeholder="Enter Roll No."
						value="<%=ServletUtility.getParameter("rollNo", request)%>">&emsp;

						<input type="submit" name="operation"
						value="<%=MarkSheetListCtl.OP_SEARCH%>"> &nbsp; <input
						type="submit" name="operation"
						value="<%=MarkSheetListCtl.OP_RESET%>"></td>
				</tr>
			</table>
			<br>

			  <table border="1" style="width: 100%; border: groove;">
                <tr style="background-color: #e1e6f1e3;">
                    <th width="5%"><input type="checkbox" id="selectall" /></th>
                    <th width="5%">S.No</th>
                    <th width="10%">Roll No.</th>
                    <th width="10%">Student Id</th>
                    <th width="50%">Name</th>
                    <th width="30%">Physics</th>
                    <th width="30%">Chemistry</th>
                    <th width="30%">Maths</th>
                    <th width="5%">Edit</th>
                </tr>

                <%
                    while (it.hasNext()) {
                        bean = (MarkSheetBean) it.next();
                %>
                <tr>
                    <td style="text-align: center;">
                        <input type="checkbox" class="case" name="ids" value="<%=bean.getId()%>">
                    </td>
                    <td style="text-align: center;"><%=index++%></td>
                    <td style="text-align: center; text-transform: capitalize;"><%=bean.getRollNo()%></td>
                     <td style="text-align: center; text-transform: capitalize;"><%=bean.getStudentId()%></td>
                      <td style="text-align: center; text-transform: capitalize;"><%=bean.getName()%></td>
                       <td style="text-align: center; text-transform: capitalize;"><%=bean.getPhysics()%></td>
                        <td style="text-align: center; text-transform: capitalize;"><%=bean.getChemistry()%></td>
                         <td style="text-align: center; text-transform: capitalize;"><%=bean.getMaths()%></td>
                    <td style="text-align: center;">
                        <a href="MarkSheetCtl?id=<%=bean.getId()%>">Edit</a>
                    </td>
                </tr>
                <%
                    }
                %>
            </table>


			<table style="width: 100%">
				<tr>
					<td style="width: 25%"><input type="submit" name="operation"
						value="<%=MarkSheetListCtl.OP_PREVIOUS%>"
						<%=pageNo > 1 ? "" : "disabled"%>></td>
					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=MarkSheetListCtl.OP_NEW%>"></td>
					<td align="center" style="width: 25%"><input type="submit"
						name="operation" value="<%=MarkSheetListCtl.OP_DELETE%>">
					</td>
					<td style="width: 25%" align="right"><input type="submit"
						name="operation" value="<%=MarkSheetListCtl.OP_NEXT%>"
						<%=nextListSize != 0 ? "" : "disabled"%>></td>
				</tr>
			</table>

			<%
				}
				if (list.size() == 0) {
			%>
			<table>
				<tr>
					<td align="right"><input type="submit" name="operation"
						value="<%=MarkSheetListCtl.OP_BACK%>"></td>
				</tr>
			</table>
			<%
				}
			%>
		</form>
	</div>
	<%@include file="Footer.jsp"%>
</body>
</html>