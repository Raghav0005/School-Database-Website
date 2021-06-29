import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class log
 */
@WebServlet("/log")
public class log extends HttpServlet {
	public void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {		
		
		String tname = request.getParameter("name");
		int trn = Integer.parseInt(request.getParameter("rno"));
		String tpass = request.getParameter("pass");
		try {
			Connection con = DBConnection.getConn();
			
			PreparedStatement ps = con.prepareStatement("select * from cbsccheck where name=? and rn=? and password=?");
			ps.setString(1, tname);
			ps.setInt(2, trn);
			ps.setString(3, tpass);
			ResultSet rs  = ps.executeQuery();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			if(rs.next())
			{
				ServletContext ctx = getServletContext();
				HttpSession se = request.getSession();
				se.setAttribute("name", tname);
				se.setAttribute("pass", tpass);
				//Cookie ck = new Cookie("name", tname);
				//response.addCookie(ck);
				ctx.setAttribute("name", tname);
				ctx.setAttribute("rollno", trn);
				response.sendRedirect("homepage");
				//RequestDispatcher rd = request.getRequestDispatcher("homepage");
				//rd.forward(request, response);
			}
			else
			{
				out.println("<html><body> <p style=\"color:red\">User Invalid. Choose one of the options below:</p>");
				out.println("<br><a href=\"registeration.html\"> Register an Account </a>");
				out.println("<br><a href=\"login.html\"> Try Again: </a>");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
