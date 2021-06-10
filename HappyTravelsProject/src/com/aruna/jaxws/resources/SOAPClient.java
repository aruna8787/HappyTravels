package com.aruna.jaxws.resources;

import java.net.URL;
import java.util.Arrays;

import javax.xml.namespace.QName;
import javax.xml.soap.MessageFactory;
import javax.xml.soap.MimeHeaders;
import javax.xml.soap.SOAPBody;
import javax.xml.soap.SOAPConnection;
import javax.xml.soap.SOAPConnectionFactory;
import javax.xml.soap.SOAPElement;
import javax.xml.soap.SOAPEnvelope;
import javax.xml.soap.SOAPException;
import javax.xml.soap.SOAPMessage;
import javax.xml.soap.SOAPPart;
import javax.xml.ws.Service;

import com.aruna.jaxws.beans.Booking;
import com.aruna.jaxws.services.BookingServices;

public class SOAPClient {
	public static void main(String[] args) {
		BookingServices bs;
		try {
		URL wsdlURL = new URL("http://localhost/SOAPBookingAPI/bookingWS?wsdl");
		//check above URL in browser, you should see WSDL file
		
		//creating QName using targetNamespace and name
		QName qname = new QName("http://services.jaxws.aruna.com/", "BookingServiceImplService"); 
		Service service = Service.create(wsdlURL, qname);  
		
		//We need to pass interface and model beans to client
		 bs= service.getPort(BookingServices.class);
		
		
		/*Booking b1 = new Booking(); b1.setFirstName("Pankaj"); b1.setBookingId(1); b1.setPhoneNumber("2485338666");
		b1.setLastName("bora");b1.setDateOfBirth("04/10/2020");b1.setDepartureId(29);b1.setReturnId(98);
		b1.setNumberOfPassengers(3);
		Booking b2 = new Booking(); b2.setFirstName("Pankajfcg"); b2.setBookingId(2); b2.setPhoneNumber("2485338666");
		b2.setLastName("boragfhg");b2.setDateOfBirth("04/10/2020");b2.setDepartureId(29);b2.setReturnId(98);
		
		//add person
		System.out.println("Add Person Status="+bs.addBooking(b1));
		System.out.println("Add Person Status="+bs.addBooking(b2));
		*/
		//get person
		System.out.println(bs.getBooking(2));
		
		//get all persons
		System.out.println(Arrays.asList(bs.getAllBookings()));
		
		//delete person
		System.out.println("Delete booking Status="+bs.deleteBooking(1));
		System.out.println("Delete booking Status="+bs.deleteBooking(2));
		//get all persons
		System.out.println(Arrays.asList(bs.getAllBookings()));
		}catch(Exception e) {
			System.out.println(e);
		}
}
}
