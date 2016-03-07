<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>

<c:if test="${not empty component.results}">
	<c:set var="generalPageContetItem" value="${component.results[0]}"/>
	<c:set var="introduction" value="${generalPageContetItem.introduction}"/>
	<c:set var="sbgGeneric" value="${generalPageContetItem.sbgGeneric}"/>
	<c:set var="description" value="${generalPageContetItem.description}"/>

	<h2>${generalPageContetItem.title} </h2>
	<c:if test="${not empty introduction}">
		<p>${introduction}</p>
	</c:if>
	<c:if test="${not empty sbgGeneric}">
		<p><sbg-templating:TranslateETLContent etlContent="${sbgGeneric.description}"/></p>
	</c:if>
	<c:if test="${not empty description}">
		<sbg-templating:TranslateETLContent etlContent="${description}"/>
	</c:if>
</c:if>