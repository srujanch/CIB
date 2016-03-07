<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>

<c:if test="${not empty component.results}">
	<div id="mediaRelContact" class="search portlet">
		<h1>Media relations contact</h1>
		<ul class="countryContacts">
			<c:forEach items="${component.results}" var="content">
				<li>
					<span class="continent">${content.title}</span>
					<templating:sort result="sortedContacts" items="${content.SBG_RELATED_CONTACTS}" properties="contactOrder" order="ascending" />
					<c:set var="multipleContacts" value="${fn:length(sortedContacts)>1}"/>
					<c:forEach items="${sortedContacts}" var="contact" varStatus="status">
						<c:set var="contactInfo" value="${contact.contactId}"/>
						<c:if test="${not empty contactInfo.country}">
							<span class="country">${contactInfo.country}</span>
						</c:if>
						<span class="person">${contactInfo.firstName} ${contactInfo.surname}</span>
						<span class="phone">${contactInfo.telephoneNumber1}</span>
						<a class="email" rel="tooltip" title="${contactInfo.emailId}" href="${contactInfo.emailId}" onclick="return trackActionClick('${contactInfo.emailId}');">${contactInfo.emailId}</a>
						<c:if test="${multipleContacts && !status.last}">
							<br class="clearBoth" />
						</c:if>
					</c:forEach>
				</li>
			</c:forEach>
		</ul>
	</div>
</c:if>