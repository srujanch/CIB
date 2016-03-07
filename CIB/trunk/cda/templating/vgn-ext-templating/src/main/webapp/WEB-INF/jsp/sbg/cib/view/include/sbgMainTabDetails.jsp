<c:set var="mainTabFriendlyName" value="${generalPageContetItem.mainTabTitle}"/> <!-- mainTabFriendlyName is used as value for 'rel' attribute. Ex:  <a rel="sfxTraderVideos" ... -->
<c:choose>				
	<c:when test="${empty mainTabFriendlyName}">
		<c:set var="mainTabFriendlyName" value="overviewPage"/>
	</c:when>
	<c:otherwise>
		<c:set var="mainTabFriendlyName" value='${fn:replace(mainTabFriendlyName," ","")}Page'/>
	</c:otherwise>
</c:choose>
<c:set var="mainTabTitle" value="${generalPageContetItem.mainTabTitle}"/>
<c:if test="empty mainTabTitle">
	<c:set var="mainTabTitle" value="Overview"/>
</c:if>