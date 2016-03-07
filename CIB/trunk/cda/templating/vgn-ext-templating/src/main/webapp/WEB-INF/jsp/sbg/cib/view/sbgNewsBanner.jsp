<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>

<c:if test="${not empty component.results}">
	<c:set var="bannerContetItem" value="${component.results[0]}"/>
	<%@include file="/WEB-INF/jsp/sbg/cib/view/include/getBannerImagePath.jsp"%>
</c:if>
<img class="headerImg" alt="${altText}" src="${imagePath}" width="760" />