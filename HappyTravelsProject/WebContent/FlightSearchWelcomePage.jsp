<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.sql.*" %>
    <%@ page import="com.aruna.jaxws.resources.*" %>
    <%@page import="java.net.URL"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="ISO-8859-1">
<title>HappyTravel</title>
</head>
<meta charset="ISO-8859-1">
<link rel="stylesheet" href="//code.jquery.com/ui/1.12.1/themes/base/jquery-ui.css">
  <link rel="stylesheet" href="/resources/demos/style.css">
  
  <script src="https://code.jquery.com/jquery-1.12.4.js"></script>
  <script src="https://code.jquery.com/ui/1.12.1/jquery-ui.js"></script>
  <script>
  $(function() {
    $( ".datepicker" ).datepicker();
  });
</script>
<style>
div.ex {
        text-align: left;
         width:500px;
        padding: 10px;
        border: 5px solid grey;
        margin: 0 auto; 
        color:white;
    }
header{
    font-size:2em;
    color:white;
   background: #000000;
    }
    h1{
    color:white;
    }
 footer{
    bottom: 0;
    position: fixed;
    width: 100%;
    color:white;
} 
  table td {
  position: relative;
}
select {
width: 200px;
margin: 30px;
}

 
</style>
<body style='background-color:#A9A9A9'>
<header><img src="travel.png" width="40" height="30"/> Happy Travels
<img src="airplane1.jpg" height="100" width=100%/></header>


 


<h1 align="center">Search For Flights</h1>

<div class="ex">
   <form style="background-color:#008B8B"   action="rest/flights/results" method="GET" >
        <table style="with: 70%">
            <tr>
                <td><input type="radio" class="btn" name="trip" value="roundtrip" checked="checked"><span>Round Trip</span></td>
                <td><input type="radio" class="btn" name="trip" value="oneway" checked="checked"><span>One Way</span></td>
            </tr>
                        <tr>
                <%
                	try{

                		Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                    	Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/myvacationdb", "root", "root");
                	String sql = "SELECT * FROM myvacationdb.airports ORDER BY airportcity ";
                	PreparedStatement pst=conn.prepareStatement(sql);
                    ResultSet rs =pst.executeQuery();
                %>

                <td>From:</td>
                <td><input type="text" size="40" list="airport" id="departureAirport" name="departureAirport" value="${para.token}">
                <datalist id="airport">
    
       
        <%
                   	while(rs.next()){
                   %>
            <option value ="<%=(rs.getString("airportcode"))%>"><%=(rs.getString("airportcity"))%></option>
     <%
     	} 
          rs.close();
         
     %>
 

    </datalist>

<%
	//**Should I input the codes here?**
        }
        catch(Exception e)
        {
             out.println("wrong entry"+e);
        }
%>
             </td>
            </tr>
            <tr>
                <%
                	try{
                //Class.forName("com.mysql.jdbc.Driver").newInstance();
               Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
                    	Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/myvacationdb", "root", "root");
                	String sql = "SELECT * FROM myvacationdb.airports ORDER BY airportcity ";
                	PreparedStatement pst=conn.prepareStatement(sql);
                    ResultSet rs =pst.executeQuery();
                %>

                <td>To:</td>
                <td><input type="text" size="40" list="airport" id="arrivalAirport" name="arrivalAirport" >
                <datalist id="airport">
    
       
        <%
                   	while(rs.next()){
                   %>
            <option value ="<%=(rs.getString("airportcode"))%>"><%=(rs.getString("airportcity"))%></option>
     <%
     	} 
          rs.close();
         conn.close();
     %>
 

    </datalist>

<%
	//**Should I input the codes here?**
        }
        catch(Exception e)
        {
             out.println("wrong entry"+e);
        }
%>
             </td>
            </tr>
            <tr>
                <td>Departure Date: </td>
                <td><input type="text" size="40" id="datepicker1" class="datepicker" id="startDate" name="startDate"></td>
            </tr>
            <tr>
                <td>Arrival Date:</td>
                <td><input type="text" size="40" id="datepicker2" class="datepicker" id="endDate" name="endDate"></td>
            </tr>
            
            <%
          	try{
          //Class.forName("com.mysql.jdbc.Driver").newInstance();
         Class.forName("com.mysql.cj.jdbc.Driver").newInstance();
         Connection conn=DriverManager.getConnection("jdbc:mysql://localhost:3306/myvacationdb", "root", "root");
          	String sql = "SELECT * FROM myvacationdb.currencies ORDER BY CurrencyCode";
          	PreparedStatement pst=conn.prepareStatement(sql);
              ResultSet rs =pst.executeQuery();
          %>
         <tr>
         <td>Currencies:</td>
         <td> <input type="text" size="40" list="currencies" name="currency" id="currency" >
         <dataList id="currencies">

        <%  while(rs.next()){ %>
            <option value ="<%=(rs.getString("CurrencyCode"))%>"/>
     <%} 
     rs.close();
  conn.close();
    %>
 

         </datalist>

<%
//**Should I input the codes here?**
        }
        catch(Exception e)
        {
             out.println("wrong entry"+e);
        }
%>
         </td></tr> 
            <tr>
                <td>Number Of Passengers:</td>
                <td><select name="passengers" id="passengers">
                 <option value="1">1</option>
                  <option value="2">2</option>
                  <option value="3">3</option>
                  <option value="4">4</option>
                  <option value="5">5</option>
                  <option value="6">6</option>
                  </select></td>
               
            </tr>
          
        </table>
        <center><input type="submit" value="Search Flights"></center>
    </form></div>
    
</body>
<footer><div class="footer">
Find Cheap Flights & Plane Tickets with HappyTravel
</div></footer>

</html>