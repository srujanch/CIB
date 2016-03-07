<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="channelPage" value="${rc.requestedChannelBean}"/>
<%@include file="/WEB-INF/jsp/sbg/cib/view/include/getChannelFriendlyName.jsp"%>

<c:if test="${not empty component.results}">
	<c:set var="generalPageContetItem" value="${component.results[0]}"/>
	<%@include file="/WEB-INF/jsp/sbg/cib/view/include/sbgMainTabDetails.jsp"%>
	<%@include file="include/sbgShowMainTabDetails.jsp"%>
	<%@include file="include/sbgDisplayHiddenTabs.jsp"%>
</c:if>

<script type="text/javascript">
		$(document).ready(function() { // bodyguard function
			var channelFriendlyName = '${channelFriendlyName}';
			var rightColumn = $('#'+channelFriendlyName+'ColRight');
			var leftColumn= $('#'+channelFriendlyName+'ColLeft');
			var contentDiv = $('#content');
			var pageHeaderDiv = $('#pageHeader');
			var isPageHeaderExists = pageHeaderDiv.length>0;

			if(rightColumn.length>0 && leftColumn.length>0) {
			   contentDiv.attr('class', 'col_3 contentBody');
			   if(isPageHeaderExists) {
					pageHeaderDiv.attr('class', 'headerImage col_3');
			   }
			} else if(rightColumn.length>0 || leftColumn.length>0) {
			   contentDiv.attr('class', 'col_4 contentBody');
			   if(isPageHeaderExists) {
					pageHeaderDiv.attr('class', 'headerImage col_4');
			   }
			} else { 
				contentDiv.attr('class', 'col_5 contentBody');
				 if(isPageHeaderExists) {
					pageHeaderDiv.attr('class', 'headerImage col_5');
			   }
			}			
		});// bodyguard function
</script>