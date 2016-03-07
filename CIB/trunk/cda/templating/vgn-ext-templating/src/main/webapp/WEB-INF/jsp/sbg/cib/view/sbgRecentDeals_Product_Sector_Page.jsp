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
    <c:if test="${!status.first}">
        <c:set var="breadCrumbPath" value="${breadCrumbPath}${bredName}:" />
    </c:if>
</c:forEach>
<c:if test="${not empty currentChannel.parentSite}">
    <c:set var="siteName" value="${fn:toUpperCase(currentChannel.parentSite.system.name)}" />
</c:if>

<c:if test="${not empty component.results}">
<div id="deals" class="portlet">
	<h1 class="left">${empty component.title ?'Recent deals':component.title}</h1>
    <br class="clearBoth" />
        <c:forEach items="${component.results}" var="content">
            <templating:contentLink var="linkUrl" channelId="${currentChannel.system.id}" oid="${content.system.id}"/>
            <div class="portletItem">
                    <c:choose>
                        <c:when test="${not empty content.fullDescription}">
                            <h3><a href="${linkUrl}" target="_blank" class="modal" rel="${content.system.id}" onclick='return trackContentClick("${breadCrumbPath}${content.title}","${siteName}")'>${content.title}</a></h3>
                        </c:when>
                        <c:otherwise>
                              <h3>${content.title}</h3>
                        </c:otherwise>
                    </c:choose>
                <div class="portletText">
                        <p>${content.shortDescription}</p>
				</div>
                <c:if test="${not empty content.fullDescription}">
                    <a href="${linkUrl}" target="_blank" class="articleLink modal" rel="${content.system.id}" onclick='return trackContentClick("${breadCrumbPath}${content.title}","${siteName}")'>Full article</a>
                </c:if>
            </div>
		</c:forEach>
</div>
</c:if>