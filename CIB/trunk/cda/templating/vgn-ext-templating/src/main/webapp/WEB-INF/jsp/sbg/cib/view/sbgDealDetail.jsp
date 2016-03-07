<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:contentItem result="content"/>
<templating:initRequestContext var="rc"/>

<c:if test="${not empty content.image}">
<div class="headerImage padL20 col_4">
        <img class="headerImg" src="${SFPathPrefix}${content.image.SOURCEPATH.placementPath}" width="${content.image.HEIGHT}" />
</div>
</c:if>
<div id="dealInfo" class="pad20 col_4">
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
    <h1>${empty component.title?'Deal information':component.title}</h1>
    <h2>${content.title}, ${countries}</h2>
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
    <sbg-templating:TranslateETLContent etlContent="${content.fullDescription}"/>
    <br class="clearBoth" />
</div>


