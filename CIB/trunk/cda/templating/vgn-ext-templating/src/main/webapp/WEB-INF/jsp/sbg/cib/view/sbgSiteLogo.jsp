<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initRequestContext var="rc"/>
<c:set var="currentSite" value="${rc.currentSiteBean}"/>
<templating:contentLink var="homeURL" oid="${currentSite.homeChannel.system.id}"/>

<div class="site-logo">
	<a title="${currentSite.globalUrlTitle}" href="${currentSite.globalUrl}">&nbsp;</a>
</div>
<div class="site_global">
	<ul>
		<li class="active">${currentSite.propertyName}<a href="#" title="${currentSite.propertyName}"></a></li>
		<!-- <li><a href="#" title="South Africa">South Africa</a></li> -->
		
	</ul>
</div>