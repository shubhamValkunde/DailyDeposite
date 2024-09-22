<%@page import="java.util.List"%>
<%@ page contentType="text/html; charset=UTF-8" %>
<%@ page import="custDB.Customer, custDB.DBCustomerHelper" %>

<%
  String errorMessage = null;
  String custId = request.getParameter("custId");
  String pwd = request.getParameter("pwd");

  if (custId != null && pwd != null) {
    DBCustomerHelper dbHelper = new DBCustomerHelper();
    List<Customer> customers = dbHelper.getCustomerByCustId(custId);

    if (!customers.isEmpty()) {
        Customer c = customers.get(0);
        if (pwd.equals(c.getPwd())) {
          // Store user information in session
          session.setAttribute("custId", custId);
          session.setAttribute("isCustomer", true); // Example attribute for admin access
          response.sendRedirect("CustPassBook.jsp");
          return; // Ensure the code execution stops after redirection
        } else {
          errorMessage = "User ID or Password is incorrect.";
        }
    } else {
        errorMessage = "User ID not found.";
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
  <form action="CustLogin.jsp" method="post">
    <input required="required" type="text" id="custId" name="custId" placeholder="Enter custId"><br><br>
    <input required="required" type="password" id="pwd" name="pwd" placeholder="Enter password"><br><br>
    <input type="submit" value="Login">
  </form>
  <form action="customer.jsp">
    <br>
    <input type="submit" value="Register">
  </form>
</body>
</html>
