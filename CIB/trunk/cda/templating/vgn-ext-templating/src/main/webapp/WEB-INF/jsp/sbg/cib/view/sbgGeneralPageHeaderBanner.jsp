<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>

<c:if test="${not empty component.results}">
	<c:set var="generalPageContetItem" value="${component.results[0]}"/>
	<c:set var="bannerContetItem" value="${generalPageContetItem.topBanner}"/>
	<div class="headerImage col_5" id="pageHeader">
	<c:if test="${not empty bannerContetItem}">
		<%@include file="include/sbgShowBannerImage.jsp"%>
	</c:if>
	<c:if test="${not empty generalPageContetItem}">
		<templating:textInlineEdit oid="${generalPageContetItem.system.id}" attributexmlname="TITLE">
			<h1>${generalPageContetItem.title}</h1>
		</templating:textInlineEdit>
		<p>${generalPageContetItem.introduction}</p>
	</c:if>
	</div>
	<c:if test="${not empty generalPageContetItem.SBG_RELATED_TABS}">
		<c:set var="ulClassName" value="bodyTabs col_5"/>
		<c:set var="mainTabFriendlyName" value="overviewPage"/>
		<c:set var="mainTabTitle" value="${generalPageContetItem.mainTabTitle}"/>
		<c:if test="${empty mainTabTitle}">
			<c:set var="mainTabTitle" value="Overview"/>
		</c:if>
		<%@include file="include/sbgShowTabNavigation.jsp"%>
	</c:if>
	<br class="clearBoth" />
</c:if>