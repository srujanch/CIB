<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentSite" value="${rc.currentSiteBean}"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>
<c:set var="portletParams" value="${rc.portletCustomProperties}"/>

<div id="breadCrumbItem" class="breadCrumb module">
	<c:if test="${not empty currentChannel.breadcrumbPath}">
		<ul id="crumbList">
			<c:forEach items="${currentChannel.breadcrumbPath}" var="breadCrumbChannel">
				<c:set var="linkUrl" value="#"/>
				<sbg-templating:descriptorProperty var="isPlaceholderChannel" channelId="${breadCrumbChannel.system.id}" attributeName="<%=DPMConstants.CHANNEL_DESC_NAV_PLACEHOLDER_CHANNEL%>"/>
				<c:if test="${isPlaceholderChannel !='Yes'}">
					<templating:contentLink var="linkUrl" oid="${breadCrumbChannel.system.id}"/>
				</c:if>
				<c:remove var="isPlaceholderChannel" scope="page"/>
				 <c:choose>
					<c:when test="${breadCrumbChannel.system.name == 'Home'}">
						<li><a href="${linkUrl}" class="crum" rel="tooltip" title="Go to the ${currentSite.globalUrlTitle} home page">Home</a></li>
					</c:when>
					<c:when test="${breadCrumbChannel== currentChannel}">
						<%-- following li is designed for CI details page --%>
						<li id="vgnCurrentChannelCiDetails" style="display:none">
							 <c:choose>
								<c:when test="${ !currentChannel.active}">
									${currentChannel.system.name}
								</c:when>
								<c:otherwise>
									<a href="${linkUrl}" class="crum">${currentChannel.system.name}</a>
								</c:otherwise>
							</c:choose>
						</li>						
						<li id="vgnCurrentChannel">${breadCrumbChannel.system.name}</li>
					</c:when>
					<c:when test="${ !breadCrumbChannel.active}">
						<li>${breadCrumbChannel.system.name}</li>
					</c:when>
					<c:otherwise>
						<li><a href="${linkUrl}" class="crum">${breadCrumbChannel.system.name}</a></li>
					</c:otherwise>
				</c:choose>	
			</c:forEach>
		</ul>
	</c:if>	
</div>