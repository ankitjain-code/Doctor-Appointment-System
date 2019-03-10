

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Servlet implementation class Book
 */
@WebServlet("/docSelect")
public class DocSelect extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DocSelect() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		HttpSession session = request.getSession(false);
		//String user = (String)session.getAttribute("user");

		if(session != null){
			out.println("<!DOCTYPE html>\n" +
					"<html>\n" +
					"<head>\n" +
					"<title>Hospital Management System</title>\n" +
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"css/common.css\">\n" +
					"<link rel=\"stylesheet\" type=\"text/css\" href=\"css/booking.css\">\n" +
					/*"<script>\n" + 
		"function setDateValue() {\n" +
		"	var d = new Date();\n" +
		"	document.getElementById(\"date\").value = d.toDateString();\n" +
		"}\n" + 
		"</script>\n" +*/
		"</head>\n" +
		"<body>\n" + // onload=\"setDateValue()\">\n" +
		"	<div id=\"header\">\n" +	
		"		<img id=\"logo\" src=\"images/hospital_logo.jpg\" alt=\"hospital logo\">\n" +
		"		<img class=\"nav-image\" src=\"images/logout.jpg\" alt=\"logout\" style=\"float: right; padding-right: 10%\">\n" +
		"		<a href=\"index.html\"><img class=\"nav-image\" src=\"images/home.jpg\" alt=\"home\" style=\"float: right; padding-right: 1%\"></a>\n" +
		"		<br>\n" +
		"		<img id=\"landing\" src=\"images/landing_1.jpg\" alt=\"landing image\">\n" +
		"		<br>\n" +
		"	</div>\n" +
		"	<div id=\"booking\">\n" +
		"	<form name=\"doctorname\" action=\"dateSelect\" method=\"post\">\n" +
		"	<fieldset>\n" +
		"		<legend>Booking Information</legend>\n" +
		"		<table id=\"bookingInfo\">\n" +
					"			<tr><td class=\"label\">Select a Doctor:</td><td class=\"choice\"><select name=\"docname\">\n");

			try{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","root");

				PreparedStatement ps=con.prepareStatement("Select DOCTOR_ID, FIRST_NAME, LAST_NAME from DOCTOR_DETAILS ORDER BY  DOCTOR_ID");

				ResultSet res = ps.executeQuery();

				int count = 0;
				while(res.next()){
					count++;
					String name = count + ". Dr. " + res.getString("FIRST_NAME") + " " + res.getString("LAST_NAME");
					out.println("<option>" + name + "</option>\n");
				}

			} catch (Exception e) {} 
			finally {
				out.println("			</select></td></tr>\n" +
						"			<tr><td class=\"label\"><input type=\"button\" value=\"Prev\" disabled></input></td><td class=\"choice\"><input type=\"submit\" value=\"Next\"></input></td><tr>\n" + 
						"		</table>\n" +
						"	</fieldset>\n" +
						//"	<input type=\"hidden\" id=\"date\" name=\"date\"></input>\n" + 
						"	</form>\n" +
						"	</div>\n" +
						"	<div id=\"footer\">\n" +
						"	</div>\n" +
						"</body>\n" +
						"</html>");
				out.close();
			}
		}
		else{
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Please Login to Continue..');");
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
