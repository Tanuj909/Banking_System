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

public class Transaction_History extends HttpServlet
{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		    res.setContentType("text/html");
	        PrintWriter out = res.getWriter();

	        // Get user input from the form
	        String enteredAccountNo = req.getParameter("accountNumber");
	        String enteredPin = req.getParameter("pin");

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


	        try
	        {
	        	Class.forName("com.mysql.cj.jdbc.Driver");

	        	try
	        	{
	        		Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ducatproject", "root", "mysql123");
	        		PreparedStatement ps = con.prepareStatement("Select * from transaction_history WHERE sender_account_number =? ");

	        	    ps.setString(1, enteredAccountNo);

	        	    ResultSet rs = ps.executeQuery();
	        	    
	        	    while(rs.next())
	        	    {
	        	    	out.println("Transaction Id: " + rs.getString("transaction_id") + "<br>");
						out.println("Transaction Date: " + rs.getString("transaction_date") + "<br>");
						out.println("Your Account Number: " + rs.getString("sender_account_number") + "<br>");
						out.println("Ammount Deducted " + rs.getString("amount_deducted") + "<br>");
						out.println("Receiver Account Number: " + rs.getString("receiver_account_number") + "<br>");
						out.println("Receiver Name" + rs.getString("receiver_name") + "<br>");
						out.println("</p>");
					}

				}
	        	catch (Exception e)
	        	{
					e.printStackTrace();
				}
			}
	        catch (Exception e)
	        {
				e.printStackTrace();
			}

	}
}
