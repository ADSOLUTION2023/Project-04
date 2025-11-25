<%@page import="in.co.rays.proj4.bean.CourseBean"%>
<%@page import="in.co.rays.proj4.util.ServletUtility"%>
<%@page import="in.co.rays.proj4.util.DataUtility"%>
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<%@page import="in.co.rays.proj4.controller.CourseCtl"%>
<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>

<!DOCTYPE html>
<html>
<head>
<title>Add College</title>

<link rel="icon" type="image/png"
    href="<%=ORSView.APP_CONTEXT%>/img/logo.png" sizes="16x16" />

</head>
<body>

<form action="<%=ORSView.COURSE_CTL%>" method="post">

    <%@ include file="Header.jsp"%>

    <jsp:useBean id="bean" class="in.co.rays.proj4.bean.CourseBean"
        scope="request"></jsp:useBean>

    <div align="center">

        <h1 align="center" style="margin-bottom: -15; color: navy">
            <% if (bean != null && bean.getId() > 0) { %>
                Update
            <% } else { %>
                Add
            <% } %>
            Course
        </h1>

        <div style="height: 15px; margin-bottom: 12px">
            <H3 align="center">
                <font color="red">
                    <%=ServletUtility.getErrorMessage(request)%>
                </font>
            </H3>

            <H3 align="center">
                <font color="green">
                    <%=ServletUtility.getSuccessMessage(request)%>
                </font>
            </H3>
        </div>

        <!-- Hidden fields -->
        <input type="hidden" name="id" value="<%=bean.getId()%>">
        <input type="hidden" name="createdBy" value="<%=bean.getCreatedBy()%>">
        <input type="hidden" name="modifiedBy" value="<%=bean.getModifiedBy()%>">
        <input type="hidden" name="createdDatetime"
            value="<%=DataUtility.getTimestamp(bean.getCreatedDatetime())%>">
        <input type="hidden" name="modifiedDatetime"
            value="<%=DataUtility.getTimestamp(bean.getModifiedDatetime())%>">

        <table>

            <!-- Course Name -->
            <tr>
                <th align="left">College Name<span style="color: red">*</span></th>
                <td>
                    <input type="text" name="name" placeholder="Enter College Name"
                        value="<%=DataUtility.getStringData(bean.getName())%>">
                </td>
                <td style="position: fixed;">
                    <font color="red">
                        <%=ServletUtility.getErrorMessage("name", request)%>
                    </font>
                </td>
            </tr>

            <!-- Address -->
            <tr>
                <th align="left">Address<span style="color: red">*</span></th>
                <td>
                    <input type="text" name="address" rows="3" placeholder="Enter Address "
                        value="<%=DataUtility.getStringData(bean.getDescription())%>">
                </td>
                <td style="position: fixed;">
                    <font color="red">
                        <%=ServletUtility.getErrorMessage("address", request)%>
                    </font>
                </td>
            </tr>

            <!-- State -->
            <tr>
                <th align="left">State<span style="color: red">*</span></th>
                <td>
                    <input type="text" name="state" placeholder="Enter State"
                        value="<%=DataUtility.getStringData(bean.getDuration())%>">
                </td>
                <td style="position: fixed;">
                    <font color="red">
                        <%=ServletUtility.getErrorMessage("state", request)%>
                    </font>
                </td>
            </tr>
             <!-- City -->
            <tr>
                <th align="left">City<span style="color: red">*</span></th>
                <td>
                    <input type="text" name="city" placeholder="Enter City"
                        value="<%=DataUtility.getStringData(bean.getDuration())%>">
                </td>
                <td style="position: fixed;">
                    <font color="red">
                        <%=ServletUtility.getErrorMessage("city", request)%>
                    </font>
                </td>
            </tr>
             <!-- Phone No. -->
            <tr>
                <th align="left">State<span style="color: red">*</span></th>
                <td>
                    <input type="text" name="phoneNo" placeholder="Enter Phone No."
                        value="<%=DataUtility.getStringData(bean.getDuration())%>">
                </td>
                <td style="position: fixed;">
                    <font color="red">
                        <%=ServletUtility.getErrorMessage("phoneNo", request)%>
                    </font>
                </td>
            </tr>
            

            <!-- Form Buttons -->
            <tr>
                <th></th>
                <% if (bean != null && bean.getId() > 0) { %>
                    <td align="left" colspan="2">
                        <input type="submit" name="operation" value="<%=CourseCtl.OP_UPDATE%>">
                        <input type="submit" name="operation" value="<%=CourseCtl.OP_CANCEL%>">
                    </td>
                <% } else { %>
                    <td align="left" colspan="2">
                        <input type="submit" name="operation" value="<%=CourseCtl.OP_SAVE%>">
                        <input type="submit" name="operation" value="<%=CourseCtl.OP_RESET%>">
                    </td>
                <% } %>
            </tr>

        </table>

    </div>

</form>

<%@ include file="Footer.jsp"%>

</body>
</html>