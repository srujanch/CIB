<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>

<c:if test="${not empty component.results}">
<div id="accolades" class="listedItem portlet">
	<h1>${empty component.title?'Recent accolades':component.title}</h1>
        <ul id="accoladeList">
			<c:forEach items="${component.results}" var="content">
                <li>
                    <h3>${content.title}, ${content.latestYear}</h3>
                    <p>${content.publication} </p>
				</li>
			</c:forEach>
		</ul>
		<p class="textRight"><a href="/cib/accolades">See all accolades &raquo;</a></p>
</div>
</c:if>