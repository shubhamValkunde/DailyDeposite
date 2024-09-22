<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="forms.Admin, forms.AdminDbHelper" %>

<%
  String errorMessage = null;
  String userId = request.getParameter("userId");
  String pwd = request.getParameter("pwd");

  if (userId != null && pwd != null) {
    AdminDbHelper dbHelper = new AdminDbHelper();
    Admin admin = dbHelper.getAdminByUserId(userId);

    if (admin != null && pwd.equals(admin.getPwd())) {
      // Store user information in session
      session.setAttribute("userId", userId);
      session.setAttribute("isAdmin", true); // Example attribute for admin access
      response.sendRedirect("Dashboard.jsp");
      return; // Ensure the code execution stops after redirection
    } else {
      errorMessage = "User ID or Password is incorrect.";
    }
  }
%>

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
  <title>Login Authentication</title>
</head>
<body>
  <h1>Login Authentication</h1>
  <% if (errorMessage != null) { %>
    <p style="color:red;"><%= errorMessage %></p>
  <% } %>
  <form action="Login.jsp" method="post">
    <input required="required" type="text" id="userId" name="userId" placeholder="Enter userId"><br><br>
    <input required="required" type="password" id="pwd" name="pwd" placeholder="Enter password"><br><br>
    <input type="submit" value="Login">
  </form>
  <form action="Register.jsp">
    <br>
    <input type="submit" value="Register">
  </form>
</body>
</html>
