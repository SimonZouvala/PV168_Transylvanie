<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>

<%@page contentType="text/html;charset=utf-8" pageEncoding="UTF-8" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<html>
<body>

<table border="1">
    <thead>
    <tr>
        <th>Cena</th>
        <th>Kapacita</th>
        <th>Cena</th>
    </tr>
    </thead>
    <c:forEach items="${rooms}" var="room">
        <tr>
            <td><c:out value="${room.price}"/></td>
            <td><c:out value="${room.capacity}"/></td>
            <td><c:out value="${room.number}"/></td>
            <td><form method="post" action="${pageContext.request.contextPath}/room/delete?id=${room.id}"
                      style="margin-bottom: 0;"><input type="submit" value="Smazat"></form></td>
        </tr>
    </c:forEach>
</table>

<h2>Zadejte pokoj</h2>
<c:if test="${not empty chyba}">
    <div style="border: solid 1px red; background-color: yellow; padding: 10px">
        <c:out value="${chyba}"/>
    </div>
</c:if>
<form action="${pageContext.request.contextPath}/room//add" method="post">
    <table>
        <tr>
            <th>Cena:</th>
            <td><input type="text" name="name" value="<c:out value='${param.price}'/>"/></td>
        </tr>
        <tr>
            <th>Kapacita:</th>
            <td><input type="text" name="phone" value="<c:out value='${param.capacity}'/>"/></td>
        </tr>
         <tr>
            <th>Číslo pokoje:</th>
            <td><input type="text" name="phone" value="<c:out value='${param.number}'/>"/></td>
        </tr>
    </table>
    <input type="Submit" value="Zadat" />
</form>

</body>
</html><table border="1">
    <thead>
    <tr>
        <th>Cena</th>
        <th>Kapacita</th>
        <th>Cena</th>
    </tr>
    </thead>
    <c:forEach items="${rooms}" var="room">
        <tr>
            <td><c:out value="${room.price}"/></td>
            <td><c:out value="${room.capacity}"/></td>
            <td><c:out value="${room.number}"/></td>
            <td><form method="post" action="${pageContext.request.contextPath}/room/delete?id=${room.id}"
                      style="margin-bottom: 0;"><input type="submit" value="Smazat"></form></td>
        </tr>
    </c:forEach>
</table>

<h2>Zadejte pokoj</h2>
<c:if test="${not empty chyba}">
    <div style="border: solid 1px red; background-color: yellow; padding: 10px">
        <c:out value="${chyba}"/>
    </div>
</c:if>
<form action="${pageContext.request.contextPath}/room//add" method="post">
    <table>
        <tr>
            <th>Cena:</th>
            <td><input type="text" name="name" value="<c:out value='${param.price}'/>"/></td>
        </tr>
        <tr>
            <th>Kapacita:</th>
            <td><input type="text" name="phone" value="<c:out value='${param.capacity}'/>"/></td>
        </tr>
         <tr>
            <th>Číslo pokoje:</th>
            <td><input type="text" name="phone" value="<c:out value='${param.number}'/>"/></td>
        </tr>
    </table>
    <input type="Submit" value="Zadat" />
</form>

</body>
</html>