

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
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class up
 */
@WebServlet("/up")
public class up extends HttpServlet {
	PreparedStatement ps;
	ResultSet rs;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		int age, grade, m1, m2, m3, m4, m5;
		
		ServletContext ctx = getServletContext();
		String n = (String) ctx.getAttribute("name");
		try {
			Connection con = DBConnection.getConn();
			ps = con.prepareStatement("select * from cbsc where name=?");
			ps.setString(1, n);
			rs = ps.executeQuery();
			while(rs.next())
			{
				if(request.getParameter("age").isEmpty())
					age = rs.getInt(2);
				else
					age = Integer.parseInt(request.getParameter("age"));
				
				if(request.getParameter("grade").isEmpty())
					grade = rs.getInt(3);
				else
					grade = Integer.parseInt(request.getParameter("grade"));
				
				if(request.getParameter("m1").isEmpty())
					m1 = rs.getInt(5);
				else
					m1 = Integer.parseInt(request.getParameter("m1"));
				
				if(request.getParameter("m2").isEmpty())
					m2 = rs.getInt(6);
				else
					m2 = Integer.parseInt(request.getParameter("m2"));
				
				if(request.getParameter("m3").isEmpty())
					m3 = rs.getInt(7);
				else
					m3 = Integer.parseInt(request.getParameter("m3"));
				
				if(request.getParameter("m4").isEmpty())
					m4 = rs.getInt(8);
				else
					m4 = Integer.parseInt(request.getParameter("m4"));
				
				if(request.getParameter("m5").isEmpty())
					m5 = rs.getInt(9);
				else
					m5 = Integer.parseInt(request.getParameter("m5"));
				ps = con.prepareStatement("update cbsc set age=?, grade=?, mark1=?, mark2=?, mark3=?, mark4=?, mark5=? where name=?");
				ps.setInt(1, age);
				ps.setInt(2, grade);
				ps.setInt(3, m1);
				ps.setInt(4, m2);
				ps.setInt(5, m3);
				ps.setInt(6, m4);
				ps.setInt(7, m5);
				ps.setString(8, n);
				ps.executeUpdate();
			}
			RequestDispatcher rd = request.getRequestDispatcher("homepage");
			rd.forward(request, response);
			out.println("<html><body> Entries updated successfully");
			
					
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
