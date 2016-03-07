<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent var="content"/>
<c:if test="${not empty content}">
	<iframe name="peopleClick" width="990" scrolling="auto" height="1050" frameborder="0" src="${content.url}"></iframe>
</c:if>