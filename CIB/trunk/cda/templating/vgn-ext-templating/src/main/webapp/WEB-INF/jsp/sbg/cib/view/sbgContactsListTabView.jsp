<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:contentItem result="contetItem"/>

<c:if test="${not empty contetItem}">
	<div id="mediaRelContact" class="pageDisplay">
		<h1>${contetItem.title}</h1>
		<c:if test="${not empty contetItem.SBG_RELATED_CONTACTS}">  
			<templating:sort result="contacts" items="${contetItem.SBG_RELATED_CONTACTS}" properties="contactOrder" order="ascending" />
			<ul class="pageContacts">
				<c:forEach items="${contacts}" var="contact">
					<c:set var="contactItem" value="${contact.contactId}"/>
					<c:if test="${not empty contactItem}">
						<li>
							<c:if test="${not empty contactItem.continent}">
								<span class="continent">${contactItem.continent}</span>
							</c:if>
							<c:if test="${not empty contactItem.country}">
								<span class="country">${contactItem.country}</span>
							</c:if>
							<span class="person">${contactItem.firstName} ${contactItem.surname}</span>
							<c:if test="${not empty contactItem.designation}">
								<span class="role">${contactItem.designation}</span>
							</c:if>
							<c:if test="${not empty contactItem.telephoneNumber1}">
								<span class="phone">${contactItem.telephoneNumber1}</span>
							</c:if>
							<c:if test="${not empty contactItem.telephoneNumber2}">
								<span class="phone">${contactItem.telephoneNumber2}</span>
							</c:if>
							<c:if test="${not empty contactItem.emailId}">
								<a class="email" rel="tooltip" title="${contactItem.emailId}" href="mailto:${contactItem.emailId}" onclick="return trackActionClick('${contactItem.emailId}');">Email</a>
							</c:if>
						</li>
					</c:if>				
				</c:forEach>
			</ul>
		</c:if>
	</div>

	<script type="text/javascript">
	( function($) {
		$(document).ready(function() { // bodyguard function
			// call this for contact us icons
			$('#contactUsPage ul.pageContacts .person').prepend('<span class="contactIcon">&nbsp;<\/span>');
			$('#contactUsPage ul.pageContacts .phone').prepend('<span class="telephoneIcon">&nbsp;<\/span>');
			$('#contactUsPage ul.pageContacts .email').prepend('<span class="emailIcon">&nbsp;<\/span>');
		});// bodyguard function
	}) (jQuery);

	</script>	
</c:if>