<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:contentItem result="content"/>
<templating:initRequestContext var="rc"/>


<%-- get Product name and set up string to pass into tabs --%>
<c:set var="objectName" value="${content.title}"/>
<c:set var="tabContext" value="sector=${objectName}" scope="request"/>
<c:set var="generalPageContetItem" value="${content}"/>
<c:set var="bannerContetItem" value="${generalPageContetItem.topBanner}"/>
<div class="headerImage col_5" id="pageHeader">
	<c:if test="${not empty bannerContetItem}">
		<%@include file="include/sbgShowBannerImage.jsp"%>
	</c:if>
	<templating:textInlineEdit oid="${generalPageContetItem.system.id}" attributexmlname="TITLE">
			<h1>${generalPageContetItem.title}</h1>
	</templating:textInlineEdit>
	<p>${generalPageContetItem.introduction}</p>
</div>
<c:if test="${not empty generalPageContetItem.SBG_RELATED_TABS}">
    <c:set var="templateFormat" value="sectorTabView"/>
    <c:set var="ulClassName" value="bodyTabs col_5"/>
	<c:set var="mainTabFriendlyName" value="overviewPage"/>
	<c:set var="mainTabTitle" value="${generalPageContetItem.mainTabTitle}"/>
	<c:if test="empty mainTabTitle">
		<c:set var="mainTabTitle" value="Overview"/>
	</c:if>
	<%@include file="include/sbgShowTabNavigation.jsp"%>
</c:if>
<br class="clearBoth" />