<c:set var="imagePath" value="${defaultImagePath}"/>
<c:set var="altText" value=""/>
<c:if test="${not empty bannerContetItem}">
	<c:set var="bannerPath" value="${bannerContetItem.mediaItem.SOURCEPATH.placementPath}"/>
	<c:set var="altText" value="${bannerContetItem.mediaItem.ALTTEXT}"/>
	<c:if test="${not empty bannerPath}">
		<c:set var="imagePath" value="${SFPathPrefix}${bannerPath}"/>
	</c:if>
</c:if>
