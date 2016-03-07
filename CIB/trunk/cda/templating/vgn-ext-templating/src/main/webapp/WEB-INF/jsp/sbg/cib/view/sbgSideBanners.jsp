<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>

<c:if test="${not empty component.results}">
	<ul class="promoBanners">
		<c:forEach items="${component.results}" var="bannerContetItem">
			<c:set var="item" value="${bannerContetItem}"/>
			<%@include file="/WEB-INF/jsp/sbg/common/include/sbgWebLinkManagement.jsp"%>
			<%@include file="include/getBannerImagePath.jsp"%>
			<li><a class="bannerImage${overLayClass}" href="${itemURL}" target="${linkTarget}"${relAtt}><img src="${imagePath}" alt="${altText}" /></a></li>
		</c:forEach>
	</ul>	
</c:if>