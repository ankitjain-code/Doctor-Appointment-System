

import java.io.*;
import java.sql.*;
import javax.servlet.*;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.*;

/**
 * Servlet implementation class CancelAppointment
 */
@WebServlet("/cancel")
public class CancelAppointment extends HttpServlet {
	private static final long serifralVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public CancelAppointment() {
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
		HttpSession session = request.getSession();
		String user = (String)session.getAttribute("user");
		
		try
		{
			Class.forName("oracle.jdbc.driver.OracleDriver");
			Connection con = DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:XE","system","root");
			Statement st = con.createStatement();
			int x = Integer.parseInt(request.getParameter("buttonCount"));
			int num = 0;
			for(int i=1; i<=x; i++){
				if(request.getParameter("cancel" + i) != null){
					num = i;
					break;
				}
			}
			
			ResultSet rs = st.executeQuery("Select * from APPOINTMENT_DETAILS where PATIENT_ID = '"+ user +"' order by APP_DATE");
			while(num>0){
				rs.next();
				num--;
			}
			String date = rs.getString("APP_DATE");
			String tempDate = date.substring(0, 10);
			String monthName = "";
			switch(tempDate.substring(5,7)){
			case "01": monthName = "JAN"; break;
			case "02": monthName = "FEB"; break;
			case "03": monthName = "MAR"; break;
			case "04": monthName = "APR"; break;
			case "05": monthName = "MAY"; break;
			case "06": monthName = "JUN"; break;
			case "07": monthName = "JUL"; break;
			case "08": monthName = "AUG"; break;
			case "09": monthName = "SEP"; break;
			case "10": monthName = "OCT"; break;
			case "11": monthName = "NOV"; break;
			case "12": monthName = "DEC"; break;
			}
			String appdate = tempDate.substring(8,10) + "-" + monthName + "-" + tempDate.substring(0,4); 
			String docId = rs.getString("DOCTOR_ID");
			
			PreparedStatement delPS = con.prepareStatement("Delete from APPOINTMENT_DETAILS where PATIENT_ID='" + user + "' and APP_DATE='" + appdate + "'");
			delPS.executeUpdate();
			
			PreparedStatement ps = con.prepareStatement("Select COUNT from COUNT_APPOINTMENT where DOCTOR_ID='" + docId + "' and APP_DATE='" + appdate + "'");
			rs = ps.executeQuery();
			if(rs.next()){
				int count = Integer.parseInt(rs.getString("COUNT"));
				if(count > 1){
					count--;
					PreparedStatement tempPS = con.prepareStatement("Update COUNT_APPOINTMENT set COUNT='" + count + "' where DOCTOR_ID='" + docId + "' and APP_DATE='" + appdate + "'");
					tempPS.executeUpdate();
				}
				else{
					PreparedStatement tempPS = con.prepareStatement("Delete from COUNT_APPOINTMENT where DOCTOR_ID='" + docId + "' and APP_DATE='" + appdate + "'");
					tempPS.executeUpdate();
				}
			}
			
			out.println("<script type=\"text/javascript\">");
			out.println("alert('Successfully cancelled your Appointment');");
			out.println("location='index.html';");
			out.println("</script>");
		}
		catch (Exception e){e.printStackTrace();}

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
