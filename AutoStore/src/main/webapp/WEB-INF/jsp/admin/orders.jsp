<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:mainLayout title="Orders">
    <table class="table table-striped">
        <thead>
        <tr>
            <th>Order id</th>
            <th>User id</th>
            <th>Change status</th>
            <th>Cars</th>
            <th>Current status</th>
            <th></th>
        </tr>
        </thead>
        <tbody>
            <c:forEach items="${orders}" var="o">
                <tr>
                    <form method="post" action="/admin/changeStatus">
                    <td>${o.id}</td>
                    <td>${o.user.id}</td>
                    <td>
                        <select id="status${o.id}" name="status">
                            <c:forEach items="${statuses}" var="st">
                                <option>${st.toString()}</option>
                            </c:forEach>
                        </select></td>
                    <td><ol>
                        <c:forEach items="${o.productInOrders}" var="pio">
                            <li>${pio.product.name} - ${pio.product.price}$ X ${pio.amount}</li>
                        </c:forEach>
                    </ol></td>
                    <td>

                        <input type="hidden" name="order" value="${o.id}">
                        <input type="hidden" name="<c:out value="${_csrf.parameterName}"/>" value="<c:out value="${_csrf.token}"/>">
                        <button class="btn btn-info" onclick="submit">Save</button> </td>
                    <td>${o.status}</td>
                    </form>
                </tr>
            </c:forEach>
        </tbody>
    </table>
</t:mainLayout>