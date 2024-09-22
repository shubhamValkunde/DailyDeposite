<%@page import="custDB.EntryDBHelper"%>
<%@page import="java.util.List"%>
<%@page import="custDB.Entry"%>
<%@page import="java.text.ParseException"%>
<%@page import="java.util.Date"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="custDB.Customer"%>
<%@page import="custDB.DBCustomerHelper"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
    <meta charset="UTF-8">
    <title>Withdraw Amount</title>
</head>
<body>
    <h1>Withdraw Amount</h1>

    <div>
        <form action="WithdrawAmount.jsp" method="post">
            <input type="text" name="custId" placeholder="Enter Customer ID" required>
            <input type="text" name="amountToWithdraw" placeholder="Enter Amount to Withdraw" required>
            <input type="date" name="withdrawDate" required>
            <input type="submit" value="Withdraw Amount">
        </form>
    </div>

    <%
        DBCustomerHelper dbCustomerHelper = new DBCustomerHelper();
        String custId = request.getParameter("custId");
        String amountToWithdrawStr = request.getParameter("amountToWithdraw");
        String withdrawDateStr = request.getParameter("withdrawDate");

        if (custId != null && amountToWithdrawStr != null && withdrawDateStr != null) {
            try {
                double amountToWithdraw = Double.parseDouble(amountToWithdrawStr);

                EntryDBHelper e = new EntryDBHelper();
                // Convert the string date to a java.util.Date object
                SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
                Date withdrawDate = formatter.parse(withdrawDateStr);

                // Fetch customer and their first deposit date
                Customer customer = dbCustomerHelper.getCustomerByCustId(custId).get(0);
                // Fetch the earliest deposit date
        List<Entry> entries = dbCustomerHelper.getEntriesByCustomerId(custId);
        if (entries.isEmpty()) {
            System.out.println("No deposit entries found for this customer.");
            return;
        }


                // Perform the withdrawal
                dbCustomerHelper.withdraw(custId, amountToWithdraw, withdrawDate);

                out.println("<h2>Amount Withdrawn Successfully!</h2>");
                out.println("<p>Customer ID: " + custId + "</p>");
                out.println("<p>Withdrawal Date: " + withdrawDateStr + "</p>");
                out.println("<p>Withdrawn Amount: " + amountToWithdraw + "</p>");
            } catch (NumberFormatException e) {
                out.println("<p>Error: Invalid amount format. Please enter a valid number.</p>");
                e.printStackTrace();
            } catch (ParseException e) {
                out.println("<p>Error: Invalid date format. Please enter the date in YYYY-MM-DD format.</p>");
                e.printStackTrace();
            } catch (Exception e) {
                out.println("<p>An unexpected error occurred: " + e.getMessage() + "</p>");
                e.printStackTrace();
            }
        } else {
            out.println("<p>Error: Missing required fields.</p>");
        }
    %>
</body>
</html>
