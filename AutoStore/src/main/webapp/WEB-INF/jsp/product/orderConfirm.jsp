<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<t:mainLayout title="Confirm your order">
    <c:forEach items="${c}" var="con">
            <div class="container">
                <div class="row">
                    <div class="col-md-8">
                        <h3>${con.name}</h3>
                        <h4>${con.cost}$ X </h4><h4 id="txt">${con.amount}</h4>
                        <sf:form action="" method="post" id="id${con.id}">
                            <input type="hidden" value="${con.id}" name="id">
                            <input type="hidden" value="+" name="action">
                            <input type="button" onclick="AjaxRequest('#id${con.id}')" name="action" value="+"/>
                        </sf:form>
                        <sf:form action="" method="post" id="idminus${con.id}">
                            <input type="hidden" value="${con.id}" name="id">
                            <input type="hidden" value="-" name="action">
                            <input type="button" onclick="AjaxRequest('#idminus${con.id}')" name="action" value="-"/>
                        </sf:form>
                    </div>
                    <div class="col-md-4">
                        <sf:form action="/remove_product" method="post">
                            <input type="hidden" value="${con.id}" id="id" name="id">
                            <button type="submit">Delete</button>
                        </sf:form>
                    </div>
                </div>
            </div>
        <hr>
    </c:forEach>

    <a class="btn" href="/order_confirmed">Done</a>

    <script type="text/javascript" src="http://ajax.googleapis.com/ajax/libs/jquery/1.2.6/jquery.js"></script>
    <script type="text/javascript">
        function AjaxRequest(name) {
            jQuery.ajax({
                url: "/cart/change",
                type: "POST",
                data: jQuery(name).serialize(),
                success: function (response) {
                    document.getElementById("txt").innerHTML = response;
                }
            })

        }
        function AjaxRequestMinus(name) {
            jQuery.ajax({
                url: "/cart/change",
                type: "POST",
                data: jQuery(name).serialize(),
                success: function (response) {
                    document.getElementById("txt").innerHTML = response;
                }
            })

        }
    </script>
</t:mainLayout>