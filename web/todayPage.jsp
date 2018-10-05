<%-- 
    Document   : todayPage
    Created on : Jul 30, 2018, 9:45:23 PM
    Author     : Ratul
--%>
<%@page import="WebTest.DatabaseConnection"%>
<%@page import="java.sql.*"%>
<%@page import="java.io.PrintWriter"%>
<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>Today's Items</title>
        <link rel="stylesheet" href="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/css/bootstrap.min.css">
        <!-- OFFLINE -->
        <link rel="stylesheet" type="text/css" href="assets/bootstrap/css/bootstrap.min.css">
        <script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.3/jquery.min.js"></script>
        <script src="http://maxcdn.bootstrapcdn.com/bootstrap/3.3.4/js/bootstrap.min.js"></script>
        <link href="https://fonts.googleapis.com/css?family=Open+Sans" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Shadows+Into+Light+Two" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Cookie" rel="stylesheet">
        <link href="https://fonts.googleapis.com/css?family=Pattaya" rel="stylesheet">
        <link rel="stylesheet" href="main.css">
        <meta name="viewport" content="width=device-width, initial-scale=1.0">
    </head>
    <body>
        
        <%
            Connection con = DatabaseConnection.dbConnector();
            PrintWriter pw = response.getWriter();
            if (session.getAttribute("user") == null) {
                //pw.println("You're not logged in!!");
                response.sendRedirect("index.html");
            }
            
        %>

        <!-- Starting NAVbar -->
        <nav class="navbar navbar-inverse">
            <div class="container-fluid">
                <div class="navbar-header">
                    <a class="navbar-brand" href="#">Personal Task Manager</a>
                </div>
                <ul class="nav navbar-nav">
                    <li><a href="userPage.jsp">All</a></li>
                    <li class="active"><a href="#">Today</a></li>
                    <li><a href="weekPage.jsp">This Week</a></li>
                </ul>

                <ul class="nav navbar-nav navbar-right">
                    <li><a href="#?value=<%= (String) session.getAttribute("user")%>"><span class="glyphicon glyphicon-user"></span> Hello, <%= (String) session.getAttribute("user")%> </a></li>
                    <li><a href="userLogout"><span class="glyphicon glyphicon-log-out"></span> Logout</a></li>
                </ul>
            </div>
        </nav>

        <!-- End of NAVbar -->


        <div class="container">
            <div class="list">
                <h1 class="header">Today's Items.</h1>

                <ul class="items">
                    <%
                        try {
                            PreparedStatement statement = con.prepareStatement(
                                    "SELECT * FROM items where DATE(created)=CURDATE() and id='" + (Integer) session.getAttribute("userID") + "'");
                            //statement.setString(0, LOGIN_UsernameField.getText());
                            //statement.setString(0, LOGIN_PasswordField.getPassword());
                            ResultSet rs = statement.executeQuery();
                            while (rs.next()) {
                    %>
                    <li>
                        <% if ("0".equalsIgnoreCase(rs.getString("done"))) {%>
                        <span class="item"> <%= rs.getString("name")%> </span>
                        <a href="doneItem?item=<%= rs.getString("number")%>" class="done-button"> Mark as done</a>
                        <a href="deleteItem?item=<%= rs.getString("number")%>" class="done-button"> Delete</a>
                        <% }%>

                        <% if ("1".equalsIgnoreCase(rs.getString("done"))) {%>
                        <span class="item done"> <%= rs.getString("name")%> </span>
                        <a href="notdoneItem?item=<%= rs.getString("number")%>" class="done-button"> Mark as not done</a>
                        <a href="deleteItem?item=<%= rs.getString("number")%>" class="done-button"> Delete</a>
                        <% }%>
                    </li>
                    <% }
                        } catch (Exception e) {
                            pw.println(e);
                        }%>

                </ul>

            </div>
        </div>

        
        
    </body>
</html>
