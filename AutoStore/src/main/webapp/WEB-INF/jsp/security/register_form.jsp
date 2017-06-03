<%@ taglib prefix="sf" uri="http://www.springframework.org/tags/form" %>
<%@taglib prefix="t" tagdir="/WEB-INF/tags" %>
<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<t:mainLayout>
    <sf:form action="/register" method="post" modelAttribute="userform" cssStyle="margin-top: 20px">

        <sf:label path="username"><span class="form-control ">Login:</span></sf:label><sf:input path="username"/><br>
        <sf:label path="email"><span class="form-control">Mail:</span></sf:label><sf:input path="email"/><br>
        <sf:label path="password"><span class="form-control">Password:</span> </sf:label> <sf:password  path="password"/><br>
        <sf:label path="repassword"><span class="form-control">Pass matches:</span> </sf:label> <sf:password path="repassword"/><br>

        <button class="btn btn-success" type="submit">Done</button>
    </sf:form>

</t:mainLayout>
