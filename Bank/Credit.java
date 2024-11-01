package Bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class Credit extends HttpServlet
{
    @Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
    {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        String enteredAccountNo = req.getParameter("account-number");
        String enteredPin = req.getParameter("pin-number");
        String creditamount = req.getParameter("amount");

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


        long credit;
        try {
            credit = Long.parseLong(creditamount);
        } catch (NumberFormatException e) {
            out.println("Invalid amount! Please enter a valid number.");
            out.flush();
            return;
        }

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ducatproject", "root", "mysql123");
            Statement s1 = con.createStatement();
            ResultSet rs = s1.executeQuery("select balance from accountdata where accountnumber='" + enteredAccountNo + "'");

            if (rs.next()) {
                long amount = rs.getLong(1);
                long newbalance = amount + credit;

                Statement s = con.createStatement();
                s.executeUpdate("update accountdata set balance=" + newbalance + " where accountnumber='" + enteredAccountNo + "'");

                out.println("New balance is: " + newbalance);
            } else {
                out.println("Account number not found!");
            }
            RequestDispatcher disp = req.getRequestDispatcher("AfterLogin.html");
            disp.include(req, res);
        } catch (Exception e) {
            out.println("An error occurred: " + e.getMessage());
        } finally {
            out.flush(); // Ensure output is sent to client
        }
    }
}
