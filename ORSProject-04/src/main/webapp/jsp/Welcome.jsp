
<%@page import="in.co.rays.proj4.controller.ORSView"%>
<!DOCTYPE html>
<html>
<head>
<title>Welcome to ORS</title>
<link rel="icon" type="image/png"
          href="<%= ORSView.APP_CONTEXT %>/img/logo.png" sizes="16x16" />
</head>
<body>
    <%@ include file="/jsp/Header.jsp" %>

    <form action="<%=ORSView.WELCOME_CTL%>">
        <br> <br> <br> <br> <br> <br>
        <h1 align="center">
            <font size="10px" color="navy">Welcome to ORS</font>
        </h1>
    </form>

    <%@ include file="/jsp/Footer.jsp" %>
</body>
</html>