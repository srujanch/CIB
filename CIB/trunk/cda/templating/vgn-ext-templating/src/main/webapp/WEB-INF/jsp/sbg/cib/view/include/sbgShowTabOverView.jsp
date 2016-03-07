<c:if test="${not empty generalPageContetItem}">
	<div class="col_4">
		<c:if test="${not empty generalPageContetItem.sbgGeneric}">
			<templating:textInlineEdit oid="${generalPageContetItem.sbgGeneric.system.id}" attributexmlname="DESCRIPTION">
				<sbg-templating:TranslateETLContent etlContent="${generalPageContetItem.sbgGeneric.description}"/>
			</templating:textInlineEdit>
		</c:if>
		<templating:textInlineEdit oid="${generalPageContetItem.system.id}" attributexmlname="DESCRIPTION">
			<sbg-templating:TranslateETLContent etlContent="${generalPageContetItem.description}"/>
		</templating:textInlineEdit>
	</div>
</c:if>