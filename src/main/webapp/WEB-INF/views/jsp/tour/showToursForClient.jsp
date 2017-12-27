
<%@ page contentType="text/html;charset=UTF-8" language="java" %>
<%@taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="form" uri="http://www.springframework.org/tags/form" %>
<html>
<head>
    <title>Tours List</title>
    <link href="${contextPath}/resources/core/css/bootstrap.min.css" rel="stylesheet">
    <link href="${contextPath}/resources/core/css/common.css" rel="stylesheet">
    <script src="https://oss.maxcdn.com/html5shiv/3.7.2/html5shiv.min.js"></script>
    <script src="https://oss.maxcdn.com/respond/1.4.2/respond.min.js"></script>

    <link href="${bootstrapCss}" rel="stylesheet"/>
    <link href="${coreCss}" rel="stylesheet"/>
</head>
<body>
<%@include file="../topbar.jsp" %>
<h1 class="text-center"><b>Tours List</b></h1> <br>




<table class="table table-hover">
    <thead>
    <tr>
        <td class="text-center"><spring:message code="tour.name"/></td>
        <td class="text-center"><spring:message code="tour.type"/></td>
        <td class="text-center"><spring:message code="tour.price"/></td>
        <td class="text-center"><spring:message code="tour.duration"/></td>
        <td class="text-center"><spring:message code="tour.hot"/></td>
    </tr>
    </thead>
    <tbody>
    <c:forEach items="${tourList}" var="tour">
        <tr>
            <td class="text-center">${tour.tourName}</td>
            <td class="text-center">${tourTypeService.getTourTypeNameById(tour.tourTypesId)}</td>
            <td class="text-center">${tour.price}</td>
            <td class="text-center">${tour.duration}</td>
            <td class="text-center">
                <c:if test="${tour.hot==true}">
                    Yes
                </c:if>
                <c:if test="${tour.hot==false}">
                    No
                </c:if>
            </td>
            <td class="text-center">
                <form:form method = "POST" action = "/orders/create"  cssClass="form-signin" cssStyle="border-bottom-width: medium">
                    <input type="hidden" name="tourId" value="${tour.id}">
                    <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="purchase"/></button>
                </form:form></td>
            </td>
        </tr>
    </c:forEach>
    </tbody>
</table>
<form:form method="GET" action="/" cssClass="form-signin">
    <button class="btn btn-lg btn-primary btn-block" type="submit"><spring:message code="button.back"/></button>
</form:form>
<%@include file="../footer.jsp" %>

</body>
</html>
