package Bank;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;

import jakarta.servlet.RequestDispatcher;
import jakarta.servlet.Servlet;
import jakarta.servlet.ServletConfig;
import jakarta.servlet.ServletException;
import jakarta.servlet.ServletRequest;
import jakarta.servlet.ServletResponse;

public class MyServlet implements Servlet {
    @Override
	public void init(ServletConfig h) {}

    @Override
    public void service(ServletRequest req, ServletResponse res) throws ServletException, IOException {
        res.setContentType("text/html");
        PrintWriter pw = res.getWriter();
        String name = req.getParameter("username");
        String pass = req.getParameter("password");
        String email = req.getParameter("email");
        String contact = req.getParameter("contact");

        try {
            Class.forName("com.mysql.cj.jdbc.Driver");
            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/ducatproject", "root", "mysql123");
            PreparedStatement ps = con.prepareStatement("insert into bank(username,userpass,email,contact) values(?,?,?,?)");
            ps.setString(1, name);
            ps.setString(2, pass);
            ps.setString(3, email);
            ps.setString(4, contact);
            int row = ps.executeUpdate();

            RequestDispatcher dispatcher = req.getRequestDispatcher("login.html");
            dispatcher.include(req, res);
            pw.println("<br>Hey " + name + ", you are registered successfully.");
            if (row>0) {
            	System.out.println("success");
			} else {
				System.out.println("failed");
			}
        } catch (Exception e) {
            System.out.println(e);
        }
        pw.close();
    }

    @Override
	public void destroy(){}
    @Override
	public String getServletInfo() { return(null); }
    @Override
	public ServletConfig getServletConfig() { return(null); }
}


