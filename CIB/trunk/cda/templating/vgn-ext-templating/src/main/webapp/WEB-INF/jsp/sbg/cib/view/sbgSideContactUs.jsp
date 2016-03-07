<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>

<c:if test="${not empty component.results}">
	<div id="mediaRelContact" class="search portlet">
		<h1>${component.title}</h1>
		<ul class="countryContacts">
			<c:forEach items="${component.results}" var="contactInfo">
					<li>
						<c:if test="${not empty contactInfo.continent}">
							<templating:textInlineEdit oid="${contactInfo.system.id}" attributexmlname="CONTINENT">
								<span class="continent">${contactInfo.continent}</span>
							</templating:textInlineEdit>
						</c:if>
						<c:if test="${not empty contactInfo.country}">
							<templating:textInlineEdit oid="${contactInfo.system.id}" attributexmlname="COUNTRY">
								<span class="country">${contactInfo.country}</span>
							</templating:textInlineEdit>
						</c:if>
						<span class="person"><templating:textInlineEdit oid="${contactInfo.system.id}" attributexmlname="FIRST-NAME">${contactInfo.firstName}</templating:textInlineEdit> <templating:textInlineEdit oid="${contactInfo.system.id}" attributexmlname="SURNAME">${contactInfo.surname}</templating:textInlineEdit></span>
						<c:if test="${not empty contactInfo.designation}">
							<templating:textInlineEdit oid="${contactInfo.system.id}" attributexmlname="DESIGNATION">
								<span class="role">${contactInfo.designation}</span>
							</templating:textInlineEdit>
						</c:if>
						<c:if test="${not empty contactInfo.telephoneNumber1}">
							<templating:textInlineEdit oid="${contactInfo.system.id}" attributexmlname="TELEPHONE-NUMBER1">
								<span class="phone">${contactInfo.telephoneNumber1}</span>
							</templating:textInlineEdit>
						</c:if>
						<c:if test="${not empty contactInfo.telephoneNumber2}">
							<templating:textInlineEdit oid="${contactInfo.system.id}" attributexmlname="TELEPHONE-NUMBER2">
								<span class="phone">${contactInfo.telephoneNumber2}</span>
							</templating:textInlineEdit>
						</c:if>
						<c:if test="${not empty contactInfo.emailId}">
							<a class="email" rel="tooltip" title="${contactInfo.emailId}" href="mailto:${contactInfo.emailId}" onClick="trackActionClick('${contactInfo.emailId}');if (typeof vuit !== 'undefined') {  if(typeof vui.cps.ui.ice.FLOATIE_MODE !== 'undefined') { return (vui.cps.ui.ice.FLOATIE_MODE !== 'inline'); } else return true; } else { return true; }"><templating:textInlineEdit oid="${contactInfo.system.id}" attributexmlname="EMAILID">Email</templating:textInlineEdit></a>
						</c:if>
					</li>
			</c:forEach>
		</ul>
	</div>
</c:if>