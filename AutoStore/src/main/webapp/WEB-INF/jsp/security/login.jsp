<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:mainLayout>
    <sf:form action="/login/process" method="post" cssStyle="margin-top: 20px ">
        <div class="container">
        <%--<sf:errors>--%>
        <input type="text" class="form-control" id="username" placeholder="Nick" name="username">
        <input type="password" class="form-control" id="exampleInputPassword1" placeholder="Password" name="password">

        <button class="btn btn-success" type="submit">Sign in</button>
        </div>
    </sf:form>
</t:mainLayout>

