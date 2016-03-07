<%-- main page sets context parameter value, which tells tab jsps (eg Deals, Accolades) about the current product etc--%>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>
<c:set var="breadCrumbNameList" value="${currentChannel.breadcrumbNamePath}" />
<c:set var="breadCrumbPath" value="" />
<c:set var="siteName" value="" />
<c:forEach items="${breadCrumbNameList}" var="bredName" varStatus="status">
    <c:if test="${!status.first}">
        <c:set var="breadCrumbPath" value="${breadCrumbPath}${bredName}:" />
    </c:if>
</c:forEach>
<c:if test="${not empty currentChannel.parentSite}">
    <c:set var="siteName" value="${fn:toUpperCase(currentChannel.parentSite.system.name)}" />
</c:if>
<c:if test="${not empty objectName}">
     <c:set var="breadCrumbPath" value="${breadCrumbPath}${objectName}" />
</c:if>
<ul class="${ulClassName}">
	<li class="activeTab"><a rel="${mainTabFriendlyName}" href="#">${mainTabTitle}</a></li>		 
	<templating:sort result="tabItems" items="${generalPageContetItem.SBG_RELATED_TABS}" properties="tabOrder" order="ascending" />
	<c:forEach items="${tabItems}" var="tabItem">
        <c:set var="tempPath" value="${breadCrumbPath}" />
        <c:set var="tabFriendlyName" value=""/>
		<c:set var="hrefValue" value="#"/>
		<c:choose>
			<c:when test="${tabItem.tabType=='Deals'}">
                <c:if test="${not empty tempPath}">
                   <c:set var="tempPath" value="${tempPath} Deals" />
                </c:if>
                <sbg-templating:buildFriendlyURL var="dealURL" format="${templateFormat}" vanityURL="deals" />
                <c:set var="hrefValue" value="${dealURL}?${tabContext}"/>
				<c:set var="tabFriendlyName" value="dealsPage"/>
			</c:when>
			<c:when test="${tabItem.tabType=='Accolades'}">
                <c:if test="${not empty tempPath}">
                   <c:set var="tempPath" value="${tempPath} Accolades" />
                </c:if>
                <sbg-templating:buildFriendlyURL var="accURL" format="${templateFormat}" vanityURL="accolades" />
                <c:set var="hrefValue" value="${accURL}?${tabContext}"/>
				<c:set var="tabFriendlyName" value="accoladesPage"/>
			</c:when>
			<c:when test="${tabItem.tabType=='external'}">
                <c:if test="${not empty tempPath}">
                   <c:set var="tempPath" value="${tempPath} External URL Tab" />
                </c:if>
                 <c:set var="hrefValue" value="${tabItem.tabExternalURL}"/>
			</c:when>
			<c:when test="${tabItem.tabType=='More Info'}">
                <c:if test="${not empty tempPath}">
                   <c:set var="tempPath" value="${tempPath} ${tabItem.tabTitle}" />
                </c:if>
                <c:set var="hrefValue" value="#"/>
				<c:set var="tabFriendlyName" value="${tabItem.tabFriendlyName}"/>
			</c:when>
			<c:otherwise>
				<c:if test='${not empty tabItem.tabChannelId}'>
					 <c:set var="channelPage" value="${tabItem.tabChannelId}"/>
					 <templating:contentLink var="hrefValue" oid="${channelPage.system.id}" format="tabView"/>
					 <%@include file="getChannelFriendlyName.jsp"%>
					 <c:set var="tabFriendlyName" value="${channelFriendlyName}"/>
                     <c:if test="${not empty tempPath}">
                        <c:set var="tempPath" value="${tempPath} ${tabItem.tabTitle}" />
                    </c:if>
                </c:if>
			</c:otherwise>
		</c:choose>
		<c:set var="tabFriendlyName" value='${fn:replace(tabFriendlyName," ","")}'/>
        <c:choose>
			<c:when test="${tabItem.tabType=='external'}">
				<li><a href="${hrefValue}" target="_blank" onclick="return trackTabClicks('${tempPath}','${siteName}')">${tabItem.tabTitle}</a></li>
				<!-- When a [rel] is not specified, the the tab takes on the default action of [a href] Note, tab also does not highlight, as the person is linking away from the current page -->
			</c:when>
			<c:otherwise>
				<li><a href="${hrefValue}" rel="${tabFriendlyName}" onclick="return trackTabClicks('${tempPath}','${siteName}')">${tabItem.tabTitle}</a></li>
			</c:otherwise>
		</c:choose>
	</c:forEach>	
</ul>
