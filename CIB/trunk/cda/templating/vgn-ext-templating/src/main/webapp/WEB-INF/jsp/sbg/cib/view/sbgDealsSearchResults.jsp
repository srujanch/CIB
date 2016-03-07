<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>
<c:set var="breadCrumbNameList" value="${currentChannel.breadcrumbNamePath}" />
<c:set var="breadCrumbPath" value="" />
<c:set var="siteName" value="" />
<c:forEach items="${breadCrumbNameList}" var="bredName" varStatus="status">
    <c:if test="${!status.first}">
        <c:set var="breadCrumbPath" value="${breadCrumbPath}${bredName}:" />
    </c:if>
</c:forEach>
<c:if test="${not empty currentChannel.parentSite}">
    <c:set var="siteName" value="${fn:toUpperCase(currentChannel.parentSite.system.name)}" />
</c:if>

<sbg-templating:getDealsSearch var="searchList" />
<c:if test="${not empty param.tabResults}">
<span class="searchResult">
</c:if>
<h1>${empty component.title?'Deals search results':component.title}</h1>
    <c:choose>
        <c:when test="${not empty totalMatches && totalMatches gt 0}">
            <h2>${totalMatches} results found displaying page ${currentPageIndex} of ${numberOfPages}. </h2>
        </c:when>
        <c:otherwise>
            <h2>Your search returned no results.</h2>
        </c:otherwise>
    </c:choose>
<c:choose>
    <c:when test="${not empty param.tabResults}">
        <%@include file="include/sbgDealsSearchTabPagination.jsp"%>
    </c:when>
    <c:otherwise>
        <%@include file="/WEB-INF/jsp/sbg/common/include/sbgSearchPagination.jsp"%>
    </c:otherwise>
</c:choose>
<ul id="searchResults">
    <c:if test="${not empty searchList}">
        <c:forEach items="${searchList}" var="content" varStatus="contentStatus">
            <templating:contentLink var="linkUrl" oid="${content.system.id}"/>
            <c:set var="countries" value="" />
            <c:set var="products" value="" />
            <c:if test="${not empty content.SBG_RELATED_COUNTRIES}">
                <c:forEach items="${content.SBG_RELATED_COUNTRIES}" var="country" varStatus="status">
                    <sbg-templating:getCountryLabel var="countryLabel" countryKey="${country.countryId}" />
                      <c:choose>
                          <c:when test="${status.last}">
                            <c:set var="countries" value="${countries}${countryLabel}" />
                          </c:when>
                          <c:otherwise>
                             <c:set var="countries" value="${countries}${countryLabel}, " />
                          </c:otherwise>
                      </c:choose>
                </c:forEach>
            </c:if>
            <c:if test="${not empty content.SBG_RELATED_PRODUCTS}">
                <c:forEach items="${content.SBG_RELATED_PRODUCTS}" var="product" varStatus="status">
                      <c:choose>
                          <c:when test="${status.last}">
                            <c:set var="products" value="${products}${product.productId}" />
                          </c:when>
                          <c:otherwise>
                             <c:set var="products" value="${products}${product.productId}, " />
                          </c:otherwise>
                      </c:choose>
                </c:forEach>
            </c:if>
            <li>

                <h2>
                    <c:choose>
                        <c:when test="${not empty content.fullDescription}">
                            <a href="${linkUrl}" target="_blank" class="modal" rel="${content.system.id}" onclick='return trackContentClick("${breadCrumbPath}${content.title}","${siteName}")'>${content.title}, ${countries}</a>
                        </c:when>
                        <c:otherwise>
                            ${content.title}, ${countries}
                        </c:otherwise>
                    </c:choose>

                </h2>

                <h4>
                   <c:choose>
                       <c:when test="${not empty products}">
                           <fmt:formatDate pattern="yyyy" value="${content.dealDate}" /> - ${products}
                       </c:when>
                       <c:otherwise>
                            <fmt:formatDate pattern="yyyy" value="${content.dealDate}" />
                       </c:otherwise>
                   </c:choose>
                </h4>

                <p> ${content.shortDescription} </p>

                <c:if test="${not empty content.fullDescription}">
                    <a href="${linkUrl}" target="_blank" class="articleLink modal" rel="${content.system.id}" onclick='return trackContentClick("${breadCrumbPath}${content.title}","${siteName}")'>Full article &raquo;</a>
                </c:if>
            </li>
        </c:forEach>
    </c:if>
</ul>
<c:choose>
    <c:when test="${not empty param.tabResults}">
        <%@include file="include/sbgDealsSearchTabPagination.jsp"%>
    </c:when>
    <c:otherwise>
        <%@include file="/WEB-INF/jsp/sbg/common/include/sbgSearchPagination.jsp"%>
    </c:otherwise>
</c:choose>
<br class="clearBoth" />
<c:if test="${not empty param.tabResults}">
    </span>
<script type="text/javascript">
( function($) {
	$(document).ready(function() { // bodyguard function

        $('#dealsSearch${param.tabResults}${param.vgnNextStartIndex} ul#searchResults li h2').prepend('<span class="searchResultsIcon">&nbsp;<\/span>');

    });// bodyguard function
}) (jQuery);
</script>
</c:if>