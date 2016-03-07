<h1>${categoryContentItem.title}</h1>
<templating:textInlineEdit oid="${categoryContentItem.system.id}" attributexmlname="DESCRIPTION">
	<sbg-templating:TranslateETLContent etlContent="${categoryContentItem.description}"/>
</templating:textInlineEdit>