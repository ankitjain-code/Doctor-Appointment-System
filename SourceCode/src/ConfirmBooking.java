

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ConfirmBooking
 */
@WebServlet("/confirmBooking")
public class ConfirmBooking extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ConfirmBooking() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		PrintWriter out = response.getWriter();
		
		HttpSession session = request.getSession();
		String pID = (String)session.getAttribute("user");
		String doc = request.getParameter("docname");
		String[] docDetails = doc.split("\\.");
		int docNumber = Integer.parseInt(docDetails[0]);
		String date = request.getParameter("appdate");
		String appdate = date.substring(8,10) + "-" + date.substring(4,7) + "-" + date.substring(11);
		String desc = request.getParameter("desc");
		
		try{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","system","root");

			PreparedStatement ps = con.prepareStatement("Select DOCTOR_ID from DOCTOR_DETAILS");
			ResultSet res = ps.executeQuery();
			while(docNumber != 0){
				res.next();
				docNumber--;
			}
			String docId = res.getString("DOCTOR_ID");
			
			ps = con.prepareStatement("Insert into APPOINTMENT_DETAILS(PATIENT_ID, APP_DATE, DESCRIPTION, DOCTOR_ID)"
					+ "values('" + pID + "','" + appdate + "','" + desc + "','" + docId + "')");
			
			int i = ps.executeUpdate();
			if (i > 0){
				ps = con.prepareStatement("Select COUNT from COUNT_APPOINTMENT where DOCTOR_ID=" + docId + " and APP_DATE='" + appdate + "'");
				res = ps.executeQuery();
				int count = 0;
				if(res.next()){
					count = Integer.parseInt(res.getString("COUNT"));
					count++;
					PreparedStatement tempPS = con.prepareStatement("Update COUNT_APPOINTMENT set COUNT='" + count + "' where DOCTOR_ID='" + docId +"'");
					tempPS.executeUpdate();
				}
				else{
					count++;
					PreparedStatement tempPS = con.prepareStatement("Insert into COUNT_APPOINTMENT(DOCTOR_ID, APP_DATE, COUNT)"
							+ "values('" + docId + "','" + appdate + "','" + count + "')");
					tempPS.executeUpdate();
				}
				
				response.setContentType("text/html");
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Booking Successful..');");
				out.println("location='index.html';");
				out.println("</script>");
			}
			else{
				response.setContentType("text/html");
				out.println("<script type=\"text/javascript\">");
				out.println("alert('Booking Unsuccessful.. Try Booking Again!!');");
				out.println("location='index.html';");
				out.println("</script>");
			}
			
		} catch (Exception e) {
			response.setContentType("text/html");
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Booking Unsuccessful.. Try Booking Again!!');");
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
