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
import jakarta.servlet.http.HttpSession;

public class Login extends HttpServlet
{
	@Override
	protected void service(HttpServletRequest req, HttpServletResponse res) throws ServletException, IOException
	{
		 res.setContentType("text/html");
	     PrintWriter out = res.getWriter();

	     String email = req.getParameter("email");
	     String pass  = req.getParameter("password");

	     try
	     {
	    	 Class.forName("com.mysql.cj.jdbc.Driver");
	    	 Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ducatproject", "root", "mysql123");
	    		PreparedStatement pst = con.prepareStatement("select * from bank where email= ? and userpass=?");
				pst.setString(1, email);
				pst.setString(2, pass);

				ResultSet rs = pst.executeQuery();

				if(rs.next()) //This checks whether the ResultSet contains any records (i.e., if a user with the provided email and password exists in the database). If rs.next() returns true, it means the email and password match, and the user is authenticated.
				{

					HttpSession session = req.getSession();
				    String dbUsername = rs.getString("username"); // Assuming 'username' is a column in your 'bank' table
				    session.setAttribute("username", dbUsername);

				    
				    //
				    PreparedStatement ps = con.prepareStatement("select * from accountdata where email = ?");
				    ps.setString(1, email);
				    ResultSet accountrs = ps.executeQuery();


				    if(accountrs.next())
				    {
						long accountNumber = accountrs.getLong("accountnumber");
						session.setAttribute("accountnumber", String.valueOf(accountNumber)); // Store as String

					    String pin = accountrs.getString("pin_number");
					    session.setAttribute("pinnumber", String.valueOf(pin));  //Store the pin
					}



				    session.setMaxInactiveInterval(30 * 60);

				    System.out.println("Username from DB: " + dbUsername);


					RequestDispatcher rd = req.getRequestDispatcher("dashboard.jsp");
					rd.forward(req,res);
				}
				else
				{
					out.println("Please Enter the Correct Email & Password!!");
					RequestDispatcher rd = req.getRequestDispatcher("login.html");
					rd.include(req,res); //'include' to show the message without losing the login page
				}


		 }
	     catch (Exception e)
	     {
			out.println(e);
		 }


	 }
}
