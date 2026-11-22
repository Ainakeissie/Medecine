<%@ page import="java.util.List" %>
<%@ page import="medecine.app.model.Role" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<!DOCTYPE html>
<html lang="fr">
<head><meta charset="UTF-8"><title>Medecine</title></head>
<body>
    <h1>Créer un compte</h1>
    <%
        List<Role> roles = (List<Role>) request.getAttribute("roles");
    %>
    <form action="${pageContext.request.contextPath}/connexion" method="post">
        <label for="username">Nom d'utilisateur :</label>
        <input type="text" id="username" name="username" required><br><br>

        <label for="password">Mot de passe :</label>
        <input type="password" id="password" name="password" required><br><br>

        <label for="roleId">Rôle :</label>
        <select id="roleId" name="roleId">
            <option value="">-- Sélectionner un rôle --</option>
            <%
                if (roles != null) {
                    for (Role role : roles) {
            %>
            <option value="<%= role.getId() %>"><%= role.getRole() %></option>
            <%
                    }
                }
            %>
        </select><br><br>

        <input type="submit" value="Créer un compte">
    </form>
</body>
</html>
