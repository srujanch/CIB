<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>

<c:set var="imagePath" value="${defaultImagePath}"/>
<c:if test="${not empty component.image}">
	<c:set var="imagePath" value="${SFPathPrefix}${component.image.placementPath}"/>
</c:if>
<div class="errorImage col_1 textCenter colLeft">
	<templating:imageInlineEdit oid="${component.system.id}" attributexmlname="image">
		<img src="${imagePath}" alt="errorPage"/>
	</templating:imageInlineEdit>
</div>