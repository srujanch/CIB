<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:contentItem result="content"/>

<c:if test="${not empty content}">
	<templating:textInlineEdit oid="${content.system.id}" attributexmlname="TITLE">
		<h1>${content.title}</h1>
	</templating:textInlineEdit>
	<templating:textInlineEdit oid="${content.system.id}" attributexmlname="DESCRIPTION">
		<sbg-templating:TranslateETLContent etlContent="${content.description}"/>
	</templating:textInlineEdit>
</c:if>