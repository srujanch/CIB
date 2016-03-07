<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>

<sbg-templating:getNewsSearch var="searchList" />

<h1>${empty component.title?'News search results':component.title}</h1>
    <c:choose>
        <c:when test="${not empty totalMatches && totalMatches gt 0}">
            <h2>${totalMatches} results found displaying page ${currentPageIndex} of ${numberOfPages}. </h2>
        </c:when>
        <c:otherwise>
            <h2>Your search returned no results.</h2>
        </c:otherwise>
    </c:choose>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgSearchPagination.jsp"%>
<ul id="searchResults">
    <c:if test="${not empty searchList}">
        <c:forEach items="${searchList}" var="content">
            <templating:contentLink var="linkUrl" oid="${content.system.id}"/>
            <li>
				<h2><a href="${linkUrl}">${content.title}</a></h2>
				<h4><fmt:formatDate value="${content.liveDate}" pattern="dd MMMM yyyy" /></h4>
				<p>${content.introduction}</p>
				<a href="${linkUrl}"  class="articleLink">Full article &raquo;</a>
			</li>
        </c:forEach>
    </c:if>
</ul>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgSearchPagination.jsp"%>
<br class="clearBoth" />

