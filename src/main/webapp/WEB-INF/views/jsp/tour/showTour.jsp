<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
<!DOCTYPE html>
<html lang="en">
<head>
<title>Tour Page</title>

<spring:url value="/resources/core/css/hello.css" var="coreCss" />
<spring:url value="/resources/core/css/bootstrap.min.css" var="bootstrapCss" />
<link href="${bootstrapCss}" rel="stylesheet" />
<link href="${coreCss}" rel="stylesheet" />
</head>
<h1>${message.toUpperCase()}</h1>
<h1>Tour information</h1>
<br>
<h3>Tour id: ${tour.id}</h3>
<h3>isHot: ${tour.hot}</h3>
<h3>isActive: ${tour.active}</h3>
<h3>Duration: ${tour.duration}</h3>
<h3>Price: ${tour.price}</h3>
<h3>Agent: ${tour.agentId}</h3>
<h3>Tour type: ${tour.tourTypesId}</h3>

<spring:url value="/resources/core/css/hello.js" var="coreJs" />
<spring:url value="/resources/core/css/bootstrap.min.js" var="bootstrapJs" />

<script src="${coreJs}"></script>
<script src="${bootstrapJs}"></script>
<script src="https://ajax.googleapis.com/ajax/libs/jquery/1.11.2/jquery.min.js"></script>

</body>
</html>