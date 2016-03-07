<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>

<c:if test="${not empty component.results}">
	<ul>
		<c:forEach items="${component.results}" var="content" varStatus="status">
			<templating:contentLink var="itemURL" oid="${content.system.id}"/>
			<c:set var="termCiFriendlyname" value="${content.friendlyName}"/>
			<c:if test="${empty termCiFriendlyname}">
				<c:set var="termCiFriendlyname" value="${content.title}"/>
			</c:if>
			<c:choose>
				<c:when test="${status.first}">
					<c:set var="className" value="first"/>
				</c:when>
				<c:when test="${status.last}">
					<c:set var="className" value="last"/>
				</c:when>
				<c:otherwise>
					<c:set var="className" value=""/>
				</c:otherwise>
			</c:choose>
			<li class="${className}"><a href="${itemURL}">${termCiFriendlyname}</a></li>
		</c:forEach>
	</ul>
</c:if>