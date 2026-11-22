<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ page import="java.util.List" %>
<%@ page import="medecine.app.model.Patient" %>
<!DOCTYPE html>
<html lang="fr">
    <head><meta charset="UTF-8"><title>Patients</title></head>
    <body>
        <h1>Bienvenue dans le dashboard</h1>
        <%
            java.util.List<medecine.app.model.Patient> patients =
            (java.util.List<medecine.app.model.Patient>) request.getAttribute("liste_patient");
            if (patients == null || patients.isEmpty()) {
            %>
            <p>Aucun patient à afficher</p>
            <%
            } else {
            %>
            <table border="1">
                <tr><th>Nom</th><th>Prénom</th><th>Date naissance</th><th>Sexe</th><th>Actions</th></tr>
                <%
                    for (Patient p : patients) {
                %>
                <tr>
                    <td><%= p.getNom() %></td>
                    <td><%= p.getPrenom() %></td>
                    <td><%= p.getDateNaissance() %></td>
                    <td><%= p.getSexe() %></td>
                    <td>
                        <a href="${pageContext.request.contextPath}/modif/patients?id=<%= p.getId() %>">Modifier</a>
                        <a href="${pageContext.request.contextPath}/delete/patients?id=<%= p.getId() %>">Supprimer</a>
                    </td>
                </tr>
                <%
                    }
                %>
            </table>
        <%
        }
    %>
    <a href="${pageContext.request.contextPath}/nouveauPatient">Créer un patient</a>
</body>
</html>
