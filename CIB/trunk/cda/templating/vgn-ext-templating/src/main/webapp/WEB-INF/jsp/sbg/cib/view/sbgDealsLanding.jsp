<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>

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

<sbg-templating:getSmartListPaginationResults var="results" resultsPerPage="15" />

<%-- component title will typically be "Recent deals" --%>
<h1>${empty component.title?'Recent deals':component.title}</h1>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgSearchPagination.jsp"%>
<ul id="searchResults">
    <c:if test="${not empty results}">

        <c:forEach items="${results}" var="content" varStatus="contentStatus">
            <templating:contentLink var="linkUrl" channelId="${currentChannel.system.id}" oid="${content.system.id}"/>
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
                <templating:textInlineEdit oid="${content.system.id}" attributexmlname="TITLE">
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
                </templating:textInlineEdit>
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
                <templating:textInlineEdit oid="${content.system.id}" attributexmlname="SUMMARY">
                <p> ${content.shortDescription} </p>
                </templating:textInlineEdit>
                <c:if test="${not empty content.fullDescription}">
                    <a href="${linkUrl}" target="_blank" class="articleLink modal" rel="${content.system.id}" onclick='return trackContentClick("${breadCrumbPath}${content.title}","${siteName}")'>Full article &raquo;</a>
                </c:if>
            </li>
		</c:forEach>
	</c:if>
</ul>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgSearchPagination.jsp"%>
<br class="clearBoth" />
