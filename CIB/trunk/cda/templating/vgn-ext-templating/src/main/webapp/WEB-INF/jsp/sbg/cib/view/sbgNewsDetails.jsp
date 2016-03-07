<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:contentItem result="content"/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>

<c:if test="${not empty content}">
	<h1>${empty component.title?'News information':component.title}</h1>
	<templating:textInlineEdit oid="${content.system.id}" attributexmlname="TITLE">
		<h2>${content.title}</h2>
	</templating:textInlineEdit>
	<h4><fmt:formatDate value="${content.liveDate}" pattern="dd MMMM yyyy" /></h4>
	<templating:textInlineEdit oid="${content.system.id}" attributexmlname="DESCRIPTION">
		<sbg-templating:TranslateETLContent etlContent="${content.description}"/>
	</templating:textInlineEdit>	
	<br class="clearBoth" />
</c:if>
<%@include file="include/sbgBreadcrumbCiDetails.jsp"%>