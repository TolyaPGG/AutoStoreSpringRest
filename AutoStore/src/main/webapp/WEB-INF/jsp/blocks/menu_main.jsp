<%@ page import="java.util.ArrayList" %>
<%@ page import="pojo.ProductCounter" %>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>



<nav class="navbar navbar-inverse navbar-fixed-bottom">
    <div class="container-fluid">

            <ul class="nav navbar-nav">

                <li class="active"><a href="/" class="navbar-brand">AutoStore</a></li>
                <security:authorize access="hasRole('ROLE_ANONYMOUS')">
                    <li><a class="btn" href="/register">Sign Up</a></li>
                    <li><a class="btn" href="/login">Sign In</a></li>
                </security:authorize>
                <security:authorize access="hasRole('ROLE_USER')">
                    <li>
                        <form:form action="/logout" method="POST">
                            <button type="submit" class="btn btn-default navbar-btn">Log Out</button>
                        </form:form>
                    </li>
                    <li>

                    </li>
                    <li>
                        <a href="/myOrders">Your Orders</a>
                    </li>
                </security:authorize>
                <security:authorize access="hasRole('ROLE_ADMIN')">
                    <li>
                        <form:form action="/logout" method="POST">
                            <button type="submit" class="btn btn-default navbar-btn">Log Out</button>
                        </form:form>
                    </li>
                </security:authorize>
            </ul>
<security:authorize access="hasRole('ROLE_USER')">
        <label class="btn btn-success" id="shoppingCart"> In Bucket <%=request.getSession().getAttribute("shoppingCart") == null ? 0 : ((ArrayList<ProductCounter>)(request.getSession().getAttribute("shoppingCart"))).size()%> products now</label>
        <% if (request.getSession().getAttribute("shoppingCart") != null && ((ArrayList)(request.getSession().getAttribute("shoppingCart"))).size() != 0){ %>
        <a class="btn" href="/confirm_order">To bucket list </a>
        <% } %>
</security:authorize>
    </div>
</nav>








