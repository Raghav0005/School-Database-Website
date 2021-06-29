import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import javax.servlet.Servlet;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class homepage
 */
@WebServlet("/homepage")
public class homepage extends HttpServlet {
	PreparedStatement ps;
	
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		try {
			Connection con = DBConnection.getConn();
			response.setContentType("text/html");
			PrintWriter out = response.getWriter();
			ServletContext ctx = getServletContext();
			String tname = (String) ctx.getAttribute("name");
			int trn = (int) ctx.getAttribute("rollno");
			HttpSession s = request.getSession(false);
			if(s!=null) {
				String s1 = (String)s.getAttribute("name");
				ps = con.prepareStatement("select * from cbsc where name=? and rollno=?");
				ps.setString(1, tname);
				ps.setInt(2, trn);
				ResultSet rs = ps.executeQuery();
				out.println("<html><h1><center> Welcome </center></h1>");
				while(rs.next())
				{
					int average = (rs.getInt(5)+rs.getInt(6)+rs.getInt(7)+rs.getInt(8)+rs.getInt(9))/5;
					char grade = ' ';
					if(average>=80)
						grade = 'A';
					else if(average>=70 && average < 80)
						grade = 'B';
					else if(average>=60 && average < 70)
						grade = 'C';
					else if(average>=50 && average < 60)
						grade = 'D';
					else
						grade = 'F';
					out.println("<br><body><center> Name: "+ rs.getString(1)+ " </center></body>");
					out.println("<br><body><center> Age: "+ rs.getInt(2)+ " </center></body>");
					out.println("<br><body><center> Grade: "+ rs.getInt(3)+ " </center></body>");
					out.println("<br><body><center> Roll Number: "+ rs.getInt(4)+ " </center></body>");
					out.println("<br><body><center> Mark 1: "+ rs.getInt(5)+ " </center></body>");
					out.println("<br><body><center> Mark 2: "+ rs.getInt(6)+ " </center></body>");
					out.println("<br><body><center> Mark 3: "+ rs.getInt(7)+ " </center></body>");
					out.println("<br><body><center> Mark 4: "+ rs.getInt(8)+ " </center></body>");
					out.println("<br><body><center> Mark 5: "+ rs.getInt(9)+ " </center></body>");
					out.println("<br><body><center> Rounded Average of all Marks: "+ average+ " </center></body>");
					out.println("<br><body><center> Letter Grade: "+ grade + " </center></body>");
				}
				out.println("<br><center><a href=\"update.html\"> Update an Existing Entry </center></a>");
				out.println("<br><center><a href=\"del.html\"> Delete your Account? </center></a>");
				out.println("<br><br><center><a href=\"index.html\"> Sign out </center></a>");
			}
			else
			{
				out.println("Please login first");
				request.getRequestDispatcher("login.html").include(request, response);
			}
			

		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
