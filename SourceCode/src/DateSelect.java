

import java.io.*;
import java.sql.*;
import java.util.ArrayList;
import java.util.Calendar;

import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Servlet implementation class BookTwo
 */
@WebServlet("/dateSelect")
public class DateSelect extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public DateSelect() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();

		String doc = request.getParameter("docname");
		String[] docDetails = doc.split("\\.");
		int docNumber = Integer.parseInt(docDetails[0]);
		//String docname = docDetails[2];
		/*String date = request.getParameter("date");
		String day = date.substring(0,3);
		String month = date.substring(4,7);
		String monthNumber = "";
		switch(month){
		case "Jan": monthNumber = "01"; break;
		case "Feb": monthNumber = "02"; break;
		case "Mar": monthNumber = "03"; break;
		case "Apr": monthNumber = "04"; break;
		case "May": monthNumber = "05"; break;
		case "Jun": monthNumber = "06"; break;
		case "Jul": monthNumber = "07"; break;
		case "Aug": monthNumber = "08"; break;
		case "Sep": monthNumber = "09"; break;
		case "Oct": monthNumber = "10"; break;
		case "Nov": monthNumber = "11"; break;
		case "Dec": monthNumber = "12"; break;
		}
		String dayNumber = date.substring(8,10);
		String year = date.substring(11,15);*/

		Calendar currentDate = Calendar.getInstance();
		Calendar date = currentDate;
		ArrayList<String> dateList = new ArrayList<>();

		for(int i=0; i<15; i++){
			String tempDate = date.getTime().toString();
			dateList.add(tempDate.substring(0, 11) + tempDate.substring(24));
			date.add(Calendar.DATE, 1);
		}

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
				"	<form name=\"backtodocselect\" id=\"backtodocselect\" action=\"docSelect\"></form>\n" + 
				"	<form name=\"dateselect\" action=\"description\" method=\"post\">\n" +
				"	<fieldset>\n" +
				"		<legend>Booking Information</legend>\n" +
				"		<table id=\"bookingInfo\">\n" +
				"			<tr><td class=\"label\">Select a Doctor:</td><td class=\"choice\">" + request.getParameter("docname") + "</td></tr>\n" + 
				"			<tr><td class=\"label\">Select a Date:</td><td class=\"choice\"><select name=\"appdate\">\n");

		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","root");

			PreparedStatement ps = con.prepareStatement("Select DOCTOR_ID from DOCTOR_DETAILS ORDER BY  DOCTOR_ID");
			ResultSet res = ps.executeQuery();
			while(docNumber != 0){
				res.next();
				docNumber--;
			}
			String docId = res.getString("DOCTOR_ID");
			//System.out.println(docId);

			ArrayList<String> toBeDeleted = new ArrayList<>();
			for(String s: dateList){
				//System.out.println(s.substring(0,3).toUpperCase());
				ps = con.prepareStatement("Select " + s.substring(0,3).toUpperCase() + " from DAILY where DOCTOR_ID=" + docId);
				res = ps.executeQuery();
				if(res.next()){
					//System.out.println(res.getString(s.substring(0,3).toUpperCase()));
					if(res.getString(s.substring(0,3).toUpperCase()).equals("YES")){
						PreparedStatement tempPS = con.prepareStatement("Select " + s.substring(0,3).toUpperCase() + " from MAXIMUM_APPOINTMENT where DOCTOR_ID=" + docId);
						ResultSet tempRes = tempPS.executeQuery();
						if(tempRes.next()){
							int maxAppointments = Integer.parseInt(tempRes.getString(s.substring(0,3).toUpperCase()));
							//System.out.println(maxAppointments);
							tempPS = con.prepareStatement("Select COUNT from COUNT_APPOINTMENT where DOCTOR_ID=" + docId + " and APP_DATE='" + s.substring(8,10) + "-" + s.substring(4,7) + "-" + s.substring(11) + "'");
							tempRes = tempPS.executeQuery();
							if(tempRes.next()){
								//System.out.println(Integer.parseInt(tempRes.getString("COUNT")));
								if(Integer.parseInt(tempRes.getString("COUNT")) == maxAppointments){
									toBeDeleted.add(s);
								}
							}
						}
					}
					else{
						toBeDeleted.add(s);
					}
				}
			}

			for(String s: toBeDeleted)
				dateList.remove(s);

			for(String s: dateList){
				out.println("<option>" + s + "</option>\n");

			}

		} catch (Exception e) {} 
		finally {
			out.println("			</select></td><tr>\n" + 
					"			<tr><td class=\"label\"><input type=\"submit\" value=\"Prev\" form=\"backtodocselect\"></input></td><td class=\"choice\"><input type=\"submit\" value=\"Next\"></input></td><tr>\n" + 
					"		</table>\n" +
					"	</fieldset>\n" +
					"	<input type=\"hidden\" name=\"docname\" value=\"" + doc +"\"></input>" +  
					"	</form>\n" +
					"	</div>\n" +
					"	<div id=\"footer\">\n" +
					"	</div>\n" +
					"</body>\n" +
					"</html>");
			out.close();
		}
	}

	/*	private String add15(String dayNumber, String monthNumber, String year) {
		// TODO Auto-generated method stub
		int d = Integer.parseInt(dayNumber);
		int m = Integer.parseInt(monthNumber);
		int y = Integer.parseInt(year);

		if(m==1 || m==3 || m==5 || m==7 || m==8 || m==10){
			if(d+14 < 32){
				dayNumber = (d+14) + "";
			}
			else{
				d = (d+14)%31;
				if (d<10)
					dayNumber = "0" + d;
				else
					dayNumber = d + "";

				m++;
				if (m<10)
					monthNumber = "0" + m;
				else
					monthNumber = m + "";
			}			
		}
		else if(m==4 || m==6 || m==9 || m==11){
			if(d+14 < 31){
				dayNumber = (d+14) + "";
			}
			else{
				d = (d+14)%30;
				if (d<10)
					dayNumber = "0" + d;
				else
					dayNumber = d + "";

				m++;
				if (m<10)
					monthNumber = "0" + m;
				else
					monthNumber = m + "";
			}
		}
		else if(m==12){
			if(d+14 < 32){
				dayNumber = (d+14) + "";
			}
			else{
				d = (d+14)%31;
				if (d<10)
					dayNumber = "0" + d;
				else
					dayNumber = d + "";

				monthNumber = "01";

				y++;
				year = y + "";
			}
		}
		else{
			if(isLeap(y)){
				if(d+14<30){
					dayNumber = (d+14) + "";
				}
				else{
					d = (d+14)%29;
					if (d<10)
						dayNumber = "0" + d;
					else
						dayNumber = d + "";

					monthNumber = "03"; 
				}
			}
			else{
				if(d+14<29){
					dayNumber = (d+14) + "";
				}
				else{
					d = (d+14)%28;
					if (d<10)
						dayNumber = "0" + d;
					else
						dayNumber = d + "";

					monthNumber = "03"; 
				}
			}
		}

		return (year + "-" + monthNumber + "-" + dayNumber);
	}

	private boolean isLeap(int y) {
		// TODO Auto-generated method stub
		if(y % 4 == 0)
			if (y % 100 == 0 && y % 400 != 0)
				return false;
			else
				return true;
		else
			return false;

	}*/

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	}

}
