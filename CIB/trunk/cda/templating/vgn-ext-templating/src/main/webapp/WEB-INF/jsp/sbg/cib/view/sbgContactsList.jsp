<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentSite" value="${rc.currentSiteBean}"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>

<c:set var="componentHeader" value="${component.header}"/>
<c:if test="${empty componentHeader}">
	<c:set var="componentHeader" value="Business  enquiries"/>
</c:if>

<h1>${component.title}</h1>
<h2>${componentHeader}</h2>
<ul class="countryContacts">
	<c:forEach items="${component.results}" var="content">
		<li>
			<h3>${content.title}</h3>
			<c:if test="${not empty content.SBG_RELATED_LINKS}">
				<templating:sort result="sortedLinks" items="${content.SBG_RELATED_LINKS}" properties="linkOrder" order="ascending" />
				<ul>
					<c:forEach items="${sortedLinks}" var="relatedLink">
						<c:set var="countryAdress" value="${relatedLink.linkToContent}"/>
						<li>
							<c:if test="${not empty countryAdress.SBG_RELATED_CONTACTS}">
								<templating:sort result="sortedContacts" items="${countryAdress.SBG_RELATED_CONTACTS}" properties="contactOrder" order="ascending" />
								<c:forEach items="${sortedContacts}" var="relatedContact" varStatus="status">
									<c:set var="individualContactInfo" value="${relatedContact.contactId}"/>
									<c:choose>
										<c:when test="${status.first}">
											<span class="country">${relatedLink.linkText}</span>
										</c:when>
										<c:otherwise>
											<span class="country">&nbsp;</span>
										</c:otherwise>
									</c:choose>
									<c:set var="phoneNo" value="${individualContactInfo.telephoneNumber1}"/>
									<c:set var="phoneNo" value="${empty phoneNo?'TBC':phoneNo}"/>
									<span class="person">${individualContactInfo.firstName} ${individualContactInfo.surname}</span>
									<span class="phone">${phoneNo}</span>
									<c:choose>
										<c:when test="${not empty individualContactInfo.emailId}">
											<a class="email" rel="tooltip" rev="250" title="${individualContactInfo.emailId}" href="mailto:${individualContactInfo.emailId}" onclick="return trackActionClick('${individualContactInfo.emailId}');">Email</a>
										</c:when>
										<c:when test="${not empty individualContactInfo.linkToUrl}">
											<c:set var="linkText" value="${individualContactInfo.linkText}"/>
											<c:set var="linkText" value="${empty linkText?'Click here':linkText}"/>
											<a class="email" rel="tooltip" title="This link will open in a new window" target="_blank" href="${individualContactInfo.linkToUrl}" onclick="return trackActionClick('${individualContactInfo.linkToUrl}');">${linkText} </a>
										</c:when>
										<c:otherwise>
											<a class="email" rel="tooltip" title="Not available" href="#">TBC</a>
										</c:otherwise>
									</c:choose>									
								</c:forEach>
							</c:if>
						</li>
					</c:forEach>
				</ul>
			</c:if>
		</li>
	</c:forEach>
</ul>