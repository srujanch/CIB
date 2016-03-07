<div id="${mainTabFriendlyName}" class="tabBody">
	<c:choose>				
		<c:when test="${not empty generalPageContetItem.SBG_RELATED_TABS}">
			<h2>${mainTabTitle}</h2>
		</c:when>
		<c:otherwise>
			<h1>${generalPageContetItem.title}</h1>
		</c:otherwise>
	</c:choose>
	<c:set var="introduction" value="${generalPageContetItem.introduction}"/>
	<c:set var="sbgGeneric" value="${generalPageContetItem.sbgGeneric}"/>
	<c:set var="description" value="${generalPageContetItem.description}"/>

	<c:if test="${not empty introduction}">
		<p>${introduction}</p>
	</c:if>
	<c:if test="${not empty sbgGeneric}">
		<sbg-templating:TranslateETLContent etlContent="${sbgGeneric.description}"/>
	</c:if>
	<c:if test="${not empty description}">
		<sbg-templating:TranslateETLContent etlContent="${description}"/>
	</c:if>
</div>