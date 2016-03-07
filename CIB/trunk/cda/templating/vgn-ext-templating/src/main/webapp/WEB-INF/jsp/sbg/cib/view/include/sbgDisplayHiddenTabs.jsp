 <c:if test="${not empty generalPageContetItem.SBG_RELATED_TABS}">  
	<templating:sort result="tabItems" items="${generalPageContetItem.SBG_RELATED_TABS}" properties="tabOrder" order="ascending" />
	<c:forEach items="${tabItems}" var="tabItem">
		<c:if test="${tabItem.tabType=='More Info'}">
			<c:set var="tabFriendlyName" value='${fn:replace(tabItem.tabFriendlyName," ","")}'/>
			<div id="${tabFriendlyName}" class="tabBody" style="display:none;">
				<sbg-templating:TranslateETLContent etlContent="${tabItem.tabDescription}"/>
			</div>
		</c:if>
	</c:forEach>
 </c:if>