<c:set var="itemURL" value="#"/>
<c:choose>
	<c:when test="${'externalURL' == item.linkTo}">
		 <c:if test="${not empty item.linkToUrl}">
			 <c:set var="itemURL" value="${item.linkToUrl}"/>
		</c:if>
	</c:when>
	<c:when test="${'channel' == item.linkTo}">
		 <c:if test="${not empty item.linkToChannel}">
			<templating:contentLink var="itemURL" oid="${item.linkToChannel.system.id}"/>
		 </c:if>
	</c:when>
	<c:when test="${'contentInstance' == item.linkTo}">
		<c:if test="${not empty item.linkToContent}">
			<c:set var="parentChannelId" value=""/>		
			<c:choose>
				<c:when test="${not empty item.linkToChannel}">
					<c:set var="parentChannelId" value="${item.linkToChannel.system.id}"/>
				</c:when>
				<c:when test="${empty item.linkToContent.system.channelAssociations}">
					<c:set var="parentChannelId" value="${currentChannel.system.id}"/>
				</c:when>
			</c:choose>	
			<templating:contentLink var="itemURL" channelId="${parentChannelId}" oid="${item.linkToContent.system.id}"/>
		</c:if>
	</c:when>
</c:choose>
<c:set var="itemClassName" value="${item.class.name}"/>
<%-- Get ToolTip/Help Text for a link.This property only applicable for related items of SBG Content List items. --%>
<c:if test="${fn:contains(itemClassName,'SBG_RELATED_LINKS')}">
	<c:set var="titleAtt" value=""/>
	<c:if test="${not empty item.linkHelpText}">
		<c:set var="titleAtt" value=' title="${item.linkHelpText}"'/>
	</c:if>	
</c:if>
<c:set var="linkTarget" value="${item.linkTarget}"/>
<c:if test="${empty linkTarget}">
	<c:set var="linkTarget" value="_self"/>
</c:if>
<c:set var="overLayClass" value=""/>
<c:set var="relAtt" value=""/>
<c:if test='${fn:startsWith(linkTarget,"modal")}'>
	<c:set var="overLayClass" value=" ${linkTarget}"/>
	<c:set var="linkTarget" value="_blank"/>
	<c:set var="relAtt" value=' rel="tempWin"'/>
</c:if>