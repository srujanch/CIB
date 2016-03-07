<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>

<div id="news" class="portlet">
	<h1 class="left"><a href="/cib/news_centre">${empty component.title?'News centre':component.title}</a></h1>
    <a class="right moreInfo" href="/cib/news_centre">More news &raquo;</a><br class="clearBoth" />
    <c:if test="${not empty component.results}">
		<c:forEach items="${component.results}" var="content">
			<templating:contentLink var="linkUrl" oid="${content.system.id}"/>
			<div class="portletItem">
				<templating:textInlineEdit oid="${content.system.id}" attributexmlname="TITLE">
				    <h3><a href="${linkUrl}">${content.title}</a></h3>
                </templating:textInlineEdit>
				<div class="portletText"><p>
                <templating:textInlineEdit oid="${content.system.id}" attributexmlname="INTRODUCTION">
                ${content.introduction}
                </templating:textInlineEdit>
                </p></div>
				<a href="${linkUrl}" class="articleLink">Full article</a>
			</div>
		</c:forEach>
    </c:if>
</div>