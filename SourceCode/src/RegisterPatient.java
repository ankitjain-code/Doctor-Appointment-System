

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Servlet implementation class RegisterPatient
 */
@WebServlet("/register")
public class RegisterPatient extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public RegisterPatient() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		

		String id = request.getParameter("id");
		String password = request.getParameter("password");
		String fname = request.getParameter("fname");
		String lname = request.getParameter("lname");
		String age = request.getParameter("age");
		String gender = request.getParameter("gender");
		String contact = request.getParameter("contact");
		String email = request.getParameter("email");
		String address = request.getParameter("address");
		
		HttpSession csession = request.getSession(false);
		
		if(csession==null)	
		{
		
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","root");

			PreparedStatement ps=con.prepareStatement("insert into PATIENT_DETAILS(PATIENT_ID, PASSWORD, FIRST_NAME, LAST_NAME, AGE, GENDER, CONTACT_NO, EMAIL, ADDRESS)"
					+ "values('" + id + "','" + password + "','" + fname + "','" + lname + "','" + age + "','" + gender + "','" + contact + "','" + email + "','" + address + "')");

			int i=ps.executeUpdate();
			if (i > 0){
				response.setContentType("text/html");
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Registration Successful!!');");
				out.println("location='index.html';");
				out.println("</script>");
				
			}
			else{
				
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Please Login to Continue..');");
				out.println("location='registration.html';");
				out.println("</script>");
				
			}

		} catch (Exception e) {
		
			response.sendRedirect("registration.html");
		} finally {
			out.close();
		}
		}
		else
		{
			response.setContentType("text/html");
			out.println("<script type=\"text/javascript\">");
			out.println("alert('You have already registerd ..');");
			out.println("location='index.html';");
			out.println("</script>");
		}
		}
		
	

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
