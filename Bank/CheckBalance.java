package Bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class CheckBalance extends HttpServlet {
    @Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();

        // Get user input from the form
        String enteredAccountNo = req.getParameter("accountno");
        String enteredPin = req.getParameter("userpin");

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

        try {
            // Use the correct JDBC driver
            Class.forName("com.mysql.cj.jdbc.Driver");

            // Use try-with-resources for better resource management
            try (Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ducatproject", "root", "mysql123");
                 PreparedStatement ps = con.prepareStatement("SELECT balance FROM accountdata WHERE accountnumber = ? AND pin_number = ?"))
            {
                ps.setString(1, sessionAccountNo);
                ps.setString(2, sessionPin);
                ResultSet rs = ps.executeQuery();

                if (rs.next()) {
                    // Print balance if found
                    out.println("<h1>Your Balance: " + rs.getString("balance") + " RS.</h1>");
                } else {
                    // Handle case where account number is not found
                    out.println("<h1>Error: Account details not found!</h1>");
                }
            }
        } catch (Exception e) {
            e.printStackTrace(out); // Print the error for debugging
        }

        out.close(); // Close PrintWriter
    }
}
