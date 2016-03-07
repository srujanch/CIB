<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>

<h1>${empty component.title?'Recent news':component.title}</h1>
<c:if test="${not empty component.results}">
    <ul id="searchResults">
    <c:forEach items="${component.results}" var="content">
		<templating:contentLink var="linkUrl" channelId="${currentChannel.system.id}" oid="${content.system.id}"/>
			<li>
				<templating:textInlineEdit oid="${content.system.id}" attributexmlname="TITLE">
					<h2><a href="${linkUrl}" onClick="if (typeof vuit !== 'undefined') {  if(typeof vui.cps.ui.ice.FLOATIE_MODE !== 'undefined') { return (vui.cps.ui.ice.FLOATIE_MODE !== 'inline'); } else return true; } else { return true; }">${content.title}</a></h2>
				</templating:textInlineEdit>
				<h4><fmt:formatDate value="${content.liveDate}" pattern="dd MMMM yyyy" /></h4>
				<templating:textInlineEdit oid="${content.system.id}" attributexmlname="INTRODUCTION">
					<p>${content.introduction}</p>
				</templating:textInlineEdit>				
				<a href="${linkUrl}"  class="articleLink">Full article &raquo;</a>
			</li>
	</c:forEach>
    </ul>
</c:if>
<br class="clearBoth" />