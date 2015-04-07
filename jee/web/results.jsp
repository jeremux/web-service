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
        <h3>Informations du livre :</h3>
        <p><b>Titre:</b> <%= request.getParameter("titre")%> </p>
        <p><b>Ateur:</b> <%= request.getParameter("auteur")%> </p>
        <p><b>Annee parution:</b> <%= request.getParameter("annee")%> </p>
        
        <form action="index.xhtml?titre=<%= request.getParameter("titre")%>&auteur=<%= request.getParameter("auteur")%>&annee=<%= request.getParameter("annee")%>
              " method="post">
            <input type="submit" value="ok">
        </form>
    </body>
</html>
