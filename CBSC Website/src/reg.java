import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

import javax.servlet.RequestDispatcher;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@WebServlet("/reg")
public class reg extends HttpServlet {
	PreparedStatement ps;
	protected void service(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		response.setContentType("text/html");
		PrintWriter out = response.getWriter();
		String name = request.getParameter("name");
		int age = Integer.parseInt(request.getParameter("age"));
		int grade = Integer.parseInt(request.getParameter("grade"));
		int rn = Integer.parseInt(request.getParameter("rn"));
		
		int m1 = Integer.parseInt(request.getParameter("m1"));
		int m2 = Integer.parseInt(request.getParameter("m2"));
		int m3 = Integer.parseInt(request.getParameter("m3"));
		int m4 = Integer.parseInt(request.getParameter("m4"));
		int m5 = Integer.parseInt(request.getParameter("m5"));
		
		String pass = request.getParameter("pass");
		String cpass = request.getParameter("cpass");
		try {
			Connection con = DBConnection.getConn();
			
			if(pass.equals(cpass))
			{
				ps = con.prepareStatement("insert into cbsc values(?,?,?,?,?,?,?,?,?,?)");
				ps.setString(1, name);
				ps.setInt(2, age);
				ps.setInt(3, grade);
				ps.setInt(4, rn);
				ps.setInt(5, m1);
				ps.setInt(6, m2);
				ps.setInt(7, m3);
				ps.setInt(8, m4);
				ps.setInt(9, m5);
				ps.setString(10, name);
				ps.executeUpdate();
				
				ps = con.prepareStatement("insert into cbsccheck values(?,?,?)");
				ps.setString(1, name);
				ps.setInt(2, rn);
				ps.setString(3, pass);
				ps.executeUpdate();
				out.println("<html><body> Registration successful. Login Below");
				RequestDispatcher rd = request.getRequestDispatcher("login.html");
				rd.include(request, response);
			}
			else
			{
				out.println("<html><body> 'Password' and 'Confirm Password' do not match!");
				RequestDispatcher rd = request.getRequestDispatcher("registration.html");
				rd.include(request, response);
			}
			
			
		
		} catch (ClassNotFoundException | SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
