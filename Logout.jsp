<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="forms.Data" %>
<%
    // Clear the user data in the Data class
    Data.userId = "";
    Data.password = "";
session.invalidate();
    // Redirect to login page
    response.sendRedirect("Login.jsp");
%>
