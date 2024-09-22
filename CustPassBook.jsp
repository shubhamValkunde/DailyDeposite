<%@page import="custDB.Customer"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page import="java.io.IOException"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="custDB.DBCustomerHelper" %>
<%@page import="custDB.Entry"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Customer Passbook</title>
        <style>
            h1 {text-align: center;}
            table {width: 100%; border-collapse: collapse; margin-top: 20px;}
            th, td {border: 1px solid #ddd; padding: 8px; text-align: center;}
            th {background-color: #f2f2f2;}
            form {text-align: center; margin-bottom: 20px;}
        </style>
    </head>
    <body>
        <h1>Customer Passbook</h1>
        
        <!-- Form to enter customer ID -->
        <form action="displayPassbook.jsp" method="get">
            <label for="custId">Enter Customer ID:</label>
            <input type="text" id="custId" name="custId" required>
            <input type="submit" value="Search">
        </form>
        
        <%
            // Get custId from request parameter
            String custId = request.getParameter("custId");
            List<Entry> entries = new ArrayList<>();

            if (custId != null && !custId.isEmpty()) {
                // Initialize database helper
                DBCustomerHelper dbHelper = new DBCustomerHelper();
                // Fetch entries for the given customer ID
                entries = dbHelper.getEntriesByCustomerId(custId);
            }
        %>

        <table>
            <tr>
                <th>Date</th>
                <th>Customer ID</th>
                <th>Balance</th>
                <th>Daily Deposit</th>
            </tr>
            <% 
                if (custId == null || custId.isEmpty()) {
                    out.println("<tr><td colspan='4'>Please enter a customer ID to search.</td></tr>");
                } else if (entries.isEmpty()) {
                    out.println("<tr><td colspan='4'>No entries found for this customer.</td></tr>");
                } else {
                    for (Entry e : entries) { 
            %>
            <tr>
                <td><%= e.getDate() %></td>
                <td><%= e.getCustId() %></td>
                <td><%= e.getBalance() %></td>
                <td><%= e.getAmount() %></td>
            </tr>
            <% 
                    }
                }
            %>
        </table>
    </body>
</html>
