<%@ page import="medecine.app.model.Patient" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head><meta charset="UTF-8"><title>Saisie patient</title></head>
<body>
    <h1>Saisie patient</h1>
    <%
        Patient patient = (Patient) request.getAttribute("patient");
        String dateNaissance = "";
        if (patient != null && patient.getDateNaissance() != null) {
            dateNaissance = new java.text.SimpleDateFormat("yyyy-MM-dd").format(patient.getDateNaissance());
        }
    %>
    <form action="${pageContext.request.contextPath}/api/patients" method="post">
        <% if (patient != null && patient.getId() != null) { %>
        <input type="hidden" name="id" value="<%= patient.getId() %>">
        <% } %>

        <label for="nom">Nom :</label>
        <input type="text" id="nom" name="nom" value="<%= patient != null && patient.getNom() != null ? patient.getNom() : "" %>" required><br><br>

        <label for="prenom">Prénom :</label>
        <input type="text" id="prenom" name="prenom" value="<%= patient != null && patient.getPrenom() != null ? patient.getPrenom() : "" %>" required><br><br>

        <label for="dateNaissance">Date de naissance :</label>
        <input type="date" id="dateNaissance" name="dateNaissance" value="<%= dateNaissance %>" required><br><br>

        <label for="sexe">Sexe :</label>
        <input type="text" id="sexe" name="sexe" value="<%= patient != null && patient.getSexe() != null ? patient.getSexe() : "" %>" required><br><br>

        <input type="submit" value="Enregistrer">
    </form>
    <p><a href="${pageContext.request.contextPath}/patients">Voir la liste</a></p>
</body>
</html>
