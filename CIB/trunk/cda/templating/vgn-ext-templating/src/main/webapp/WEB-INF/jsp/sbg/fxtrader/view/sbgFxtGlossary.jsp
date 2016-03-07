<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>
<%@ taglib uri="/WEB-INF/sbg-templating.tld" prefix="sbgTemplating" %>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>

<c:if test="${not empty component.results}">
	<c:set var="glossaryContentItem" value="${component.results[0]}"/>
	<c:set var="alphabetsArray" value='${fn:split("A,B,C,D,E,F,G,H,I,J,K,L,M,NO,P,Q,R,S,T,U,V,W,X,Y,Z",",")}'/>
	<jsp:useBean id="categoryTitles" class="za.co.standardbank.sbg.cda.templating.web.util.SBGArrayList"/>
	<templating:sort result="sortedCategories" items="${glossaryContentItem.SBG_RELATED_CATEGORY}" properties="categoryTitle" order="ascending" />
	<c:forEach items="${sortedCategories}" var="category">
		<c:set target="${categoryTitles}" property="addItem" value="${category.categoryTitle}"/>
	</c:forEach>
	
	<h1>Glossary</h1>
	<a name="Top">&nbsp;</a>
	<div class="paginationMenu padL5">
		<ul class="pagination">
			<c:forEach items="${alphabetsArray}" var="alphabet">
				<c:choose>
					<c:when test="${sbgTemplating:contains(categoryTitles,alphabet)}">
						<li><a href="#${alphabet}">${alphabet}</a></li>
					</c:when>
					<c:otherwise>
						<li class="previousOff">${alphabet}</li>
					</c:otherwise>
				</c:choose>				
			</c:forEach>			
		</ul>
	</div>
	<br class="clearBoth" />
	
	<c:forEach items="${sortedCategories}" var="category">
		<c:set var="categoryTitle" value="${category.categoryTitle}"/>
		<a class="keyIndex" name="${categoryTitle}" id="${categoryTitle}">${categoryTitle}</a>

		<templating:sort result="sortedGlossaries" items="${category.SBG_RELATED_FAQ}" properties="faqOrder" order="ascending" />
		<c:forEach items="${sortedGlossaries}" var="glossaryItem">
			<p><strong>${glossaryItem.faqTitle}</strong><br />${glossaryItem.faqAnswer}</p>
		</c:forEach>
		<a href="#Top" class="back2Top">back to top</a>
	</c:forEach>
</c:if>
