<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>

<t:mainLayout title="Add Car">

    <sf:form action="/admin/addProduct" method="post" modelAttribute="productform" cssStyle="margin-top: 20px">
        <sf:label path="productName"><span class="form-control">Name:</span></sf:label><sf:input path="productName"/><br>
        <sf:label path="cost"><span class="form-control">Price:</span></sf:label><sf:input path="cost"/><br>
        <sf:label path="warehouse"><span class="form-control">Warehouses:</span></sf:label>
        <form:select path="warehouse">
            <c:forEach items="${warehouses}" var="warehouse">
                <form:option value="${warehouse.id}"> ${warehouse.city} - ${warehouse.address} </form:option>
            </c:forEach>
        </form:select>
        <br>
        <sf:label path="amount"><span class="form-control">Amount:</span></sf:label><sf:input path="amount"/><br>
        <sf:label path="description"><span class="form-control">Descr:</span></sf:label><sf:textarea path="description"/><br>
        <button class="btn btn-success" type="submit">Done</button>
    </sf:form>
</t:mainLayout>
