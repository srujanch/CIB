<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>

<c:if test="${not empty component.results}">
	<div id="mainPromo">
		<div id="slidebox">
			<a class="next">&nbsp;</a><a class="previous">&nbsp;</a>
			<div class="thumbs">
				<c:forEach var="i" begin="1" end="${fn:length(component.results)}">
					<a href="#" onclick="Slidebox('${i}');return false" class="thumb">${i}</a>
				</c:forEach>
			 </div>
			 <div class="container">
				<c:forEach items="${component.results}" var="bannerContetItem">
                    <c:set var="item" value="${bannerContetItem}"/>
				    <%@include file="/WEB-INF/jsp/sbg/common/include/sbgWebLinkManagement.jsp"%>
                    <%@include file="/WEB-INF/jsp/sbg/cib/view/include/getBannerImagePath.jsp"%>
                    <%@include file="/WEB-INF/jsp/sbg/common/sitecatalyst/sbgBannerTracking.jsp"%>
                    <div class="content">
						<div>
                            <c:choose>
                                <c:when test="${itemURL eq '#'}">
                                    <img src="${imagePath}" alt="${altText}"/>
                                </c:when>
                                <c:otherwise>
                                     <a href="${itemURL}" class="${overLayClass}" target="${linkTarget}"${relAtt} ${click}><img alt="${altText}" src="${imagePath}" /></a>
                                </c:otherwise>
                            </c:choose>
                         </div>
                    </div>
				</c:forEach>
			 </div>
		</div>
	</div>
</c:if>
<!--<img class="banner" src="banners/CIB/hpgBanner0.jpg" />-->