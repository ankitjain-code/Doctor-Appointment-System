

import java.io.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Servlet implementation class BookThree
 */
@WebServlet("/description")
public class Description extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Description() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();

		out.println("<!DOCTYPE html>\n" +
				"<html>\n" +
				"<head>\n" +
				"<title>Hospital Management System</title>\n" +
				"<link rel=\"stylesheet\" type=\"text/css\" href=\"css/common.css\">\n" +
				"<link rel=\"stylesheet\" type=\"text/css\" href=\"css/booking.css\">\n" + 
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
				"	<div id=\"booking\">\n" +
				"	<form name=\"backtodateselect\" id=\"backtodateselect\" action=\"dateSelect\"><input type=\"hidden\" name=\"docname\" value=\"" + request.getParameter("docname") + "\"></input></form>\n" + 
				"	<form name=\"description\" action=\"confirmBooking\" method=\"post\">\n" +
				"	<fieldset>\n" +
				"		<legend>Booking Information</legend>\n" +
				"		<table id=\"bookingInfo\">\n" +
				"			<tr><td class=\"label\">Select a Doctor:</td><td class=\"choice\">" + request.getParameter("docname") + "</td></tr>\n" + 
				"			<tr><td class=\"label\">Select a Date:</td><td class=\"choice\">" + request.getParameter("appdate") + "</td></tr>\n" + 
				"			<tr><td class=\"label\">Description:</td><td class=\"choice\"><textarea name=\"desc\" cols=\"50\" rows=\"8\" placeholder=\"Briefly describe the patient's condition.. For example, mention the symptoms,etc..\"></textarea></td></tr>\n" +
				"			<tr><td class=\"label\"><input type=\"submit\" value=\"Prev\" form=\"backtodateselect\"></input></td><td class=\"choice\"><input type=\"submit\" value=\"Next\"></input></td><tr>\n" + 
				"		</table>\n" +
				"	</fieldset>\n" +
				"	<input type=\"hidden\" name=\"docname\" value=\"" + request.getParameter("docname") + "\"></input>" +  
				"	<input type=\"hidden\" name=\"appdate\" value=\"" + request.getParameter("appdate") + "\"></input>" +
				"	</form>\n" +
				"	</div>\n" +
				"	<div id=\"footer\">\n" +
				"	</div>\n" +
				"</body>\n" +
				"</html>");
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
