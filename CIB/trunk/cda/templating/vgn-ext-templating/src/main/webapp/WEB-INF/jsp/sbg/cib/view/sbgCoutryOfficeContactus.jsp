<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:contentItem result="content"/>
<templating:initRequestContext var="rc"/>

<c:set var="headOfficeAddress" value="${content.headOfficeAddress}"/>

<c:if test="${not empty headOfficeAddress}">
	<div id="contactUs" class="countryPages portlet">
		<h1>${component.title}</h1>
		<ul class="countryContacts">
			<li>
				<h2>${headOfficeAddress.bankName}</h2>
				<sbg-templating:TranslateETLContent etlContent="${headOfficeAddress.address}"/>				
				<c:if test="${not empty headOfficeAddress.SBG_RELATED_CONTACTS}">
					<templating:sort result="sortedContacts" items="${headOfficeAddress.SBG_RELATED_CONTACTS}" properties="contactOrder" order="ascending" />
						<c:forEach items="${sortedContacts}" var="contact">
							<c:set var="contactInfo" value="${contact.contactId}"/>
							<p>
								<span class="person">${contactInfo.firstName} ${contactInfo.surname}</span>
								<c:if test="${not empty contactInfo.designation}">
									<span class="role">${contactInfo.designation}</span>
								</c:if>
								<c:if test="${not empty contactInfo.telephoneNumber1}">
									<span class="phone">${contactInfo.telephoneNumber1}</span>
								</c:if>
								<c:if test="${not empty contactInfo.telephoneNumber2}">
									<span class="phone">${contactInfo.telephoneNumber2}</span>
								</c:if>
								<a class="email" rel="tooltip" title="${contactInfo.emailId}" href="mailto:${contactInfo.emailId}" onclick="return trackActionClick('${contactInfo.emailId}');">${contactInfo.emailId}</a>
							</p>
						</c:forEach>
				</c:if>					
			</li>
		</ul>
	</div>
</c:if>