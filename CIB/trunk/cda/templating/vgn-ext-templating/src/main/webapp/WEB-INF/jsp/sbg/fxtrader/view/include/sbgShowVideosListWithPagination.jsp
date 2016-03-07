<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<sbg-templating:initContentRegion controller="za.co.standardbank.sbg.cda.templating.controller.VideoGalleryComponentController"/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>
<templating:contentLink var="currentChannelChannelURL" oid="${currentChannel.system.id}"  format="tabView"/>
<fmt:formatNumber var="currentPageIndex" value="${currentPage}" minIntegerDigits="2"/>
<c:if test="${not empty results}">
	<c:set var="videoServerHost" value="<%=VideoService.getVideoServerHost()%>"/>
	<c:set var="videoServerPort" value="<%=VideoService.getVideoServerPort()%>"/>
	<div class="mediaGalleryButtons">
		<div class="toggleSet">
			<div class="toggleButton detailView selected" title="View detail"><span>&nbsp;</span></div>
			<div class="divider"></div>
			<div class="toggleButton gridView" title="View all"><span>&nbsp;</span></div>
		</div>
	</div>
	<%@include file="sbgShowPagination.jsp"%>
	<br class="clearBoth" />
	<ul class="galleryDisplay listView videoLinks">
		<c:forEach items="${results}" var="videoContentItem">
			<c:set var="videoTitle" value="${videoContentItem.TITLE}"/>
			<c:set var="docKey" value="${videoContentItem.REMOTEID}"/>
			<c:set var="thumbnailPath" value="http://${videoServerHost}:${videoServerPort}/staging/rest/file/GetFileThumbnailMedium/${videoContentItem.REMOTEID}/thumbnail.jpg"/>
			<templating:contentLink var="itemURL" channelId="${currentChannel.system.id}" oid="${videoContentItem.system.id}"/>
			<li>
				<a href="${docKey}" target="_blank" rel="videoContent" class="imgGallery" onclick="return trackVideoClick('${videoContentItem.TITLE}');"><img src="${thumbnailPath}" alt="thumb1" /></a>
				<h2><a href="${docKey}" target="_blank" rel="videoContent" class="titleLink" onclick="return trackVideoClick('${videoContentItem.TITLE}');">${videoContentItem.TITLE}</a></h2>
				<p class="descr">${videoContentItem.DESCRIPTION}</p>
				<p class="duration"><strong>Length:</strong> ${videoContentItem.VIDEODURATION} seconds </p>
				<a href="${docKey}" target="_blank" rel="videoContent" class="viewButton" onclick="return trackVideoClick('${videoContentItem.TITLE}');">View &raquo;</a>
			</li>
		</c:forEach>		
	</ul>
</c:if>
<%@include file="sbgShowPagination.jsp"%>