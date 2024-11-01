package Bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Arrays;
import java.util.Random;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServlet;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

public class NewAccount extends HttpServlet
{
    @Override
	public void service(HttpServletRequest req, HttpServletResponse res) throws IOException, ServletException
    {
        res.setContentType("text/html");
        PrintWriter out = res.getWriter();  // Corrected from printWriter to PrintWriter
        String user = req.getParameter("username");
        String adhar = req.getParameter("adhar");
        String email = req.getParameter("email");
        String mobile = req.getParameter("mobile");
        String father = req.getParameter("father");
        String accounttype = req.getParameter("account-type");
        String balance = req.getParameter("balance");
        String gender = req.getParameter("gender");
        String pin = req.getParameter("pin");




        Random random = new Random();
        String s = "1234567890";
        boolean isUnique = false;
        String res1 = "";

        try
        {
        	Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ducatproject", "root", "mysql123");


            while(!isUnique) {
                char[] otp = new char[11];
                for (int i = 0; i < 11; i++)
                {
                    otp[i] = s.charAt(random.nextInt(s.length()));
                }

                String[] strArray = new String[otp.length];
                for (int i = 0; i < otp.length; i++)
                {
                    strArray[i] = String.valueOf(otp[i]);
                }

                String s1 = Arrays.toString(strArray);


                for (String num : strArray)
                {
                    res1 += num;
                }

                PreparedStatement uniqueps = con.prepareStatement("select * from accountdata WHERE accountnumber =?");
                uniqueps.setString(1, res1);
                ResultSet rs = uniqueps.executeQuery();

                if(!rs.next())
                {
                	isUnique = true;
                }
             }

		}
        catch (Exception e)
        {
			out.println(e);
		}



        try
        {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ducatproject", "root", "mysql123");

            // Use CURRENT_TIMESTAMP for the update_time column
            PreparedStatement ps = con.prepareStatement(
            	    "INSERT INTO accountdata (username, accountnumber, adhar, email, mobile, father_name, balance, gender, accounttype, pin_number) " +
            	    "VALUES (?, ?, ?, ?, ?, ?, ?, ?, ?, ?)");
            	ps.setString(1, user);
            	ps.setString(2, res1);  // accountnumber
            	ps.setString(3, adhar);
            	ps.setString(4, email);
            	ps.setString(5, mobile);
            	ps.setString(6, father);
            	ps.setString(7, balance);
            	ps.setString(8, gender);
            	ps.setString(9, accounttype);
            	ps.setString(10, pin);

            	ps.executeUpdate();

            PreparedStatement ps1 = con.prepareStatement("SELECT * FROM accountdata WHERE accountnumber = ?");
            ps1.setString(1, res1);

            ResultSet rs = ps1.executeQuery();  // Corrected from Resultset to ResultSet
            while (rs.next())
            {
                out.println("<br>name= " + rs.getString(1));
                out.println("<br>account no= " + rs.getString(2));
                out.println("<br>adhar= " + rs.getString(3));
                out.println("<br>email id= " + rs.getString(4));
                out.println("<br>mobile no= " + rs.getString(5));
                out.println("<br>fathername= " + rs.getString(6));
                out.println("<br>balance= " + rs.getString(7));
                out.println("<br>gender= " + rs.getString(8));
                out.println("<br>accounttype= " + rs.getString(9));

            }

            RequestDispatcher disp = req.getRequestDispatcher("AfterLogin.html");
            disp.include(req, res);  // Corrected parameter order from (res, req) to (req, res)
            out.println("<br>Registered successfully");

        }
        catch (Exception e)
        {
            out.println(e);
        }
        out.close();
    }
}
