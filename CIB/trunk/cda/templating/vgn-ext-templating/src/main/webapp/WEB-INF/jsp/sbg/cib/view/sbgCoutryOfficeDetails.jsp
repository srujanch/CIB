<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:contentItem result="content"/>
<templating:initRequestContext var="rc"/>

<c:if test="${not empty content}">
	<div id="englishPage" class="tabBody">
		<h1>${content.title}</h1>
		<c:if test="${not empty content.summary}">
			<p>${content.summary}</p>
		</c:if>
		<c:if test="${not empty content.introduction}">
			<templating:textInlineEdit oid="${content.system.id}" attributexmlname="INTRODUCTION">
				<p>${content.introduction}</p>
			</templating:textInlineEdit>
		</c:if>
		<c:if test="${not empty content.sbgOverview}">
			<templating:textInlineEdit oid="${content.sbgOverview.system.id}" attributexmlname="DESCRIPTION">
				<sbg-templating:TranslateETLContent etlContent="${content.sbgOverview.description}"/>
			</templating:textInlineEdit>
		</c:if>
		<templating:textInlineEdit oid="${content.system.id}" attributexmlname="DESCRIPTION">
			<sbg-templating:TranslateETLContent etlContent="${content.description}"/>
		</templating:textInlineEdit>
	</div>
</c:if>
<%@include file="include/sbgBreadcrumbCiDetails.jsp"%>