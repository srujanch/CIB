<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<c:set var="now" value="<%=new java.util.Date()%>" />

<c:set var="moreDetailsLink" value="${component.data}"/>
<c:if test="${empty moreDetailsLink}">
	<c:set var="moreDetailsLink" value="#"/>
</c:if>
<c:set var="moreDetailsLink" value="${component.data}"/>
<div id="shareTicker">
	<div class="shareInfo">
		<div class="tickerHeader">
			<h2>${component.title}</h2>
			<h4>Updated <fmt:formatDate pattern="dd MMM yyyy" value="${now}" /></h4>
			<br class="clearBoth" />
		</div>
		<div id="ticker" class="scrollWrapper">
		</div>
		<a href="${moreDetailsLink}" onclick="return trackActionClick('Global markets');" class="right">More financial data</a>
		<br class="clearBoth" />
	</div>	
</div>