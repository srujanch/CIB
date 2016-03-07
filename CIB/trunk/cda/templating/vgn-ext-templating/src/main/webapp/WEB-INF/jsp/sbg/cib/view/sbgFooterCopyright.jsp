<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>

<div class="copyright">
	&copy; 2010 Standard Bank is a licensed financial services provider in terms of the
	<c:forEach items="${component.results}" var="content" varStatus="status">
		<templating:contentLink var="itemURL" oid="${content.system.id}"/>
		<c:set var="termCiFriendlyname" value="${content.friendlyName}"/>
		<c:if test="${empty termCiFriendlyname}">
			<c:set var="termCiFriendlyname" value="${content.title}"/>
		</c:if>
		<a href="${itemURL}">${termCiFriendlyname}</a>
	</c:forEach>
</div>