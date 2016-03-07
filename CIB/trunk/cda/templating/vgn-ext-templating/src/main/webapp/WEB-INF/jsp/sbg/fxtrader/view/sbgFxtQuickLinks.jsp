<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:contentItem result="content"/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>

<c:if test="${not empty content && not empty content.SBG_RELATED_LINKS}">
	<templating:sort result="sortedLinks" items="${content.SBG_RELATED_LINKS}" properties="linkOrder" order="ascending" />
	<div class="portlet">
		<ul class="quickLinks">
			<c:forEach items="${sortedLinks}" var="relatedLink">
				<c:set var="item" value="${relatedLink}"/>
				<c:set var="linkIconClass" value=""/>
				<c:set var="linkColourClass" value=""/>
				<c:if test="${not empty relatedLink.linkClassName}">
					<c:set var="classNames" value="${fn:split(relatedLink.linkClassName,'&&')}"/>
					<c:set var="linkIconClass" value="${classNames[0]}"/>
					<c:if test="${fn:length(classNames)>1}">
						<c:set var="linkColourClass" value="${classNames[1]}"/>
					</c:if>
				</c:if>
				<%@include file="/WEB-INF/jsp/sbg/common/include/sbgWebLinkManagement.jsp"%>
				<li class="${linkColourClass}">
					<a href="${itemURL}" class="button${overLayClass}" target="${linkTarget}" ${relAtt} ${titleAtt} onclick="return trackFormClick('${relatedLink.linkText}');">
						<span class="${linkIconClass}">${relatedLink.linkText}</span>
					</a>
					<c:if test="${not empty relatedLink.linkHelpText}">
						<a href="#${linkIconClass}HowTo" class="help" rel="toolTip" ${titleAtt}>&nbsp;</a>
					</c:if>
				</li>
			</c:forEach>
		</ul>
	</div>
</c:if>