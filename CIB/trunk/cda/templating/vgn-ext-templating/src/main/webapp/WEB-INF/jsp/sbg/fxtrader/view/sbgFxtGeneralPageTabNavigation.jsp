<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>

<c:if test="${not empty component.results}">
	<c:set var="generalPageContetItem" value="${component.results[0]}"/>

	<c:if test="${not empty generalPageContetItem.SBG_RELATED_TABS}">
		<h1>${generalPageContetItem.title}</h1>
		<c:set var="ulClassName" value="bodyTabs"/>
		<%@include file="../../cib/view/include/sbgMainTabDetails.jsp"%>
		<%@include file="../../cib/view/include/sbgShowTabNavigation.jsp"%>
	</c:if>
</c:if>