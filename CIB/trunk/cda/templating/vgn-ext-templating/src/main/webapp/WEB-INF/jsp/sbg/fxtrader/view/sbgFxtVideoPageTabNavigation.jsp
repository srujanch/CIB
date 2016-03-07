<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:if test="${not empty component.results}">
	<c:set var="generalPageContetItem" value="${component.results[0]}"/>

	<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>
	<c:set var="currentChannelFriendlyName" value="${currentChannel.friendlyName}"/>
	<c:if test="${empty currentChannelFriendlyName}">
		<c:set var="currentChannelFriendlyName" value="${currentChannel.system.name}"/>
	</c:if>
	<c:set var="mainTabFriendlyName" value='${fn:replace(currentChannelFriendlyName," ","")}'/>
	<c:set var="mainTabTitle" value="${generalPageContetItem.mainTabTitle}"/>
	<c:if test="empty mainTabTitle">
		<c:set var="mainTabTitle" value="Overview"/>
	</c:if>
	<c:set var="ulClassName" value="bodyTabs"/>
	<h1>${generalPageContetItem.title}</h1>
	<%@include file="../../cib/view/include/sbgShowTabNavigation.jsp"%>
	<br class="clearBoth">
</c:if>