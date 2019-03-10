

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Servlet implementation class Login
 */
@WebServlet("/login")
public class Login extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Login() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		response.setContentType("text/html");        
		String id = request.getParameter("id");
		String password = request.getParameter("password");
		
		HttpSession csession = request.getSession(false);
	
	if(csession==null)	
	{

		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","root");
			Statement st = con.createStatement();
			ResultSet rs = st.executeQuery("Select * from PATIENT_DETAILS where PATIENT_ID = '" + id+"' and PASSWORD ='"+ password+"'  ");

			if(rs.next())
			{
				HttpSession session = request.getSession();
				session.setAttribute("user", id);
				response.setContentType("text/html");
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Login Successful');");
				out.println("location='index.html';");
				out.println("</script>");
			}
			else
			{
				response.setContentType("text/html");
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Try Logging in again..');");
				out.println("location='login.html';");
				out.println("</script>");
			}
			
		}
		catch (Exception e){}
	}
	else
	{
		response.setContentType("text/html");
		out.println("<script type=\"text/javascript\">");
		out.println("alert('You have already logged in..');");
		out.println("location='index.html';");
		out.println("</script>");
		
	}
	out.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
