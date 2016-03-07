<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%@ page import="com.vignette.ext.templating.util.RequestContext" %>
<%@ page import="com.vignette.ext.templating.util.PageUtil" %>
<%@ page import="com.vignette.ext.link.delivery.ETLDeliveryTranslator" %>

<%-- initialize component & results --%>
<templating:initComponent/>

<div class="errorMsg col_4">
	<c:if test="${not empty component.title}">
		<templating:textInlineEdit oid="${component.system.id}" attributexmlname="vgnExtTemplatingComponentTitle">
			<h1>${component.title}</h1>
		</templating:textInlineEdit>	
	</c:if>
	<c:if test="${not empty component.text}">
		<c:set var="etlContent" value="${component.text}" scope="request"/>
		<templating:textInlineEdit oid="${component.system.id}" attributexmlname="text">
			<sbg-templating:TranslateETLContent etlContent="${etlContent}"/>
		</templating:textInlineEdit>
	</c:if>
</div>