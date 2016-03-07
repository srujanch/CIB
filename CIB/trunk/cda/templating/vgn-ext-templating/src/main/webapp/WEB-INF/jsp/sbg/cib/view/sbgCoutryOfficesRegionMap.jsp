<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:contentItem result="mediaImageItem"/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>
<c:set var="parentChannel" value="${currentChannel.parentChannel}"/>
<templating:contentLink var="parentChannelUrl" oid="${parentChannel.system.id}"/>
<c:if test="${not empty mediaImageItem}">
	<c:set var="altText" value="${mediaImageItem.ALTTEXT}"/>
	<c:set var="mediaImagePath" value="${mediaImageItem.SOURCEPATH.placementPath}"/>
	<c:if test="${not empty mediaImagePath}">
		<c:set var="imagePath" value="${SFPathPrefix}${mediaImagePath}"/>
	</c:if>
</c:if>
<c:if test="${empty imagePath}">
	<c:set var="imagePath" value="${defaultImagePath}"/>
</c:if>

<div class="return"><a href="${parentChannelUrl}">Return to world view</a></div>
<img src="${imagePath}" width="760" height="378" alt="${altText}" class="${altText}" border="0"  />