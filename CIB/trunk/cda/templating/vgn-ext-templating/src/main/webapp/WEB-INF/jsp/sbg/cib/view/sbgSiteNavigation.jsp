<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:contentItem result="content"/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>
<c:set var="currentSite" value="${rc.currentSiteBean}"/>
<c:set var="currentSiteStylingClassname" value="${currentSite.stylingClassname}"/>

<c:if test="${not empty content && not empty content.SBG_RELATED_LINKS}">
	<templating:sort result="sortedLinks" items="${content.SBG_RELATED_LINKS}" properties="linkOrder" order="ascending" />
	<ul class="mainMenu">
		<c:forEach items="${sortedLinks}" var="relatedLink">
			<c:set var="item" value="${relatedLink}"/>
			<c:set var="isActiveTab" value="${currentSiteStylingClassname==relatedLink.linkClassName?'activeTab':''}"/>

			<%@include file="/WEB-INF/jsp/sbg/common/include/sbgWebLinkManagement.jsp"%>
			<li class="${isActiveTab}"><a href="${itemURL}" class="${overLayClass}" target="${linkTarget}"${relAtt}${titleAtt} onclick="return trackActionClick('${relatedLink.linkText}')">${relatedLink.linkText}</a></li>
		</c:forEach>
	</ul>
</c:if>