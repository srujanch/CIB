<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:contentItem result="teaser"/>

<div id="aboutUsInfo" class="portlet">
	<h1>${teaser.title}</h1>
	<div class="portletText">
		<templating:textInlineEdit oid="${teaser.system.id}" attributexmlname="DESCRIPTION">
			<sbg-templating:TranslateETLContent etlContent="${teaser.description}"/>
		</templating:textInlineEdit>
	</div>
	<c:set var="item" value="${teaser}"/>
	<%@include file="/WEB-INF/jsp/sbg/common/include/sbgWebLinkManagement.jsp"%>
	<c:set var="linkText" value="${not empty teaser.linkText?teaser.linkText:'Read More'}"/>
	<a href="${itemURL}" class="articleLink${overLayClass}" target="${linkTarget}"${relAtt}>${linkText}</a>
</div>