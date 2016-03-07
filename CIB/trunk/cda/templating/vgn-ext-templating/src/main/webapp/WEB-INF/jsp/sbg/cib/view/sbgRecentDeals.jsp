<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>

<c:set var="breadCrumbNameList" value="${currentChannel.breadcrumbNamePath}" />
<c:set var="breadCrumbPath" value="" />
<c:set var="siteName" value="" />
<c:forEach items="${breadCrumbNameList}" var="bredName" varStatus="status">
    <c:set var="breadCrumbPath" value="${breadCrumbPath}${bredName}:" />
</c:forEach>
<c:if test="${not empty currentChannel.parentSite}">
    <c:set var="siteName" value="${fn:toUpperCase(currentChannel.parentSite.system.name)}" />
</c:if>

<div id="deals" class="portlet">
	<h1 class="left"><a href="/cib/deals">${empty component.title ?'Recent deals':component.title}</a></h1>
    <a class="right moreInfo" href="/cib/deals">More deals &raquo;</a>
    <br class="clearBoth" />
    <c:if test="${not empty component.results}">
        <c:forEach items="${component.results}" var="content">
            <templating:contentLink var="linkUrl" channelId="${currentChannel.system.id}" oid="${content.system.id}"/>
            <c:set var="countries" value="" />
            <c:set var="fullDescription" value="" />
            <c:if test="${not empty content.fullDescription}">
                <c:set var="fullDescription" value="${content.fullDescription}" />
            </c:if>
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
            <div class="portletItem">
				<templating:textInlineEdit oid="${content.system.id}" attributexmlname="TITLE">
                    <c:choose>
                        <c:when test="${not empty content.fullDescription}">
                            <h3><a href="${linkUrl}" target="_blank" class="modal" rel="${content.system.id}" onclick='return trackContentClick("${breadCrumbPath}${content.title}","${siteName}")'>${content.title}, ${countries}</a></h3>
                        </c:when>
                        <c:otherwise>
                              <h3>${content.title}, ${countries}</h3>
                        </c:otherwise>
                    </c:choose>
                </templating:textInlineEdit>
                <div class="portletText">
                    <templating:textInlineEdit oid="${content.system.id}" attributexmlname="SUMMARY">
                        <p>${content.shortDescription}</p>
                    </templating:textInlineEdit>
				</div>
                <c:if test="${not empty content.fullDescription}">
                    <a href="${linkUrl}" target="_blank" class="articleLink modal" rel="${content.system.id}" onclick='return trackContentClick("${breadCrumbPath}${content.title}","${siteName}")'>Full article</a>
                </c:if>
            </div>
		</c:forEach>
	</c:if>
</div>