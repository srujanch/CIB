<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>

<c:if test="${not empty component.results}">
	<c:set var="categoryContentItem" value="${component.results[0]}"/>
	<c:if test="${fn:length(categoryContentItem.SBG_RELATED_CATEGORY)>1}">
		<%@include file="include/sbgCategorySummary.jsp"%>
		<%@include file="include/sbgCategoryTabNav.jsp"%>
	</c:if>
</c:if>