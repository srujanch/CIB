<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>

<div id="accolades" class="listedItem portlet">
	<h1><a href="/cib/accolades">${empty component.title?'Recent accolades':component.title}</a></h1>
    <c:if test="${not empty component.results}">
        <ul id="accoladeList">
			<c:forEach items="${component.results}" var="content">
                <li>
                    <h3>${content.title}, ${content.latestYear}</h3>
                    <p>${content.publication} </p>
				</li>
			</c:forEach>
		</ul>
	</c:if>
    <p class="textRight"><a href="/cib/accolades" class="right">See all accolades &raquo;</a></p>
</div>