<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>
<c:set var="channelPage" value="${currentChannel}"/>
<%@include file="/WEB-INF/jsp/sbg/cib/view/include/getChannelFriendlyName.jsp"%>

<c:if test="${not empty component.results}">
	<c:set var="generalPageContetItem" value="${component.results[0]}"/>
	<div id="${channelFriendlyName}Content" class="col_5">
		<templating:textInlineEdit oid="${generalPageContetItem.system.id}" attributexmlname="TITLE">
			<h1>${generalPageContetItem.title} </h1>
		</templating:textInlineEdit>
		<templating:textInlineEdit oid="${generalPageContetItem.system.id}" attributexmlname="INTRODUCTION">
			<p>${generalPageContetItem.introduction}</p>
		</templating:textInlineEdit>
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

<script type="text/javascript">
		$(document).ready(function() { // bodyguard function
			var channelFriendlyName = '${channelFriendlyName}';
			var rightColumn = $('#'+channelFriendlyName+'ColRight');
			var leftColumn= $('#'+channelFriendlyName+'ColLeft');
			var contentDiv = $('#'+channelFriendlyName+'Content');
			if(rightColumn.length>0 && leftColumn.length>0) {
			   contentDiv.attr('class', 'col_3');
			} else if(rightColumn.length>0 || leftColumn.length>0) {
			   contentDiv.attr('class', 'col_4');
			} else { 
				contentDiv.attr('class', 'col_5');
			}
		});// bodyguard function
</script>