<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import= "com.aruna.jaxws.resources.FlightService" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Book flight tickets</title>

<style>
    div.ex {
        text-align: left;
         width:500px;
               
        padding: 10px;
        border: 5px solid grey;
        margin: 0 auto; 
    }
 
</style>
<body style='background-color: #A9A9A9'>

<h1>welcome to booking page</h1>
<p name="departureId">Departure:<%= FlightService.departureId%></p>
<p name="returnId">Return:<%= FlightService.arrivalId%></p>
<p name="passengers">Number Of Passengers:<%= FlightService.noOfPassengers%></p>


<h1 align="center">Booking Form</h1>
<div class="ex">
   <form style="background-color:#008B8B" action="BookingServlet" method="post" >
        <table style="with: 50%">
            <tr>
                <td>First Name</td>
                <td><input type="text" name="firstname"/></td>
            </tr>
            <tr>
                <td>Last Name</td>
                <td><input type="text" name="lastname"/></td>
            </tr>
            <tr>
                <td>Address</td>
                <td><input type="text" name="address"/></td>
            </tr>
            <tr>
                <td>Phone Number</td>
                <td><input type="text" name="phone"/></td>
                <td>Must be 10 digits</td>
            </tr>
            <tr>
                <td>Date Of Birth</td>
                <td><input type="text" name="dob"/></td>
                <td>MM/DD/YYYY</td>
            </tr>
            
            
            <tr>
                <td>card Number</td>
                <td><input type="text" name="card"/></td>
            </tr>
            <tr>
                <td>Email</td>
                <td><input type="text" name="email"/></td>
            </tr>
        </table>
        <center><input type="submit" style="background-color:#49743D;font-weight:bold;color:#ffffff" value="Book"/></center>
    </form></div>
    
</body>
</head>
</html>