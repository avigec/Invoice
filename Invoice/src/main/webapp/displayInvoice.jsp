<%@ page import="java.sql.*" %>
<html>
<head>
    <title>Invoice Display</title>
    <link rel="stylesheet" href="style.css">
</head>
<body>
    <div class="invoice-container">
        <header>
            <h1>INVOICE</h1>
        </header>

        <%
            String invoiceNumber = request.getParameter("invoiceNumber");

            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice_db", "root", "Avinash@123");
            Statement stmt = conn.createStatement();
            ResultSet rs = stmt.executeQuery("SELECT * FROM invoice WHERE invoice_number='" + invoiceNumber + "'");

            // Variables to hold totals
            double subtotal = 0;
            double total = 0;

            if (rs.next()) {
        %>
        <section class="invoice-details">
            <p><strong>Invoice Number:</strong> <%= rs.getString("invoice_number") %></p>
            <p><strong>Date:</strong> <%= rs.getString("invoice_date") %></p>
        </section>

        <section class="client-info">
            <h2>Client Information</h2>
            <p><strong>Name:</strong> <%= rs.getString("client_name") %></p>
            <p><strong>Address:</strong> <%= rs.getString("client_address") %></p>
        </section>

        <section class="invoice-items">
            <table>
                <thead>
                    <tr>
                        <th>Description</th>
                        <th>Quantity</th>
                        <th>Unit Price</th>
                        <th>Tax (%)</th>
                        <th>Total</th>
                    </tr>
                </thead>
                <tbody>
                    <%
                        do {
                            subtotal += rs.getDouble("subtotal");
                            total += rs.getDouble("total");
                    %>
                    <tr>
                        <td><%= rs.getString("item_desc") %></td>
                        <td><%= rs.getInt("item_qty") %></td>
                        <td><%= rs.getDouble("item_price") %></td>
                        <td><%= rs.getDouble("item_tax") %></td>
                        <td>Rs <%= rs.getDouble("total") %></td>
                    </tr>
                    <% } while (rs.next()); %>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="4">Subtotal:</td>
                        <td>Rs <%= subtotal %></td>
                    </tr>
                    <tr>
                        <td colspan="4">Total:</td>
                        <td>Rs <%= total %></td>
                    </tr>
                </tfoot>
            </table>
        </section>

        <!-- Print Button -->
        <button onclick="window.print()">Print Invoice</button>

        <%
            }
            rs.close();
            stmt.close();
            conn.close();
        %>
    </div>
</body>
</html>

