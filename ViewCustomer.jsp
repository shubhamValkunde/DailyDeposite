<%@page import="java.text.ParseException"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="custDB.Customer"%>
<%@page import="java.util.List"%>
<%@page import="java.util.ArrayList"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<%@page import="forms.Admin" %>
<%@page import="custDB.DBCustomerHelper" %>
<%@page import="custDB.MonthlySummary" %>

<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Customer List</title>
    <style>
        body {
            font-family: 'Arial', sans-serif;
            background-color: #f4f4f9;
            margin: 0;
            padding: 0;
        }
        h1 {
            text-align: center;
            color: #333;
            margin-top: 20px;
        }
        table {
            width: 90%;
            margin: 20px auto;
            border-collapse: collapse;
            box-shadow: 0 2px 10px rgba(0, 0, 0, 0.1);
            background-color: #fff;
        }
        th, td {
            padding: 15px;
            text-align: center;
            border-bottom: 1px solid #ddd;
        }
        th {
            background-color: #f2f2f2;
            color: #333;
            font-weight: bold;
        }
        tr:nth-child(even) {
            background-color: #f9f9f9;
        }
        form {
            display: inline-block;
        }
        input[type="submit"] {
            padding: 8px;
            margin: 5px;
            border: 1px solid #ddd;
            border-radius: 5px;
            background-color: #4CAF50;
            color: white;
            cursor: pointer;
        }
        input[type="submit"]:hover {
            background-color: #45a049;
        }
        .form-container {
            text-align: center;
            margin-top: 20px;
        }
        h2 {
            text-align: center;
            color: #555;
            margin-top: 40px;
        }
    </style>
</head>
<body>
    <h1>Customer List</h1>

    <div class="form-container">
        <form action="ViewCustomer.jsp" method="get">
            <input type="text" id="filterCustId" name="filterCustId" placeholder="Enter custId number to filter">
            <input type="submit" value="Filter">
        </form>
    </div>

    <%
        List<Customer> customers = new ArrayList<Customer>();
        List<MonthlySummary> monthSummaries = new ArrayList<MonthlySummary>();
        DBCustomerHelper dbCustomerHelper = new DBCustomerHelper();
        String deleteCustId = request.getParameter("deleteCustId");
        String action = request.getParameter("action");
        String filterCustId = request.getParameter("filterCustId");

        if (deleteCustId != null && "confirmDelete".equals(action)) {
            dbCustomerHelper.deleteCustomer(deleteCustId);
            customers = dbCustomerHelper.getAllCustomers();
        } else {
            if (filterCustId != null && !filterCustId.isEmpty()) {
                customers = dbCustomerHelper.getCustomerByCustId(filterCustId);
                monthSummaries = dbCustomerHelper.calculateMonthlySummary(filterCustId);
            } else {
                customers = dbCustomerHelper.getAllCustomers();
            }
        }
    %>

    <table>
        <tr>
            <th>Name</th>
            <th>Mobile No</th>
            <th>Aadhar No</th>
            <th>Customer Id</th>
            <th>Balance</th>
            <th>Address</th>
            <th>Delete Customer</th>
            <th>Add Balance</th>
        </tr>
        <% for (Customer c : customers) { %>
        <tr>
            <td><%= c.getName() %></td>
            <td><%= c.getMob_No() %></td>
            <td><%= c.getAadhar_No() %></td>
            <td><%= c.getCustId() %></td>
            <td><%= c.getBalance() %></td>
            <td><%= c.getAddress() %></td>
            <td>
                <form action="ViewCustomer.jsp" method="post">
                    <input type="hidden" name="deleteCustId" value="<%= c.getCustId() %>">
                    <input type="hidden" name="action" value="confirmDelete">
                    <input type="submit" value="Delete">
                </form>
            </td>
            <td>
                <form action="addBalance.jsp" method="post"> <!-- Redirects to addBalance.jsp -->
                    <input type="hidden" name="custId" value="<%= c.getCustId() %>">
                    <input type="submit" value="Add Balance">
                </form>
            </td>
        </tr>
        <% } %>
    </table>

    <% if (filterCustId != null && !filterCustId.isEmpty()) { %>
        <h2>Monthly Summary for Customer ID: <%= filterCustId %></h2>
        <table>
            <tr>
                <th>Month</th>
                <th>First Half Amount</th>
                <th>Second Half Amount</th>
                <th>First Half X Amount</th>
                <th>Second Half X Amount</th>
                <th>Monthly Interest</th>
                <th>Withdrawn Amount</th> <!-- New Column for Withdrawn Amount -->
                <th>Penalty</th> <!-- New Column for Penalty -->
                <th>Balance</th>
            </tr>
            <% for (MonthlySummary summary : monthSummaries) { %>
            <tr>
                <td><%= summary.getMonth() %></td>
                <td><%= summary.getFirstHalfAmount() %></td>
                <td><%= summary.getSecondHalfAmount() %></td>
                <td><%= summary.getxAmountFirstHalf() %></td>
                <td><%= summary.getXamountSecondHalf() %></td>
                <td><%= summary.getMonthlyInterest() %></td>
                <td><%= summary.getWithdrawnAmount() %></td> <!-- Display Withdrawn Amount -->
                <td><%= summary.getPenalty() %></td> <!-- Display Penalty -->
                <td><%= summary.getCarryOver() %></td>
            </tr>
            <% } %>
        </table>
    <% } %>
</body>
</html>
