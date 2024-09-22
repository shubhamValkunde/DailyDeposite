<%@page import="custDB.DBCustomerHelper"%>
<%@page import="custDB.Customer"%>
<%@page import="custDB.Entry"%>
<%@page import="custDB.PrintPassBookDBHelper"%>
<%@page import="java.util.List"%>
<%@page import="java.text.SimpleDateFormat"%>
<%@page import="com.google.gson.Gson"%>
<!DOCTYPE html>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
    <title>Print Passbook</title>
    <style>
        body {
            font-family: Arial, sans-serif;
            margin: 0;
            padding: 0;
        }
        .footer {
            text-align: center;
            margin-top: 30px;
            font-size: 14px;
            color: #777;
        }
        .passbook-container {
            width: 100%;
            max-width: 1000px;
            margin: 20px auto;
            background-color: #ffffff;
            padding: 20px;
            border: 1px solid #ddd;
        }
        h1, h2 {
            text-align: center;
            color: #004d99;
        }
        .customer-details, .passbook-table {
            margin-bottom: 20px;
            padding: 10px;
            background-color: #f9f9f9;
            border-radius: 8px;
            box-shadow: 0 2px 4px rgba(0, 0, 0, 0.05);
        }
        .customer-details p {
            margin: 8px 0;
            font-size: 16px;
            color: #333;
        }
        table {
            width: 100%;
            border-collapse: collapse;
            margin-top: 20px;
        }
        th, td {
            border: 1px solid #ddd;
            padding: 12px;
            text-align: center;
            font-size: 14px;
        }
        th {
            background-color: #004d99;
            color: #ffffff;
        }
        td {
            background-color: #f9f9f9;
        }
        @media print {
            .footer {
                display: none;
            }
        }
    </style>
</head>
<body>
    <div class="passbook-container">
        <h1>ShriGanesh Financial</h1>
        <h2>Customer Passbook</h2>
        <!-- Bank Details -->
        <div class="bank-details">
            <p>Bank Address: 1234 Bank MG Road, Sangli, India</p>
            <p>Contact: +189-834-567-890 | Email: support@shriganeshfinancial.com</p>
            <p>Account Number: <strong>123456784623456</strong></p>
        </div>

        <% 
            String filterCustId = request.getParameter("custId");
            DBCustomerHelper dbHelper = new DBCustomerHelper();
            List<Customer> customers = dbHelper.getCustomerByCustId(filterCustId);

            if (customers != null && !customers.isEmpty()) {
                Customer customer = customers.get(0);
                List<Entry> passbookEntries = dbHelper.getEntriesByCustomerId(filterCustId);
                SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy-MM-dd");

                // Save print data
                PrintPassBookDBHelper printDBHelper = new PrintPassBookDBHelper();
                printDBHelper.createPrintPassBook(filterCustId, customer, passbookEntries);
        %>
        <!-- Customer Details -->
        <div class="customer-details">
            <p><strong>Customer Name:</strong> <%= customer.getName() %></p>
            <p><strong>Mobile Number:</strong> <%= customer.getMob_No() %></p>
            <p><strong>Customer ID:</strong> <%= customer.getCustId() %></p>
            <p><strong>Address:</strong> <%= customer.getAddress() %></p>
        </div>

        <!-- Passbook Entries Table -->
        <div class="passbook-table">
            <table>
                <tr>
                    <th>Date</th>
                    <th>Daily Deposit</th>
                    <th>Balance</th>
                </tr>
                <% 
                    if (passbookEntries != null && !passbookEntries.isEmpty()) {
                        for (Entry entry : passbookEntries) {
                            String date = dateFormat.format(entry.getDate());
                            double dailyDeposit = entry.getAmount();
                            double balance = entry.getBalance();
                %>
                <tr>
                    <td><%= date %></td>
                    <td><%= dailyDeposit %></td>
                    <td><%= balance %></td>
                </tr>
                <% 
                        }

                    } else {
                        out.println("<tr><td colspan='3'>No passbook entries found.</td></tr>");
                    }
                %>
            </table>
        </div>

        <% 
            } else {
                out.println("<p style='color:red; text-align:center;'>Customer ID not found.</p>");
            }
        %>
        
        <!-- Footer -->
        <div class="footer">
            <p>&copy; 2024 ShriGanesh Financial. All rights reserved.</p>
        </div>
    </div>
</body>
</html>
