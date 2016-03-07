<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%@ page import="com.vignette.ext.templating.util.RequestContext" %>
<%@ page import="com.vignette.ext.templating.util.PageUtil" %>
<%@ page import="com.vignette.ext.link.delivery.ETLDeliveryTranslator" %>

<%-- initialize component & results --%>
<templating:contentItem result="contetItem"/>

<div id="teaser" class="portlet">
	<templating:textInlineEdit oid="${contetItem.system.id}" attributexmlname="TITLE">
		<h1>${contetItem.title}</h1>
	</templating:textInlineEdit>
	<templating:textInlineEdit oid="${contetItem.system.id}" attributexmlname="DESCRIPTION">
		<sbg-templating:TranslateETLContent etlContent="${contetItem.description}"/>
	</templating:textInlineEdit>
</div>