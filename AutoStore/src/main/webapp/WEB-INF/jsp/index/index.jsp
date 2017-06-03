<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:mainLayout>

    <security:authorize access="hasRole('ROLE_ADMIN')">
        <div class="list-group">
            <a class="btn btn-info" href="admin/addProduct">New Car</a><br>
            <a  class="btn btn-info" href="admin/warehouses">New warehouse</a><br>
            <a class="btn btn-info" href="/admin/users">Users</a><br>
            <a class="btn btn-info" href="/admin/orders">Orders</a>
        </div>

    </security:authorize>
    <div class="panel panel-default">
    <!-- Default panel contents -->
    <div class="panel-heading"><h1>Cars</h1></div>
        <table class="table">
    <c:forEach items="${products}" var="product">

                <a class="list-group-item" href="/product?id=${product.id}"><h3>${product.name}</h3></a>


    </c:forEach>
            </table>
    </div>

</t:mainLayout>
