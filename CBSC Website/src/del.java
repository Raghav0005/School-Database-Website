

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * Servlet implementation class del
 */
@WebServlet("/del")
public class del extends HttpServlet {
	PreparedStatement ps;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		try {
			Connection con = DBConnection.getConn();
			String c = request.getParameter("del");
			if(c.equals("DELETE"))
			{
				ServletContext ctx = getServletContext();
				int trn = (int) ctx.getAttribute("rollno");
				System.out.println(trn);
				ps = con.prepareStatement("delete from cbsc where rollno=?");
				ps.setInt(1, trn);
				ps.executeUpdate();
				ps = con.prepareStatement("delete from cbsccheck where rn=?");
				ps.setInt(1, trn);
				ps.executeUpdate();
				RequestDispatcher rd = request.getRequestDispatcher("index.html");
				rd.include(request, response);

			}
			else
			{
				out.println("<html><body> Deletion Incomplete");
				out.println("<br><a href=\"homepage.html\"> Go to Homepage </a>");
				out.println("<br><a href=\"del.html\"> Retry Deletion </a>");
			}
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
