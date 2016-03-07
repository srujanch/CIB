<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>

<div id="products" class="portlet">
	<c:forEach items="${component.results}" var="content">
		<sbg-templating:getCtdXmlName var="ctdName" oid="${content.system.id}"/>
		<c:choose>
			<c:when test="${ctdName=='SBG-CONTENT-LIST'}">
				<c:set var="contentLisLink" value="#"/>
				<c:if test="${not empty content.channelLink}">
					 <templating:contentLink var="contentLisLink" oid="${content.channelLink.system.id}"/>
				</c:if>
				<div class="portletItem">
					<h1><a href="${contentLisLink}">${content.title}</a></h1>
					<c:if test="${not empty content.SBG_RELATED_LINKS}">
						<templating:sort result="sortedLinks" items="${content.SBG_RELATED_LINKS}" properties="linkOrder" order="ascending" />
						<ul class="linkList">
							<c:forEach items="${sortedLinks}" var="relatedLink">
								<c:set var="item" value="${relatedLink}"/>
								<%@include file="/WEB-INF/jsp/sbg/common/include/sbgWebLinkManagement.jsp"%>
								<li><a href="${itemURL}" class="${overLayClass}" target="${linkTarget}"${relAtt}${titleAtt}>${relatedLink.linkText}</a></li>
							</c:forEach>
						</ul>
					</c:if>
				</div>
			</c:when>
			<c:otherwise>
				<h1>Content instances of type :: '${ctdName}' are not handled for this region.</h1>
			</c:otherwise>
		</c:choose>
	</c:forEach>
	<br class="clearBoth" />
</div>