package Bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;


public class Transaction extends HttpServlet
{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		res.setContentType("text/html");
        PrintWriter out = res.getWriter();
		String withdrawquery = "UPDATE accountdata SET balance = balance - ? WHERE accountnumber =?";
		String depositquery =  "UPDATE accountdata SET balance = balance + ? WHERE accountnumber =?";
		String insertTransactionQuery = "INSERT INTO transaction_history (sender_account_number, amount_deducted, receiver_account_number , receiver_name) VALUES (?, ?, ?, ?)";
		String receiverNameQuery = "SELECT * FROM accountdata WHERE accountnumber = ?";  // Query to get receiver's name

        // Get user input from the form
        String enteredAccountNo = req.getParameter("senderAccountNo");
        String enteredPin = req.getParameter("senderPin");
        String receiveraccount = req.getParameter("receiverAccountNo");
        double amount = Double.parseDouble(req.getParameter("amount"));

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
		}
		catch (Exception e)
		{
			e.printStackTrace();
		}

		try
		{

			Connection connection = DriverManager.getConnection("jdbc:mysql://localhost:3306/ducatproject", "root", "mysql123");
			connection.setAutoCommit(false); //BY DEFAULT THE COMMIT IS TRUE SO WE HAVE SET IT AS FALSE
			String receivername= null;
			try
			{
				PreparedStatement receivernamestatement = connection.prepareStatement(receiverNameQuery);
				receivernamestatement.setString(1, receiveraccount);
				ResultSet rs = receivernamestatement.executeQuery();
				
				if (rs.next()) 
				{
					
				} 
				else 
				{
					out.println("<h1>Error: Receiver account not found!</h1>");
	                return; // Exit if receiver's account is not found
				}
				
				PreparedStatement withdrawStatement = connection.prepareStatement(withdrawquery);
			    PreparedStatement depositStatement = connection.prepareStatement(depositquery);
			    PreparedStatement insertTransactionStatement = connection.prepareStatement(insertTransactionQuery);

			    withdrawStatement.setDouble(1,amount);
			    withdrawStatement.setString(2,enteredAccountNo);

			    depositStatement.setDouble(1, amount);
			    depositStatement.setString(2,receiveraccount);

			    int RowsAffecttedWithdraw = withdrawStatement.executeUpdate();
			    int RowsAffectedDeposit = depositStatement.executeUpdate();

			    if (RowsAffecttedWithdraw > 0 && RowsAffectedDeposit > 0)
			    {
		                insertTransactionStatement.setString(1, enteredAccountNo);
	                    insertTransactionStatement.setDouble(2, amount);
	                    insertTransactionStatement.setString(3, receiveraccount);
	                    insertTransactionStatement.setString(4, receivername);

	                    insertTransactionStatement.executeUpdate();

			    	connection.commit();
			    	RequestDispatcher dispatcher = req.getRequestDispatcher("dashboard.jsp");
		            dispatcher.include(req, res);
				    out.println("Transaction Successful!!!");
				}
			    else
			    {
			    	connection.rollback();
					System.out.println("Transaction Failed");
			    }

			}
			catch (Exception e)
			{
				e.printStackTrace();
			}
		}
		catch(Exception e)
		{
			e.printStackTrace();
		}
	}
}
