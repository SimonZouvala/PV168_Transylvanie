<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>
<h3>Vytvořené pokoje</h3>
<table border="1">
    <thead>
    <tr>      
        <th>Číslo pokoje</th>
        <th>Cena</th>
        <th>Kapacita</th>
    </tr>
    </thead>
    <c:forEach items="${room}" var="room">
        <tr>
            <td><c:out value="${room.number}"/></td>
            <td><c:out value="${room.price}"/></td>
            <td><c:out value="${room.capacity}"/></td>
            
            <td><form method="post" action="${pageContext.request.contextPath}/room/delete?id=${room.id}"
                      style="margin-bottom: 0;"><input type="submit" value="Smazat"></form></td>
        </tr>
    </c:forEach>
</table>

<h3>Zadejte pokoj</h3>
<c:if test="${not empty chyba}">
    <div style="border: solid 1px black; background-color: yellow; padding: 10px">
        <c:out value="${chyba}"/>
    </div>
</c:if>
<form action="${pageContext.request.contextPath}/room//add" method="post">
    <table>
        <tr>
            <th>Číslo pokoje:</th>
            <td><input type="text" name="number" value="<c:out value='${param.number}'/>"/></td>
        </tr>
        <tr>
            <th>Cena:</th>
            <td><input type="text" name="price" value="<c:out value='${param.price}'/>"/></td>
        </tr>
        <tr>
            <th>Kapacita:</th>
            <td><input type="text" name="capacity" value="<c:out value='${param.capacity}'/>"/></td>
        </tr>

    </table>
    <input type="Submit" value="Zadat" />
</form>
<br>
<a href="index.jsp">Zpět na rozcesník</a>
</body>
</html>