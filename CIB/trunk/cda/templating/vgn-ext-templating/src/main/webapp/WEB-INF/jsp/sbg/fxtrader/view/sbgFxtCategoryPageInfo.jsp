<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>

<c:if test="${not empty component.results}">
	<c:set var="categoryContentItem" value="${component.results[0]}"/>
	<c:if test="${fn:length(categoryContentItem.SBG_RELATED_CATEGORY)<2}">
		<%@include file="include/sbgCategorySummary.jsp"%>
	</c:if>
	<templating:sort result="categories" items="${categoryContentItem.SBG_RELATED_CATEGORY}" properties="categoryOrder" order="ascending" />
	<c:forEach items="${categories}" var="category" varStatus="status">
		<c:set var="categoryTitle" value="${category.categoryTitle}"/>
		<c:set var="tabFriendlyName" value='${fn:replace(categoryTitle," ","")}'/>
		<c:set var="displayStyle" value="display:none;"/>
		<c:if test="${status.first}">
			<c:set var="displayStyle" value=""/>
		</c:if>
		<div style="${displayStyle}" class="tabBody" id="${tabFriendlyName}">
			<c:set var="faqsLength" value="0"/>
			<c:catch var="nullPointerExc">
				<c:set var="faqsLength" value="${fn:length(category.SBG_RELATED_FAQ)}"/>
			</c:catch>
			<c:choose>
				<c:when test="${faqsLength>0}">
					<templating:sort result="faqs" items="${category.SBG_RELATED_FAQ}" properties="faqOrder" order="ascending" />
					<ul class="accordian toggle">
						<c:forEach items="${faqs}" var="faq" varStatus="status">
							<c:set var="faqTitle" value="${faq.faqTitle}"/>
							<c:set var="faqAnswer" value="${faq.faqAnswer}"/>
							<li>
								<h3>${faqTitle}</h3>
								<div class="panel">${faqAnswer}</div>
							</li>
						</c:forEach>
					</ul>
				</c:when>
				<c:otherwise>
					<strong>No Items Listed under this Category.</strong>
				</c:otherwise>
			</c:choose>
		</div>
	</c:forEach>	
</c:if>