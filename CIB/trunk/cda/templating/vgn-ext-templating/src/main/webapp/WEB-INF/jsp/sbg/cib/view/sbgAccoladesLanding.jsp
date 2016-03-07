<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initRequestContext var="rc"/>

<sbg-templating:getAccoladeData var="results" />

<div class="clearBoth col_5" id="content">
    <h1>${empty component.title?'Recent accolades':component.title}</h1>
    <ul class="inline" id="jumpTo">
        <c:set var="alphaBets" value="ABCDEFGHIJKLMNOPQRSTUVWXYZ" />
        <c:set var="jumpToLetter" value="" />

        <c:forEach items="${results}" var="entry">
            <c:set var="currentLetter" value="${fn:substring(entry.key,0,1)}" />
            <c:set var="jumpToLetter" value="${jumpToLetter}, ${currentLetter}" />
        </c:forEach>

        <c:forEach var="i" begin="0" end="25" step="1">
            <c:set var="currentLetter" value="${fn:substring(alphaBets,i,i+1)}" />
            <c:choose>
                <c:when test="${fn:contains(jumpToLetter, currentLetter)}">
                    <li><a href="#${currentLetter}">${currentLetter}</a></li>
                </c:when>
                <c:otherwise>
                    <li>${currentLetter}</li>
                </c:otherwise>
            </c:choose>
        </c:forEach>
    </ul>

    <ul id="searchResults">
        <c:set var="oldPublication" value="" />
        <c:set var="oldLetter" value="" />
        <c:forEach items="${results}" var="entry" varStatus="status">
            <li>
                <a name="${fn:substring(entry.key,0,1)}" class="jumpToLink"></a>
                <h2>${entry.key}</h2>
                <c:set var="values" value="${entry.value}" />
                <c:forEach items="${values}" var="content">
                    <sbg-templating:getAccoladePublicationYears var="pubYears" oid="${content.system.id}" order="desc" separator=";" />
                    <templating:textInlineEdit oid="${content.system.id}" attributexmlname="TITLE">
                        <p>${content.title} (${pubYears})</p>
                    </templating:textInlineEdit>
                </c:forEach>
            </li>
        </c:forEach>
    </ul>
    <br class="clearBoth" />

</div>



