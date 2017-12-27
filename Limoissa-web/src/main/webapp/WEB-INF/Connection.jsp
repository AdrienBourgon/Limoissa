<%-- 
    Document   : Connection
    Created on : 28 nov. 2017, 12:47:57
    Author     : Adrien
--%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@ page contentType="text/html" pageEncoding="UTF-8"%>
<div id="SignInSignUp">
    <h1>Connexion / Inscription</h1>

    <form method="post" action="Signin">
        <fieldset>
            <legend>Connexion</legend>
            <input type="text" name="SI_Mail" placeholder="Votre adresse e-mail" value="martin.dupont@limoissa.fr" />
            <input type="password" name="SI_Password" placeholder="Votre mot de passe" value="martindupont" />
            <button type="submit">Connexion</button>
            <span class="error">${Error}</span>
        </fieldset>
    </form>
    <form method="post" action="Signup">
        <fieldset>
            <legend>Inscription</legend>
            <input type="text" name="SU_FirstName" placeholder="Votre prénom" value="${ param.SU_FirstName }" />
            <input type="text" name="SU_LastName" placeholder="Votre nom de famille" value="${ param.SU_LastName }" />
            <input type="text" name="SU_Mail" placeholder="Votre adresse e-mail" value="${ param.SU_Mail }"/>
            <input type="text" name="SU_Mail2" placeholder="Répétez votre adresse e-mail" />
            <input type="password" name="SU_Password" placeholder="Votre mot de passe" />
            <input type="password" name="SU_Password2" placeholder="Répétez votre mot de passe" />
            <button type="submit">Inscription</button>
            <p class="error">
                <c:forEach var="E" varStatus="S" items="${ SignupErrors }" >
                    <c:out value="${E}" /><c:if test="${not S.last}"><br /></c:if>
                </c:forEach>
            </p>
            ${SignupMessage}
        </fieldset>
    </form>
</div>
