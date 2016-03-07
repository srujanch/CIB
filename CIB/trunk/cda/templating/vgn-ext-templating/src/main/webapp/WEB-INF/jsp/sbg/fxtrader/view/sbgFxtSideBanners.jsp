<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>

<c:if test="${not empty component.results}">
	<div class="portlet">
		<c:forEach items="${component.results}" var="bannerContetItem">
			<c:set var="item" value="${bannerContetItem}"/>
			<%@include file="/WEB-INF/jsp/sbg/common/include/sbgWebLinkManagement.jsp"%>
			<%@include file="/WEB-INF/jsp/sbg/cib/view/include/getBannerImagePath.jsp"%>
            <%@include file="/WEB-INF/jsp/sbg/common/sitecatalyst/sbgBannerTracking.jsp"%>
            <a href="${itemURL}" class="${overLayClass}" target="${linkTarget}"${relAtt} ${click}><img border="0" src="${imagePath}" alt="${altText}" /></a>
			<sbg-templating:TranslateETLContent etlContent="${bannerContetItem.description}"/>
		</c:forEach>
	</div>
</c:if>