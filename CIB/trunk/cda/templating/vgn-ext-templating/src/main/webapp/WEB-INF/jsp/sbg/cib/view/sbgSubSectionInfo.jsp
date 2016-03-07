<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>

<div id="information" class="left collapsed">
	<div id="products" class="portlet">
		<c:forEach items="${component.results}" var="content">
			<c:set var="item" value="${content}"/>
			<%@include file="/WEB-INF/jsp/sbg/common/include/sbgWebLinkManagement.jsp"%>

			<div class="portletItem">
				<templating:textInlineEdit oid="${content.system.id}" attributexmlname="TITLE">
					<h1><a href="${itemURL}" onClick="if (typeof vuit !== 'undefined') {  if(typeof vui.cps.ui.ice.FLOATIE_MODE !== 'undefined') { return (vui.cps.ui.ice.FLOATIE_MODE !== 'inline'); } else return true; } else { return true; }">${content.title}</a></h1>
				</templating:textInlineEdit>
				
				<div class="portletText">
					<templating:textInlineEdit oid="${content.system.id}" attributexmlname="DESCRIPTION">
						<sbg-templating:TranslateETLContent etlContent="${content.description}"/>
					</templating:textInlineEdit>
				</div>
			</div>
		</c:forEach>
		<br class="clearBoth" />
	</div>
</div>