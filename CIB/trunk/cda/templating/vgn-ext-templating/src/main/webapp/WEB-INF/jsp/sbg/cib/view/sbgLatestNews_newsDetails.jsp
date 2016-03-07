<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>
<templating:contentLink var="currentChannelUrl" oid="${currentChannel.system.id}"/>

<div id="recentNews" class="listedItem portlet">
	<h1>${empty component.title?'Recent news':component.title}</h1>
	<c:if test="${not empty component.results}">
        <ul id="news">
        <c:forEach items="${component.results}" var="content">
			<templating:contentLink var="linkUrl" channelId="${currentChannel.system.id}" oid="${content.system.id}"/>
				<li><h3><a href="${linkUrl}">${content.title}</a></h3></li>
		</c:forEach>
        </ul>
        <p class="textRight"><a href="/cib/news_centre">See all recent news &raquo;</a></p>
	</c:if>
</div>