<c:if test="${not empty bannerContetItem}">
    <c:set var="linkText" value="${empty bannerContetItem.linkText?bannerContetItem.title:bannerContetItem.linkText}" />
    <c:set var="click" value="" />
 <c:choose>
     <c:when test="${bannerContetItem.mediaItem.MEDIACLASSTYPE eq 'feature'}">
         <c:set var="click">
             onclick="return trackFeaturedLinkClick('${linkText}')"
         </c:set>
     </c:when>
     <c:when test="${bannerContetItem.mediaItem.MEDIACLASSTYPE eq 'banner'}">
        <c:set var="click">
             onclick="return trackBannerClick('${linkText}')"
         </c:set>
     </c:when>
     <c:otherwise>
         <c:set var="click" value="" />
     </c:otherwise>
 </c:choose>
</c:if>