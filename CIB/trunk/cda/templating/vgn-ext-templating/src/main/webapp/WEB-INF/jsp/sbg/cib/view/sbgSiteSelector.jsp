<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentSite" value="${rc.currentSiteBean}"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>

<div class="site_selector">
	<span class="current">&nbsp;<!-- ${currentSite.propertyName} --></span>
	<ul id="scroller" class="styleSelector noFollow">
		<c:forEach items="${component.results}" var="content">
			<li>${content.title}
				<c:if test="${not empty content.SBG_RELATED_LINKS}">
					<templating:sort result="sortedLinks" items="${content.SBG_RELATED_LINKS}" properties="linkOrder" order="ascending" />
					<ul>
						<c:forEach items="${sortedLinks}" var="relatedLink">
							<c:set var="item" value="${relatedLink}"/>
							<%@include file="/WEB-INF/jsp/sbg/common/include/sbgWebLinkManagement.jsp"%>
							<li><a href="${itemURL}" class="${relatedLink.linkClassName}${overLayClass}" target="${linkTarget}"${relAtt}${titleAtt}>${relatedLink.linkText}</a></li>
						</c:forEach>
					</ul>
				</c:if>
			</li>
		</c:forEach>			
	</ul>
</div>