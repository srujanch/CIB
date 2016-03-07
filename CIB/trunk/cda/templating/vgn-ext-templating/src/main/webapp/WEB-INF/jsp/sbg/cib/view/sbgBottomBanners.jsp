<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>

<c:if test="${not empty component.results}">
	<div id="homeBottomBanners" class="horzBanners">
		<ul class="promoBanners">
			<c:forEach items="${component.results}" var="bannerContetItem" varStatus="status">
				<c:set var="item" value="${bannerContetItem}"/>
				<%@include file="/WEB-INF/jsp/sbg/common/include/sbgWebLinkManagement.jsp"%>
				<%@include file="/WEB-INF/jsp/sbg/cib/view/include/getBannerImagePath.jsp"%>
                <%@include file="/WEB-INF/jsp/sbg/common/sitecatalyst/sbgBannerTracking.jsp"%>
                <li><a href="${itemURL}" class="${overLayClass}" target="${linkTarget}"${relAtt} ${click} id="hpgBanner${status.count}"><img class="banner" alt="${altText}" src="${imagePath}" /></a></li>
			</c:forEach>		
		</ul>
		<br class="clearBoth" />
	</div>
</c:if>
	