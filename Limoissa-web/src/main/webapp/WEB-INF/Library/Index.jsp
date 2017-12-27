<%-- 
    Document   : Index
    Created on : 28 nov. 2017, 18:19:24
    Author     : Adrien
--%>

<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="ct" tagdir="/WEB-INF/tags/" %>
<%@page contentType="text/html" pageEncoding="UTF-8"%>

<div id="LibraryContent">
    <form method="get" action="#" id="F_Search">
        <input type="text" name="S_Title" placeholder="Votre recherche..." value="${param.Recherche}" />
        <button type="submit">Rechercher</button>
    </form>
    
    <c:choose>
        <c:when test="${ null == Results }">Les résultats de votre recherche s'afficheront ici.</c:when>
        <c:when test="${empty Results.books }">
            <p>Aucun résultat disponible</p>
        </c:when>
        <c:otherwise>
            <table id="SearchResults" data-title="${ param.S_Title }" data-orderby="${ param.S_OrderBy }" data-orderbydirection="${ empty param.S_OrderBy_Direction ? 'ASC' : param.S_OrderBy_Direction }" data-page="${ empty Page ? 1 : Page}">
                <thead>
                    <tr>
                        <th><a href="#" data-target="Title">Titre</a></th>
                        <th><a href="#" data-target="A.LastName">Auteur</a></th>
                        <th><a href="#" data-target="Ca.Name">Catégorie</a></th>
                        <th class="center"><a href="#" data-target="PublicationDate">Date de publication</a></th>
                        <th class="center small"><a href="#" data-target="Price">Prix</a></th>
                        <th class="center small right"><a href="#" data-target="Stock">Stock</a></th>
                    </tr>
                </thead>
                <tbody>
                    <c:forEach items="${ Results.books }" var="R" varStatus="loop">
                        <tr class="Book${ loop.index % 2 == 0 ? ' odd' : '' }">
                            <td>${ Member.IsAdmin() ? ('<a href="Library?page=Books&B_Id=' += R.id +='">'+= R.title += '</a>') : R.title }</td>
                            <td>${ Member.IsAdmin() ? '<a href="Library?page=Authors&A_Id=' += R.author.id += '">' : '' }${ R.author.firstName += " " += R.author.lastName }&nbsp;(${ R.author.country.name })${ Member.IsAdmin() ? '</a>' : '' }</td>
                            <td><ct:CategoryParent data="${ R.category }" /></td>
                            <td class="center">${ R.getFormattedDate() }</td>
                            <td class="center">${ R.price }</td>
                            <td class="center right">${ R.stock }</td>
                        </tr>
                        <tr class="BookSummary${ loop.index % 2 == 0 ? ' odd' : '' }">
                            <td colspan="6">
                                ${ R.summary }
                            </td>
                        </tr>
                    </c:forEach>
                </tbody>
                <tfoot>
                    <tr>
                        <td colspan="6" id="pagination">
                            <c:if test="${ Page > 1}"><a href="#">${ Page - 1 }</a></c:if>
                            <span class="currentPage">${ Page }</span>
                            <c:if test="${ Page < Results.pages}"><a href="#">${ Page + 1 }</a></c:if>
                        </td>
                    </tr>
                </tfoot>
            </table>
        </c:otherwise>
    </c:choose>
</div>
