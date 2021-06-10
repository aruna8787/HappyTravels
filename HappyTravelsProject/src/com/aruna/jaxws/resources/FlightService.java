package com.aruna.jaxws.resources;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DecimalFormat;

import javax.jws.WebMethod;
import javax.jws.WebService;
import javax.persistence.Entity;
import javax.ws.rs.FormParam;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.QueryParam;
import javax.ws.rs.core.MediaType;


@WebService()
@Entity
@Path("/flights")
public class FlightService {
	
		public static String departureId="";
		public static String arrivalId="";
		public static String requestedCurrency="";
		 public static String fromCurrency="USD";
		public static String noOfPassengers;
		public static  String tocurrency;
		
		
	    @GET
	    @Path("/results")
	    @Produces("text/html")
	    @WebMethod(operationName = "getDepartureFlights")
	    public String getDepartureFlights(@QueryParam("trip") String trip,@QueryParam("departureAirport") String departureAirport,@QueryParam("arrivalAirport") String arrivalAirport,
	    		@QueryParam("startDate") String startDate,@QueryParam("endDate") String endDate,@QueryParam("currency") String toCurrency,@QueryParam("passengers") String noOfPassengers) 
	    {
	    	this.noOfPassengers=noOfPassengers;
	    	this.tocurrency=toCurrency;
	    	
	    	String details="";
	        try 
	        {
	            Class.forName("com.mysql.jdbc.Driver");
	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myvacationdb", "root", "root");
	            String query = "select * from flightdata where departureAirportCode=? and arrivalAirportCode=? and startDate=? and endDate=? ";

	            PreparedStatement st = con.prepareStatement(query);
	            st.setString(1, departureAirport);
	            st.setString(2, arrivalAirport);
	            st.setString(3,startDate);
	            st.setString(4,endDate);
	            
	            ResultSet rs=st.executeQuery(); 
	            String action="";
	            if(trip.equalsIgnoreCase("roundtrip")) {action="\"departure\"";}
	            else {action="\"booking\"";}
	            details = "<html><body  style=\"color: white\"  bgcolor=\"#A9A9A9\">"+
	            		"<h1 align="+"center"+">Departure Flights</h1>"+
	            		"<form style=\"background-color:#008B8B\" action="+action+ " method=\"GET\">"; 
	            		
	            details = details + "<table border=1>";
	            details = details + "<tr><td><Strong>Id </Strong></td>" +
	                                    "<td><Strong> Departure City </Strong></td>" +
	                                    "<td><Strong> Departure Date</Strong></td>" +
	                                    "<td><Strong> Departure Time</Strong></td>"+
	                                    "<td><Strong> Arrival City </Strong></td>" + 
	                                    "<td><Strong> Arrival Date</Strong></td>" + 
	                                    "<td><Strong> Arrival Time</Strong></td>" + 
	                                    "<td><Strong> Price in"+tocurrency+ "</Strong></td>" +
	                                    "<td><Strong> Flight Number </Strong></td>"+
	                                    "<td><Strong> Flight Duration</Strong></td>" +
	                                    "<td><Strong> Airline Name </Strong></td>"+
	                                    "<td><Strong> Stops</Strong></td>" +
	              
	                                    "<td><Strong> Submit</Strong></td>" +"</tr>";
	            String price="";
	        	CurrencyConverter crt;
	            while (rs.next()) 
	            	
	            {   
	            	if(toCurrency=="USD") {price=rs.getString("roundPrice").toString();}
	            	else {
	            		//converting price to different currencies according to user  tocurrency
	            	String tempPrice=rs.getString("roundPrice").toString();
	                crt=new CurrencyConverter(fromCurrency,toCurrency,tempPrice);
	                
	                DecimalFormat format=new DecimalFormat("##.00");
	               
	                String StringPrice=crt.getCurrencyRate();
	        		price=format.format(Double.parseDouble(StringPrice));
	        		 
	            	
	            	}
	        		 //getting airline website url to each airline
	        		 String iata_code=(rs.getString("flightNumber")).substring(0,2);
	        		 String website=Airline.getWebsite(iata_code);
	        		 if(website==null) {website="http://www.google.com";}
	        		 
	                details = details + "<tr><td>" + rs.getInt("id")+"</td>" +
	                                        "<td>" + rs.getString("departureCity") +"</td>" +
	                                        "<td>" + rs.getString("departureDate") + "</td>" +
	                                        "<td>" + rs.getString("departureTime") + "</td>" +
	                                        "<td>" + rs.getString("arrivalCity") + "</td>" +
	                                        "<td>" + rs.getString("arrivalDate") + "</td>" +
	                                        "<td>" + rs.getString("arrivalTime") + "</td>" +
	                                        "<td>" + price + "</td>" +
	                                        "<td>" + rs.getString("flightNumber") + "</td>" +
	                                        "<td>" + rs.getString("flightDuration") + "</td>" +
	                                        "<td >" +"<a href=\""+website+"\">"+ rs.getString("airlineName") + "</a>"+"</td>" +
	                                        "<td>" + rs.getString("stops") + "</td>"+
	                                        "<td><input type=\"submit\" value=\""+rs.getString("id")+"\" name=\"departureId\""
	                                        		+ " style=\"background-color:#49743D;font-weight:bold;color:#ffffff\">"+
	                                       "</td></tr>";
	            }
	            details += "</table></body></html>"; 
	        } 
	        catch (Exception e) 
	        {
	            System.out.println(e.getMessage());
	        }
	        return details;
	    }

	    @GET
	    @Path("/departure")
	    @Produces("text/html")
	    @WebMethod(operationName ="getReturnFlights")
	    public String getReturnFlights(@QueryParam("departureId") String departureId)
	    		 
	    {
	    	this.departureId=departureId;
	        ResultSet rs = null;
	        String details = ""; 
	        try 
	        {
	        	Class.forName("com.mysql.cj.jdbc.Driver");
	            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myvacationdb", "root", "root");

	            String query = "SELECT * FROM flightdata WHERE id=? "; 
	            PreparedStatement st = con.prepareStatement(query);
	            st.setString(1, departureId);
	             rs=st.executeQuery();
	            	while(rs.next()) {
	            String departureAirport=rs.getString("departureAirportCode");
	            String arrivalAirport=rs.getString("arrivalAirportCode");
	            String startDate=rs.getString("startDate");
	            String endDate=rs.getString("endDate");
	            String departureDate=endDate.substring(1);
	            	
	            String query1="select * from flightdata where departureAirportCode=? and arrivalAirportCode=? and departureDate=? and "
	            		+ "startDate=? and endDate=?";
	            st=con.prepareStatement(query1);
	            st.setString(1, arrivalAirport);
	            st.setString(2, departureAirport);
	            st.setString(3, departureDate);
	            st.setString(4, startDate);
	            st.setString(5, endDate);
	            
	            	
	            
	            ResultSet rs1 = st.executeQuery();

	            details = "<html><body  style=\"color: white\"  bgcolor=\"#A9A9A9\">"+
	            		"<h1 align="+"center"+"> return flight results for</h1>"+
	            		"<form style=\"background-color:#008B8B\" action=\"booking\" method=\"GET\">"; 
	            		
	            details = details + "<table border=1>";
	            details = details + "<tr><td><Strong>Id </Strong></td>" +
	                                    "<td><Strong> Departure City </Strong></td>" +
	                                    "<td><Strong> Departure Date</Strong></td>" +
	                                    "<td><Strong> Departure Time</Strong></td>"+
	                                    "<td><Strong> Arrival City </Strong></td>" + 
	                                    "<td><Strong> Arrival Date</Strong></td>" + 
	                                    "<td><Strong> Arrival Time</Strong></td>" + 
	                                    "<td><Strong> Price in"+ tocurrency+"</Strong></td>" +
	                                    "<td><Strong> Flight Number </Strong></td>"+
	                                    "<td><Strong> Flight Duration</Strong></td>" +
	                                    "<td><Strong> Airline Name </Strong></td>"+
	                                    "<td><Strong> Stops</Strong></td>" +
	                                   "<td><Strong> Submit</Strong></td>" +"</tr>";
	            String price="";
	        	CurrencyConverter crt;
	            while (rs1.next()) 
	            {
	            	if(tocurrency=="USD") {price=rs1.getString("roundPrice").toString();}
	            	else {
	            		//converting price to different currencies according to user  tocurrency
	            	String tempPrice=rs.getString("roundPrice").toString();
	                crt=new CurrencyConverter(fromCurrency,tocurrency,tempPrice);
	                
	                DecimalFormat format=new DecimalFormat("##.00");
	               
	                String StringPrice=crt.getCurrencyRate();
	        		price=format.format(Double.parseDouble(StringPrice));
	        		 
	            	
	            	}
	            	//getting airline website url to each airline
	       		 String iata_code=(rs.getString("flightNumber")).substring(0,2);
	       		 String website=Airline.getWebsite(iata_code);
	       		 if(website==null) {website="http://www.google.com";}
	                details = details + "<tr><td>" + rs1.getInt("id") + "</td>" +
	                                        "<td>" + rs1.getString("departureCity") +"</td>" +
	                                        "<td>" + rs1.getString("departureDate") + "</td>" +
	                                        "<td>" + rs1.getString("departureTime") + "</td>" +
	                                        "<td>" + rs1.getString("arrivalCity") + "</td>" +
	                                        "<td>" + rs1.getString("arrivalDate") +"</td>" + 
	                                        "<td>" + rs1.getString("arrivalTime") + "</td>" +
	                                        "<td>" + rs1.getString("roundPrice") + "</td>" +
	                                        "<td>" + rs1.getString("flightNumber") + "</td>" +
	                                        "<td>" + rs1.getString("flightDuration") + "</td>" +
	                                        "<td >" +"<a href=\""+website+"\">"+ rs.getString("airlineName") + "</a>"+"</td>" +
	                                        "<td>" + rs1.getString("stops") + "</td>" +
	                                        "<td><input type=\"submit\" value=\""+rs1.getString("id")+"\" name=\"returnId\""
	                                		+ " style=\"background-color:#49743D;font-weight:bold;color:#ffffff\">"+
	                               "</td></tr>";
	            }
	            details += "</table></body></html>"; 
	            System.out.println(details);
	            	}
	        } 
	        catch (Exception e) 
	        {
	            System.out.println(e.getMessage());
	        }   
	        return details;
	    }
	    @GET
	    @Path("/booking")
	    @Produces("text/html")
	    @WebMethod(operationName ="getBooking")
	    public  String addBooking(@QueryParam("returnId") String returnId){
	    	 
	                arrivalId=returnId;
	    //Session.setAttribute("arrival_Id",returnId);
	    //request.getSession().setAttribute("departure_Id",departureId);
	    	
	    	String details = "<html><body  style=\"color: white\"  bgcolor=\"#A9A9A9\">"+
	        		         "<h1 align="+"center"+"> your selected flights </h1>"+
	    			         "<p align=\"center\"> departureId:"+departureId+"</p>"+
	        		         "<p align=\"center\"> returnId:"+arrivalId+"</p>"+
	    			         "<center><a  href=\"http:\\HappyTravelsProject/BookingForm.jsp\"> clink link to book tickets</a></center>"+
	        		         "</body> </html>";
	    	
			return  details;
	    	
	    	
	    			
	    }
	    @GET
	    @Path("/resultsJson")
	    @Produces({MediaType.APPLICATION_JSON })
	    public String getFlights(@QueryParam("trip") String trip,@QueryParam("departureAirport") String departureAirport,@QueryParam("arrivalAirport") String arrivalAirport,
	    		@QueryParam("startDate") String startDate,@QueryParam("endDate") String endDate,@QueryParam("currency") String toCurrency,@QueryParam("passengers") String noOfPassengers) {
				String details=null;
				int i=0;
				
			
						this.noOfPassengers=noOfPassengers;
		    	this.tocurrency=toCurrency;
		    	
		    	
		        try 
		        {
		            Class.forName("com.mysql.jdbc.Driver");
		            Connection con = DriverManager.getConnection("jdbc:mysql://localhost:3306/myvacationdb", "root", "root");
		            String query = "select * from flightdata where departureAirportCode=? and arrivalAirportCode=? and startDate=? and endDate=? ";

		            PreparedStatement st = con.prepareStatement(query);
		            st.setString(1, departureAirport);
		            st.setString(2, arrivalAirport);
		            st.setString(3,startDate);
		            st.setString(4,endDate);
		            
		            ResultSet rs=st.executeQuery(); 
		           
		            
		            details="{\"flights\":["; 
		            		
		           
		            
		            String price="";
		        	CurrencyConverter crt;
		            while (rs.next()) 
		            	
		            {   
		            	if(toCurrency=="USD") {price=rs.getString("roundPrice").toString();}
		            	else {
		            		//converting price to different currencies according to user  tocurrency
		            	String tempPrice=rs.getString("roundPrice").toString();
		                crt=new CurrencyConverter(fromCurrency,toCurrency,tempPrice);
		                
		                DecimalFormat format=new DecimalFormat("##.00");
		               
		                String StringPrice=crt.getCurrencyRate();
		        		price=format.format(Double.parseDouble(StringPrice));
		        		 
		            	
		            	}
		        		 //getting airline website url to each airline
		        		 String iata_code=(rs.getString("flightNumber")).substring(0,2);
		        		 String website=Airline.getWebsite(iata_code);
		        		 if(website==null) {website="http://www.google.com";}
		        		
		        		 
		                details = details + " { \"id\": \"" + rs.getInt("id")+"\",\n" +
		                                        "\"departure city\": \"" + rs.getString("departureCity") +"\",\n"+
		                                        "\"departure Date\": \"" + rs.getString("departureDate") + "\",\n" +
		                                        "\"departure Time\": \"" + rs.getString("departureTime") + "\",\n"+
		                                        "\"arrival city\": \"" + rs.getString("arrivalCity") + "\",\n"+
		                                        "\"arrival date\": \"" + rs.getString("arrivalDate") + "\",\n"+
		                                        "\"arrival time\": \"" + rs.getString("arrivalTime") + "\",\n"+
		                                        "\"price\":\"" + price + "\",\n"+
		                                        "\"flight number\": \"" + rs.getString("flightNumber") +  "\",\n"+
		                                        "\"flight duration\":\"" + rs.getString("flightDuration") + "\",\n"+
		                                        "\"airline\": \""+ rs.getString("airlineName") + "\",\n"+
		                                        "\"stops\": \"" + rs.getString("stops") + "\"\n"+
		                                        "},\n";
		                i++;
		            }
		            details += "]} "; 
		        } 
		        catch (Exception e) 
		        {
		            System.out.println(e.getMessage());
		        }
		        return details;
	    	
	    }

}
