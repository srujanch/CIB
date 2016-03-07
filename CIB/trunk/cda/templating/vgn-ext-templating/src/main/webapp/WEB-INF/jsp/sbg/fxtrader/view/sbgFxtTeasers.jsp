<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>
<c:set var="count" value="0" scope="page" />
<c:if test="${not empty component.results}">
	<div class="sfxInfoPortals">
		<c:forEach items="${component.results}" var="teaserContentItem" varStatus="status">
			<c:set var="item" value="${teaserContentItem}"/>
			<%@include file="/WEB-INF/jsp/sbg/common/include/sbgWebLinkManagement.jsp"%>
				<c:choose>     
                    <c:when test="${(count + status.count) % 2 == 0}">      
                       <div class="portlet right">    
                    </c:when>     
                    <c:otherwise>     
                       <div class="portlet left">   
                    </c:otherwise>     
				</c:choose>     
			
				<templating:textInlineEdit oid="${teaserContentItem.system.id}" attributexmlname="TITLE">
					<h1><a href="${itemURL}" class="${overLayClass}" target="${linkTarget}"${relAtt} onClick="if (typeof vuit !== 'undefined') {  if(typeof vui.cps.ui.ice.FLOATIE_MODE !== 'undefined') { return (vui.cps.ui.ice.FLOATIE_MODE !== 'inline'); } else return true; } else { return true; }">${teaserContentItem.title}</a></h1>
				</templating:textInlineEdit>
				<templating:textInlineEdit oid="${teaserContentItem.system.id}" attributexmlname="DESCRIPTION">
					<sbg-templating:TranslateETLContent etlContent="${teaserContentItem.description}"/>
				</templating:textInlineEdit>
				<templating:textInlineEdit oid="${teaserContentItem.system.id}" attributexmlname="LINK-TEXT">
					<p class="portletLinks right">
						<a class="articleLink" target="${teaserContentItem.linkTarget}" href="${itemURL}" onClick="if (typeof vuit !== 'undefined') {  if(typeof vui.cps.ui.ice.FLOATIE_MODE !== 'undefined') { return (vui.cps.ui.ice.FLOATIE_MODE !== 'inline'); } else return true; } else { return true; }">${teaserContentItem.linkText}</a>
					</p>
				</templating:textInlineEdit>
			</div>
		</c:forEach>
		<br class="clearBoth" />
	</div>
</c:if>