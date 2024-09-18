package com.developer.Invoice;

import java.io.IOException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/saveInvoice")
public class InvoiceServlet extends HttpServlet {
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        // Retrieve form data
        String invoiceNumber = request.getParameter("invoice-number");
        String invoiceDate = request.getParameter("invoice-date");
        String clientName = request.getParameter("client-name");
        String clientAddress = request.getParameter("client-address");

        // Database connection setup
        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection conn = DriverManager.getConnection("jdbc:mysql://localhost:3306/invoice_db", "root", "Avinash@123");
            
            // Start transaction
            conn.setAutoCommit(false);

            // Prepare SQL statements
            String insertInvoiceQuery = "INSERT INTO invoice (invoice_number, invoice_date, client_name, client_address, item_desc, item_qty, item_price, item_tax, subtotal, total) VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)";
            PreparedStatement pstmt = conn.prepareStatement(insertInvoiceQuery);

            double grandTotal = 0;

            // Process each item
            String[] itemDescs = request.getParameterValues("item-desc[]");
            String[] itemQtys = request.getParameterValues("item-qty[]");
            String[] itemPrices = request.getParameterValues("item-price[]");
            String[] itemTaxes = request.getParameterValues("item-tax[]");

            for (int i = 0; i < itemDescs.length; i++) {
                String itemDesc = itemDescs[i];
                int itemQty = Integer.parseInt(itemQtys[i]);
                double itemPrice = Double.parseDouble(itemPrices[i]);
                double itemTax = Double.parseDouble(itemTaxes[i]);

                double subtotal = itemQty * itemPrice;
                double total = subtotal + (subtotal * itemTax / 100);
                grandTotal += total;

                pstmt.setString(1, invoiceNumber);
                pstmt.setString(2, invoiceDate);
                pstmt.setString(3, clientName);
                pstmt.setString(4, clientAddress);
                pstmt.setString(5, itemDesc);
                pstmt.setInt(6, itemQty);
                pstmt.setDouble(7, itemPrice);
                pstmt.setDouble(8, itemTax);
                pstmt.setDouble(9, subtotal);
                pstmt.setDouble(10, total);
                
                pstmt.addBatch(); // Add to batch
            }

            // Execute batch
            pstmt.executeBatch();
            conn.commit(); // Commit transaction

            pstmt.close();
            conn.close();

            // Success: Redirect to display invoice
            response.sendRedirect("displayInvoice.jsp?invoiceNumber=" + invoiceNumber);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}





