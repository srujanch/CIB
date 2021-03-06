<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:contentItem result="bannerContetItem"/>

<div id="countryOffices" class="search portlet">
	<h1>${bannerContetItem.title}</h1>
	<c:set var="item" value="${bannerContetItem}"/>
	<%@include file="/WEB-INF/jsp/sbg/common/include/sbgWebLinkManagement.jsp"%>
	<%@include file="/WEB-INF/jsp/sbg/cib/view/include/getBannerImagePath.jsp"%>
    <%@include file="/WEB-INF/jsp/sbg/common/sitecatalyst/sbgBannerTracking.jsp"%>
    <p><a href="${itemURL}" class="${overLayClass}" target="${linkTarget}"${relAtt}  ${click}><img src="${imagePath}" alt="${altText}" /></a></p>
</div>