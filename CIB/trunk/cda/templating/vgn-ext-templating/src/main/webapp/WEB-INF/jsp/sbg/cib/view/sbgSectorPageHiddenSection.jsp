<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>


<%-- initialize component & results --%>
<templating:contentItem result="content"/>
<templating:initRequestContext var="rc"/>

<c:if test="${not empty content}">
	<c:set var="generalPageContetItem" value="${content}"/>
	<%@include file="include/sbgDisplayHiddenTabs.jsp"%>
</c:if>
