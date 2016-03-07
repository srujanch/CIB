<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:contentItem result="content"/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>

<c:if test="${not empty content && not empty content.SBG_RELATED_LINKS}">
	<templating:sort result="sortedLinks" items="${content.SBG_RELATED_LINKS}" properties="linkOrder" order="ascending" />
	<div id="relatedLinks" class="portlet">
		<h1>${content.title}</h1>
		<h3>${content.summary}</h3>
		<ul>
		<c:forEach items="${sortedLinks}" var="relatedLink">
			<c:set var="item" value="${relatedLink}"/>
			<%@include file="/WEB-INF/jsp/sbg/common/include/sbgWebLinkManagement.jsp"%>
			<li><a href="${itemURL}" class="${overLayClass}" target="${linkTarget}"${relAtt}${titleAtt} onclick="return trackSocialMediaClick('${relatedLink.linkText}')">${relatedLink.linkText}</a></li>
		</c:forEach>
		</ul>
	</div>
</c:if>