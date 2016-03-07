<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>


<c:if test="${not empty component.results}">
	<c:set var="generalPageContetItem" value="${component.results[0]}"/>
	<!-- <div class="col_4"> -->
	<div>
		<templating:textInlineEdit oid="${generalPageContetItem.system.id}" attributexmlname="TITLE">
			<h2>${generalPageContetItem.title} </h2>
		</templating:textInlineEdit>
		<templating:textInlineEdit oid="${generalPageContetItem.system.id}" attributexmlname="INTRODUCTION">
			<p>${generalPageContetItem.introduction}</p>
		</templating:textInlineEdit>
		<c:if test="${not empty generalPageContetItem.sbgGeneric}">
			<templating:textInlineEdit oid="${generalPageContetItem.sbgGeneric.system.id}" attributexmlname="DESCRIPTION">
				<sbg-templating:TranslateETLContent etlContent="${generalPageContetItem.sbgGeneric.description}"/>
			</templating:textInlineEdit>
		</c:if>
		<templating:textInlineEdit oid="${generalPageContetItem.system.id}" attributexmlname="DESCRIPTION">
			<sbg-templating:TranslateETLContent etlContent="${generalPageContetItem.description}"/>
		</templating:textInlineEdit>		
	</div>
</c:if>

<script type="text/javascript">
	$(document).ready(function() { // bodyguard function
		$('.mediaGalleryButtons').on('click','.detailView',function(e){
			$('.galleryDisplay').attr('class','galleryDisplay listView');
			$('.mediaGalleryButtons .gridView').removeClass('selected');
			$(this).addClass('selected');
		})
		$('.mediaGalleryButtons').on('click','.gridView',function(e){
			$('.galleryDisplay').attr('class','galleryDisplay thumbView');
			$('.mediaGalleryButtons .detailView').removeClass('selected');
			$(this).addClass('selected');
		})
	});// bodyguard function
</script>