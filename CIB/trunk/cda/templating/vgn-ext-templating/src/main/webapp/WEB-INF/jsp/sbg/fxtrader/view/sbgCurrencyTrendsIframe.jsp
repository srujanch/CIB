<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent var="content"/>
<c:if test="${not empty content}">
	<div class="portlet">
		<iframe height="185" frameborder="0" style="background-color:#FFFFFF; padding-top:10px" src="${content.url}"></iframe>
	</div>
</c:if>