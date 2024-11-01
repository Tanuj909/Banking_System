package Bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Debit extends HttpServlet
{
    @Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
    {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();
        BigDecimal debitAmount;


        // Get user input from the form
        String enteredAccountNo = req.getParameter("account-number");
        String enteredPin = req.getParameter("pin-number");

        // Create a SessionValidator instance
        SessionValidator sessionValidator = new SessionValidator(req);

        // Check if user is logged in
        if (!sessionValidator.isUserLoggedIn()) {
            out.println("<h1>Error: You are not logged in!</h1>");
            return; // Exit if user is not logged in
        }

        // Get account number and pin from session
        String sessionAccountNo = sessionValidator.getSessionAccountNumber();
        String sessionPin = sessionValidator.getSessionPin();

        // Validate entered details with session values
        if (!enteredAccountNo.equals(sessionAccountNo) || !enteredPin.equals(sessionPin)) {
            out.println("<h1>Error: Invalid account number or PIN!</h1>");
            return; // Exit if details do not match
        }


        // Validate input
        try {
            debitAmount = new BigDecimal(req.getParameter("amount")); // Parse amount as BigDecimal
        } catch (NumberFormatException e) {
            out.println("Invalid amount. Please provide a valid number.");
            return; // Exit if the amount is invalid
        }

        Connection con = null;
        PreparedStatement selectStmt = null;
        PreparedStatement updateStmt = null;
        ResultSet rs = null;

        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ducatproject", "root", "mysql123");

            // Use Prepared Statements to avoid SQL injection
            selectStmt = con.prepareStatement("SELECT balance FROM accountdata WHERE accountnumber = ? AND pin_number= ?");
            selectStmt.setString(1, enteredAccountNo);
            selectStmt.setString(2, enteredPin);
            rs = selectStmt.executeQuery();

            if (rs.next()) {
                BigDecimal currentBalance = rs.getBigDecimal(1); // Get balance as BigDecimal
                BigDecimal newBalance = currentBalance.subtract(debitAmount);

                // Check if the new balance is negative
                if (newBalance.compareTo(BigDecimal.ZERO) < 0) {
                    out.println("Insufficient balance. Current balance is: " + currentBalance);
                } else {
                    // Update the balance
                    updateStmt = con.prepareStatement("UPDATE accountdata SET balance = ? WHERE accountnumber = ?");
                    updateStmt.setBigDecimal(1, newBalance);
                    updateStmt.setString(2, enteredAccountNo);
                    updateStmt.executeUpdate();
                    out.println("New balance is: " + newBalance);
                }
            } else {
                out.println("Account not found.");
            }

            // Redirect to AfterLogin.html
            RequestDispatcher disp = req.getRequestDispatcher("AfterLogin.html");
            disp.include(req, res);
        }
        catch(Exception e)
        {
            out.println("An error occurred: " + e.getMessage());
            e.printStackTrace(out); // Print the stack trace for debugging
        }
        finally {
            // Close resources
            try {
                if (rs != null) {
					rs.close();
				}
                if (selectStmt != null) {
					selectStmt.close();
				}
                if (updateStmt != null) {
					updateStmt.close();
				}
                if (con != null) {
					con.close();
				}
            } catch (SQLException e) {
                e.printStackTrace(out);
            }
        }
    }
}
