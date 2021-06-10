<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
     <%@ page import="java.sql.*" %>
       <%@ page import= "com.aruna.jaxws.resources.FlightService" %>
<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>Conformation page</title>
</head>
<body style='background-color: #A9A9A9' >
<p align="center">Departure:<%= FlightService.departureId%></p>
<table align="center"  border="1">
<tr>

<% try{ 
	//Class.forName("com.mysql.jdbc.Driver").newInstance();
    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
         	Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/myvacationdb", "root", "root");
     	String sql = "SELECT * FROM myvacationdb.flightdata where id=?";
     	
     	PreparedStatement pst=conn.prepareStatement(sql);
     	pst.setString(1,FlightService.departureId);
         ResultSet rs =pst.executeQuery();
         while(rs.next()){
        	%>
        	<td><%= rs.getString("id") %></td>
        	<td><%= rs.getString("departureAirportCode") %></td>
        	<td><%= rs.getString("departureDate") %></td>
        	<td><%= rs.getString("departureTime") %></td>
        	<td><%= rs.getString("arrivalAirportCode") %></td>
        	<td><%= rs.getString("arrivalDate") %></td>
        	<td><%= rs.getString("arrivalTime") %></td>
        	<td><%= rs.getString("flightNumber") %></td>
        	<td><%= rs.getString("airlineName") %></td>
        	<td><%= rs.getString("price") %></td>
        	<%
         }rs.close();
         conn.close();
}catch(Exception e){System.out.println(e);
	
}%>

</tr>
</table>
<p align="center">Return:<%= FlightService.arrivalId %></p>
<table align="center"  border="1">
<tr>

<% try{ 
	//Class.forName("com.mysql.jdbc.Driver").newInstance();
    Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
         	Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/myvacationdb", "root", "root");
     	String sql = "SELECT * FROM myvacationdb.flightdata where id=?";
     	
     	PreparedStatement pst=conn.prepareStatement(sql);
     	pst.setString(1,FlightService.arrivalId);
         ResultSet rs =pst.executeQuery();
         while(rs.next()){
        	%>
        	<td><%= rs.getString("id") %></td>
        	<td><%= rs.getString("departureAirportCode") %></td>
        	<td><%= rs.getString("departureDate") %></td>
        	<td><%= rs.getString("departureTime") %></td>
        	<td><%= rs.getString("arrivalAirportCode") %></td>
        	<td><%= rs.getString("arrivalDate") %></td>
        	<td><%= rs.getString("arrivalTime") %></td>
        	<td><%= rs.getString("flightNumber") %></td>
        	<td><%= rs.getString("airlineName") %></td>
        	<td><%= rs.getString("price") %></td>
        	<%
         }rs.close();
         conn.close();
}catch(Exception e){System.out.println(e);
	
}%>

</tr>
</table>
<center>ticket booked</center>
</body>
</html>