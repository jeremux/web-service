<%-- 
    Document   : results.jsp
    Created on : 6 avr. 2015, 23:10:56
    Author     : jeremux
--%>

<%@page contentType="text/html" pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
    <head>
        <meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
        <title>JSP Page</title>
    </head>
    <body>
        <h1>Informations du livre :</h1>
        <h3>Titre = <%= request.getParameter("titre")%> </h3>
        <h3>Author = <%= request.getParameter("auteur")%> </h3>
        <h3>Release Date = <%= request.getParameter("annee")%> </h3>
        <% request.setAttribute("titre", request.getParameter("titre")); %>
        <form action="index.html">
            <input type="submit" value="Submit as admin">
        </form>
    </body>
</html>
