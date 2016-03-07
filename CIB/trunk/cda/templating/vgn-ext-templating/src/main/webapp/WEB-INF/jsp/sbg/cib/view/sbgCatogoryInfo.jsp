<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>

<c:if test="${not empty component.results}">
	<c:set var="generalPageContetItem" value="${component.results[0]}"/>
	<div id="catInfo">
		<h1>${generalPageContetItem.title}</h1>
		<templating:textInlineEdit oid="${generalPageContetItem.system.id}" attributexmlname="DESCRIPTION">
			<sbg-templating:TranslateETLContent etlContent="${generalPageContetItem.description}"/>
		</templating:textInlineEdit>
	</div>
</c:if>
