<%@ page contentType="text/html; charset=UTF-8" %>

<%
  // Retrieve user information from session
  String userId = (String) session.getAttribute("userId");

  // Security: Check if userId is present in session and redirect if missing
  if (userId == null || userId.isEmpty()) {
    response.sendRedirect("Login.jsp");
    return; // Stop further executions
  }
%>

<!DOCTYPE html>
<html>
<head>
  <meta http-equiv="Content-Type" content="text/html; charset=UTF-8" />
  <title>Dashboard</title>
</head>
<body>
  <h1>Welcome <%= userId %></h1>
  <p>You are logged in.</p>
  
  <form action="customer.jsp" method="post">
    <input type="submit" value="Add Customer">
  </form><br><br> 
  
  <form action="Logout.jsp" method="post">
    <input type="submit" value="Logout">
  </form>
  <br>
  
  <form action="ViewCustomer.jsp" method="post">
    <input type="submit" value="View Customer">
  </form>
  <br>
  
</body>


</html>
