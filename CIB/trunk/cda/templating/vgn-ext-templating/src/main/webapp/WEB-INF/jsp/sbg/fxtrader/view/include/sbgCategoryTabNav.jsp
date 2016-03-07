<ul class="bodyTabs">
	<templating:sort result="tabItems" items="${categoryContentItem.SBG_RELATED_CATEGORY}" properties="categoryOrder" order="ascending" />
	<c:forEach items="${tabItems}" var="tabItem" varStatus="status">
		 <c:set var="categoryTitle" value="${tabItem.categoryTitle}"/>
		 <c:set var="tabFriendlyName" value='${fn:replace(categoryTitle," ","")}'/>
		 <c:choose>
			<c:when test="${status.first}">
				<li class="activeTab"><a href="#" rel="${tabFriendlyName}">${categoryTitle}</a></li>
			</c:when>
			<c:otherwise>
				<li><a href="#" rel="${tabFriendlyName}">${categoryTitle}</a></li>
			</c:otherwise>
		</c:choose>		
	</c:forEach>	
</ul>