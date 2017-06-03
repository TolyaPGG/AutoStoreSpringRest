<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:mainLayout title="My orders">
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Order</th>
            <th>Status</th>
            <th>Cars</th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${orders}" var="o">
                <tr>
                    <td>${o.id}</td>
                    <td>${o.status.toString()}</td>
                    <td>
                        <ol>
                            <c:forEach items="${o.productInOrders}" var="pio">
                                <li>${pio.product.name} - ${pio.product.price}$ X ${pio.amount}</li>
                            </c:forEach>
                        </ol>
                    </td>
                </tr>
            </c:forEach>
        </tbody>
</t:mainLayout>