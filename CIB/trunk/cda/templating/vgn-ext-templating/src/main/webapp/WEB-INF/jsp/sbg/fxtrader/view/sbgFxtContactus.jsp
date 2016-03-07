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

	<templating:textInlineEdit oid="${generalPageContetItem.system.id}" attributexmlname="TITLE">
		<h1>${generalPageContetItem.title}</h1>
	</templating:textInlineEdit>
	<c:if test="${not empty introduction}">
		<templating:textInlineEdit oid="${generalPageContetItem.system.id}" attributexmlname="INTRODUCTION">
			<p>${introduction}</p>
		</templating:textInlineEdit>
	</c:if>
	<c:if test="${not empty sbgGeneric}">
		<templating:textInlineEdit oid="${sbgGeneric.system.id}" attributexmlname="DESCRIPTION">
			<sbg-templating:TranslateETLContent etlContent="${sbgGeneric.description}"/>
		</templating:textInlineEdit>
	</c:if>
	<div class="col_2 colLeft contactInfo portlet actionBar">
		<c:if test="${not empty description}">
			<templating:textInlineEdit oid="${generalPageContetItem.system.id}" attributexmlname="DESCRIPTION">
				<sbg-templating:TranslateETLContent etlContent="${description}"/>
			</templating:textInlineEdit>
		</c:if>
	</div>
</c:if>