package com.aruna.jaxws.servlets;

import java.io.IOException;
import java.net.URL;
import java.sql.*;


import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.xml.namespace.QName;
import javax.xml.ws.Service;

import com.aruna.jaxws.beans.Booking;
import com.aruna.jaxws.resources.FlightService;
import com.aruna.jaxws.resources.SendEmail;
import com.aruna.jaxws.services.BookingServices;

/**
 * Servlet implementation class BookingServlet
 */
@WebServlet(description = "booking servlet", urlPatterns = { "/BookingServlet" })
public class BookingServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BookingServlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.getWriter().append("Served at: ").append(request.getContextPath());
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doGet(request, response);
	
	String firstname = request.getParameter("firstname");
    String lastname= request.getParameter("lastname");
    String address = request.getParameter("address");
    String phone = request.getParameter("phone");
    String dob=request.getParameter("dob");
    String passport=request.getParameter("passport");
    String card=request.getParameter("card");
    String email=request.getParameter("email");
    BookingServices bs;
	try {
	URL wsdlURL = new URL("http://localhost/SOAPBookingAPI/bookingWS?wsdl");
	//check above URL in browser, you should see WSDL file
	
	//creating QName using targetNamespace and name
	QName qname = new QName("http://services.jaxws.aruna.com/", "BookingServiceImplService"); 
	Service service = Service.create(wsdlURL, qname);  
	
	//We need to pass interface and model beans to client
	 bs= service.getPort(BookingServices.class);
	
	
	Booking b1 = new Booking(); 
	b1.setFirstName(firstname);
	b1.setLastName(lastname);
	b1.setPhoneNumber(phone);
	b1.setAddress(address);
	b1.setDateOfBirth(dob);
	b1.setEmail(email);
	b1.setCardNumber(card);
	b1.setNumberOfPassengers(Integer.parseInt(FlightService.noOfPassengers));
	b1.setDepartureId(Integer.parseInt(FlightService.departureId));
	b1.setReturnId(Integer.parseInt(FlightService.arrivalId));
	 try{ 
			//Class.forName("com.mysql.jdbc.Driver").newInstance();
		    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
		   Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/myvacationdb", "root", "root");
		     	String sql = "insert into myvacationdb.bookings (firstname,lastname,address,phone,email,dob,departureId,arrivalId,"
		     			+ "noOfPassengers,card) values(?,?,?,?,?,?,?,?,?,?)";
		     	
		     	PreparedStatement pst=conn.prepareStatement(sql);
		     	pst.setString(1,b1.getfirstName());
		     	pst.setString(2, b1.getLastName());
		     	pst.setString(3, b1.GetAddress());
		     	pst.setString(4, b1.getPhoneNumber());
		     	pst.setString(5, b1.getEmail());
		     	pst.setString(6, b1.getDateOfBirth());
		     	pst.setInt(7, b1.getDepartureId());
		     	pst.setInt(8, b1.getReturnId());
		     	pst.setInt(9, b1.getNumberOfPassengers());
		     	pst.setString(10, b1.getcardNumber());
		     	
		     	
		         pst.execute();
		         String sql2="select id from myvacationdb.bookings where dob='"+b1.getDateOfBirth()+"'";
		         pst=conn.prepareStatement(sql2);
		         ResultSet rs=pst.executeQuery();
		         while(rs.next()) {
		        	 b1.setBookingId(rs.getInt("id"));
		        	        
	//add person
	
	System.out.println("Add Booking Status="+bs.addBooking(b1));
	System.out.println(bs.getBooking(b1.getBookingId()));
	System.out.println(bs.getAllBookings());
	String message="booking details conformed on name\n <"+b1.getfirstName()+" "+b1.getLastName()+">\n"+
			        "<Phone Number:"+b1.getPhoneNumber()+">\n"+
			        "<address: "+b1.GetAddress()+">";
	                
	SendEmail s=new SendEmail(b1.getEmail(),"Booking conformation",message);
	  System.out.println("success");
	
		         }
		         conn.close();
		         rs.close();
		  }catch(Exception e) {
		        	 System.out.println(e);
		        	 }	
	
	}catch(Exception e) {
		System.out.println(e);
	}
    response.sendRedirect("conformation.jsp");
}


}
