<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<div id="accolades" class="portlet">
	<h1> <a href="/cib/accolades">${empty component.title?'Accolades':component.title}</a></h1>
    <c:if test="${not empty component.results}">
        <ul id="accoladeList">
			<c:forEach items="${component.results}" var="content">
                <li>
					<templating:textInlineEdit oid="${content.system.id}" attributexmlname="TITLE">
                        <h3>${content.title}, ${content.latestYear}</h3>
                    </templating:textInlineEdit>
                    <p>${content.publication} </p>
				</li>
			</c:forEach>
		</ul>
	</c:if>
    <a href="/cib/accolades" class="right">View more accolades &raquo;</a>
</div>
<!--<a href="#" id="allInfo" class="moreInfo clearBoth">View all article feeds</a>-->