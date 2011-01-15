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
    } else if (UserServiceFactory.getUserService().isUserAdmin()) {
    	List<ConstantLocation> cLocs = DSCommon.GetConstantLocations();
    	if (cLocs.size() == 0) {
%>
<p>There are no constant locations.</p>
<%
		} else {
			for (ConstantLocation loc : locs) {
%>
<p>Name: <%= loc.getName()%></p>
<%
    		}
    	}
    }
%>

    <form action="/AddConstantLocation" method="post">
      <div>Name: <input type="text" name="name" /></div>
      <div>Longitude: <input type="text" name="longitude" /></div>
      <div>Latitude: <input type="text" name="latitude" /></div>
      <div>Trophy Name(Optional): <input type="text" name="trophyName" /></div>
      <div><input type="submit" value="Add" /></div>
    </form>

  </body>
</html>