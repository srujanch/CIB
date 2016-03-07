<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<templating:initComponent/>
<c:if test="${not empty component.title}">
	<templating:textInlineEdit oid="${component.system.id}" attributexmlname="vgnExtTemplatingComponentTitle">
		<h1>${component.title}</h1>
	</templating:textInlineEdit>	
</c:if>
<c:if test="${not empty component.text}">
	<c:set var="etlContent" value="${component.text}" scope="request"/>
	<templating:textInlineEdit oid="${component.system.id}" attributexmlname="text">
		<sbg-templating:TranslateETLContent etlContent="${etlContent}"/>
	</templating:textInlineEdit>
</c:if>