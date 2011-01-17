<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="com.google.appengine.api.users.User" %>
<%@ page import="com.google.appengine.api.users.UserService" %>
<%@ page import="com.google.appengine.api.users.UserServiceFactory" %>
<%@ page import="moishd.server.dataObjects.ConstantLocation" %>
<%@ page import="moishd.server.common.DSCommon" %>

<html>
  <head>
    <link type="text/css" rel="stylesheet" href="/stylesheets/main.css" />
  </head>
  <body>
<%
    UserService userService = UserServiceFactory.getUserService();
    User user = userService.getCurrentUser();
    if (user == null) {
			response.sendRedirect("/test/Login");
    } else if (!UserServiceFactory.getUserService().isUserAdmin()) {
%>
<p>You need to be admin to access test methods.</p>
<%    	
    } else if (UserServiceFactory.getUserService().isUserAdmin()) {
    	List<ConstantLocation> cLocs = DSCommon.GetConstantLocations();
    	if (cLocs.isEmpty()) {
%>
<p>There are no constant locations.</p>
<%
		} else {
%>
<table>
<%
			for (ConstantLocation loc : cLocs) {
%>
<tr><td><%= loc.getName()%>,</td>
<td><%= loc.getLongitude()%>,</td>
<td><%= loc.getLatitude()%>,</td>
<td><%= loc.getTrophyName()%></td>
</tr>
<%
    		}
%>
</table>
<%   
    	}
%>
</br>
<form action="/test/AddConstantLocation" method="post">
  <table>
    <tr><td>Name: </td><td><input type="text" name="name" /></td></tr>
    <tr><td>Longitude: </td><td><input type="text" name="longitude" /></td></tr>
    <tr><td>Latitude: </td><td><input type="text" name="latitude" /></td></tr>
    <tr><td>Trophy Name(Optional): </td><td><input type="text" name="trophyName" /></td></tr>
    <tr><td><input type="submit" value="Add" /></td></tr>
  </table>
</form>
<%
    }
%>
  </body>
</html>