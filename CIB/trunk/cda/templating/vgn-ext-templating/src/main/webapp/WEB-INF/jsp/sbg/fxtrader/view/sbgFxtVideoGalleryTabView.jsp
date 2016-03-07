<%@include file="include/sbgShowVideosListWithPagination.jsp"%>

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