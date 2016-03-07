<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>

<templating:initRequestContext var="rc"/>

<sbg-templating:getAccoladeData var="results" />

<c:if test="${empty results}">
    <h2>No recent accolades found.</h2>
</c:if>
<c:if test="${not empty results}">
    <h2>${empty component.title?'Accolades':component.title}</h2>
    <div class="clearBoth col_5" id="content">
        <div class="accolades">
        <ul id="searchResults">
            <c:set var="oldPublication" value="" />
            <c:set var="oldLetter" value="" />
            <c:forEach items="${results}" var="entry" varStatus="status">
                <li>
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
        </div>
    </div>
</c:if>
<br class="clearBoth" />
<script type="text/javascript">
    ( function($) {
        $(document).ready(function() { // bodyguard function
            $('ul#searchResults li h2').prepend('<span class="searchResultsIcon">&nbsp;<\/span>');
        });// bodyguard function
    }) (jQuery);
</script>




