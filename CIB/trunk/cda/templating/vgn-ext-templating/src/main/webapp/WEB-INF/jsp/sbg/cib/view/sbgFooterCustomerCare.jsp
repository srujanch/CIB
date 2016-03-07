<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:contentItem result="content"/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>

<c:if test="${not empty content}">
	<div id="customerCare" class="portlet">
		<h1>${content.firstName}</h1>
		<h3>${content.introduction}</h3>
		<ul>
			<li><span class="telephoneIcon">&nbsp;</span> <templating:textInlineEdit oid="${content.system.id}" attributexmlname="TELEPHONE-NUMBER1">${content.telephoneNumber1}</templating:textInlineEdit> / <templating:textInlineEdit oid="${content.system.id}" attributexmlname="TELEPHONE-NUMBER2">${content.telephoneNumber2}</templating:textInlineEdit></li>
			<li><a href="mailto:${content.emailId}" onClick="trackActionClick('${content.emailId}');if (typeof vuit !== 'undefined') {  if(typeof vui.cps.ui.ice.FLOATIE_MODE !== 'undefined') { return (vui.cps.ui.ice.FLOATIE_MODE !== 'inline'); } else return true; } else { return true; }"> <span class="emailIcon">&nbsp;</span> <templating:textInlineEdit oid="${content.system.id}" attributexmlname="EMAILID">${content.emailId}</templating:textInlineEdit></a></li>
		</ul>
	</div>
</c:if>