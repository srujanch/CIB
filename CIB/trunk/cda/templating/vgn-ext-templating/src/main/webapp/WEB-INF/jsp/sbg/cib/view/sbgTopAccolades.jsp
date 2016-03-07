<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>

<div id="pageHeader" class="headerImage col_5 horzBanners">
	<!--<img class="headerImg" src="banners/CIB/pageHeader/pH175x150_prodServices.jpg" />-->
	<c:if test="${not empty component.title}">
		<h1>${component.title}</h1>
	</c:if>
	<c:if test="${not empty component.header}">
		<p>${component.header}</p>
	</c:if>
	<c:if test="${not empty component.results}">
		<ul id="popPromo" class="promoBanners">
			<c:forEach items="${component.results}" var="bannerContetItem">
				<c:if test="${not empty bannerContetItem}">
					<%@include file="/WEB-INF/jsp/sbg/cib/view/include/getBannerImagePath.jsp"%>
					<c:if test="${bannerContetItem.linkTo == 'contentInstance'}">
						<c:set var="accoladeContentItem" value="${bannerContetItem.linkToContent}"/>
					</c:if>
                    <%@include file="/WEB-INF/jsp/sbg/common/sitecatalyst/sbgBannerTracking.jsp"%>
                    <li>
						<a class="bannerImage" href="#" ${click}><img alt="${altText}" src="${imagePath}" /></a>
						<c:choose>
							<c:when  test="${not empty accoladeContentItem}">
								<c:set var="pressReleaseURL" value="#"/>
								<c:set var="pressReleaseContentItem" value="${accoladeContentItem.pressRelease}"/>
								<c:if test="${not empty pressReleaseContentItem}">
									<templating:contentLink var="pressReleaseURL" oid="${pressReleaseContentItem.system.id}"/>
								</c:if>
								<sbg-templating:getAccoladePublicationYears var="pubYears" oid="${accoladeContentItem.system.id}" order="desc" separator=";" />
								<div class="moreInfo">
									<h1>${accoladeContentItem.publication}</h1>
									<h2>${accoladeContentItem.title} (${pubYears})</h2>
									${bannerContetItem.description}
									<a href="${pressReleaseURL}">${bannerContetItem.linkText}</a>
								</div>
							</c:when>
							<c:otherwise>
								<div class="moreInfo">
									<h1>Please select an accolade for this Banner. </h1>
								</div>
							</c:otherwise>
						</c:choose>
					</li>
					<c:remove var="accoladeContentItem"/>
				</c:if>
			</c:forEach>
		</ul>
		<br class="clearBoth" />
	</c:if>
</div>

<script type="text/javascript">
	$(document).ready(function() { // bodyguard function
		$('ul#popPromo').on('click','li',function(e){
			var $this=$(this),
			id = 'window',
			maskHeight = $(document).height(),
			maskWidth = $(window).width(),
			winH = $(window).height()/2-$(id).height()/2,
			winW = $(window).width()/2-$(id).width()/2,
			$winPop = $('#'+id);
			if($('#window').length === 0){
				$this.attr('id',id);
				$('#'+id+' .moreInfo h1').before('<a class="close right" href="#">Close [X]</a>');
			//	alert($winPop.css('left'));
				e.preventDefault();		//Cancel the link behavior
				$('#maskPerm').css({'width':maskWidth,'height':maskHeight,'top':0});		//Set heigth and width to mask to fill up the whole screen
			//	$('#mask').fadeIn(1000);			//transition effect		
				$('#maskPerm').fadeTo("slow",0.8);	
			//	$winPop.css({'top':winH,'left':winW});
				$this.addClass('active').animate({'top':winH,'left':winW,'margin-left':-$('#window').width()/2});
				$winPop.fadeIn(2000);		//transition effect
			}
			$('ul#popPromo li.active').on('click','a.close',function(e){
				$('#window').removeClass('active').removeAttr('style').removeAttr('id');
				$('#maskPerm').fadeOut('medium');
				$('.close').remove();
			});
		});
				
	});// bodyguard function
</script>