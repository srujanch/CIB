<c:set var="channelFriendlyName" value="${channelPage.friendlyName}"/>
<c:if test="${empty channelFriendlyName}">
	<c:set var="channelFriendlyName" value="${channelPage.system.name}"/>
</c:if>
<c:set var="channelFriendlyName" value='${fn:replace(channelFriendlyName," ","")}'/>