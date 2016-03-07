<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>

<c:if test="${not empty component.results}">
    <h1>${component.title}</h1>
    <div id="imgGallery">
        <c:forEach items="${component.results}" var="content">
            <c:set var="sfPath" value="${content.SOURCEPATH.placementPath}"/>
            <c:if test="${not empty sfPath}">
                <c:set var="sfPath" value="${SFPathPrefix}${sfPath}"/>
            </c:if>
        
            <c:if test="${not empty content.VCMCM_DSX_MEDIA_FORMAT}">    
                <c:forEach items="${content.VCMCM_DSX_MEDIA_FORMAT}" var="thumbNailImage">    
                    <c:if test="${thumbNailImage.FORMAT_MEDIAFORMATTYPE == 'small'}">    
                        <c:set var="thumbnailPath" value="${thumbNailImage.FORMAT_SOURCEPATH.placementPath}"/>
                        <c:if test="${not empty thumbnailPath}">
                            <c:set var="thumbnailPath" value="${SFPathPrefix}${thumbnailPath}"/>
                        </c:if>

                         <a class="imgGallery" href="${sfPath}" title="${content.TITLE}"><img src="${thumbnailPath}" alt="${content.ALTTEXT}"/>${content.ALTTEXT}</a>
                    </c:if>    
                </c:forEach>
            </c:if>
        </c:forEach>
    </div>
</c:if>

<script type="text/javascript">
	$(document).ready(function() {
		var $galleryName=$('#imgGallery');
		window.Lightbox = new jQuery().lightBoxGal({
			autoPlay:true,
			borderSize:21,
			classNames:'lightBox,imgGallery',
			descSliding:true,
			enableRightClick:true,
			enableSlideshow:true,
			resizeSpeed:7,
			slideTime:4,
			startZoom:true
		})
	});// bodyguard function
</script>