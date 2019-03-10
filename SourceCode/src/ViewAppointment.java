

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;
/**
 * Servlet implementation class ViewAppointment
 */
@WebServlet("/view")
public class ViewAppointment extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public ViewAppointment() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out=response.getWriter();
		response.setContentType("text/html");    

		HttpSession session = request.getSession(false);
		

		if(session != null)
		{
			String user = (String)session.getAttribute("user");
			try
			{
				Class.forName("oracle.jdbc.driver.OracleDriver");
				Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","root");
				Statement st = con.createStatement();     
				ResultSet rs = st.executeQuery("Select * from APPOINTMENT_DETAILS where PATIENT_ID = '"+ user +"' order by APP_DATE");

				if(rs.next())
				{
					out.println("<!DOCTYPE html>\n" +
							"<html>\n" +
							"<head>\n" +
							"<title>Hospital Management System</title>\n" +
							"<link rel=\"stylesheet\" type=\"text/css\" href=\"css/common.css\">\n" +
							"<link rel=\"stylesheet\" type=\"text/css\" href=\"css/view.css\">\n" +
							"</head>\n" +
							"<body>\n" + 
							"	<div id=\"header\">\n" +	
							"		<img id=\"logo\" src=\"images/hospital_logo.jpg\" alt=\"hospital logo\">\n" +
							"		<img class=\"nav-image\" src=\"images/logout.jpg\" alt=\"logout\" style=\"float: right; padding-right: 10%\">\n" +
							"		<a href=\"index.html\"><img class=\"nav-image\" src=\"images/home.jpg\" alt=\"home\" style=\"float: right; padding-right: 1%\"></a>\n" +
							"		<br>\n" +
							"		<img id=\"landing\" src=\"images/landing_1.jpg\" alt=\"landing image\">\n" +
							"		<br>\n" +
							"	</div>\n" +
							"	<div id=\"viewcancel\">\n" +
							"	<form name=\"cancel\" action=\"cancel\" method=\"post\">\n" +
							"	<fieldset>\n" +
							"		<legend>Booking Information</legend>\n" +
							"		<table id=\"viewCancel\">\n" +
							"			<tr><th class=\"doc\">Doctor</th><th class=\"date\">Date</th><th class=\"desc\">Description</th><th class=\"can\"></th></tr>\n");

					int count = 0;
					do{
						count++;
						String date = rs.getString("APP_DATE");
						String appdate = date.substring(0, 11);
						String desc=	rs.getString("DESCRIPTION");
						String docId=	rs.getString("DOCTOR_ID");
						Statement st2 = con.createStatement();
						ResultSet rs2 = st2.executeQuery("Select FIRST_NAME,LAST_NAME from DOCTOR_DETAILS where DOCTOR_ID = '" + docId + "' ");
						String docname = "";
						if(rs2.next())
							docname = rs2.getString("FIRST_NAME") + " " + rs2.getString("LAST_NAME");
						out.println("<tr><td class=\"doc\">" + docname + "</td><td class=\"date\">" + appdate + "</td><td class=\"desc\">" + desc + "</td><td class=\"can\"><input type=\"submit\" name=\"cancel"+ count + "\" value=\"Cancel\"></td></tr>\n");
					}while(rs.next());

					out.println("		</table>\n" +
							"	</fieldset>\n" +
							"	<input type=\"hidden\" name=\"buttonCount\" value=\"" + count + "\">\n" + 
							"	</form>\n" +
							"	</div>\n" +
							"	<div id=\"footer\">\n" +
							"	</div>\n" +
							"</body>\n" +
							"</html>");
				}
				else
				{
					out.println("<script type=\"text/javascript\">");
					out.println("alert('You have No Appointments Scheduled');");
					out.println("location='index.html';");
					out.println("</script>");
				}
			}
			catch (Exception e){}
			out.close();
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
