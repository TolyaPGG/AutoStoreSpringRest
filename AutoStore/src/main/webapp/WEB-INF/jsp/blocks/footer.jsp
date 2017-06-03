<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<%@taglib uri="http://www.springframework.org/tags" prefix="spring" %>
<%@taglib uri="http://www.springframework.org/security/tags" prefix="security" %>

<%@ page contentType="text/html;charset=UTF-8" language="java" %>

<hr>
<footer>

    <script>
        $(function () {
            //setting up Datapicker internationalization + defaults
            $.datepicker.setDefaults(
                    $.extend($.datepicker.regional["${language}"]));
            $(".datepicker").datepicker({
                minDate: null,
                maxDate: null, dateFormat: 'yy-mm-dd' }).val();
        });
        $(document).ready(function () {
            $("form").submit(function () {
                var btn = $(this).find("input[type=submit]:focus");
                $(btn).attr("disabled", "true");
            });
        });
    </script>
</footer>
