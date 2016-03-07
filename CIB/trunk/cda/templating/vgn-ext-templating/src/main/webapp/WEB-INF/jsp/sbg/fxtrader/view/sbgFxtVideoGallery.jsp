<%@include file="include/sbgShowVideosListWithPagination.jsp"%>

<script type="text/javascript">
	var vidWidth=420,vidHeight=320;
	function addVideo (thisVideo){
		$('#videoLayer').empty();
			var api = $( '#videoLayer' ).vid({
			type: 'vidavee',
			width:vidWidth,
			height:vidHeight,
			videoId : thisVideo 
		});
	}
	$(document).ready(function() { // bodyguard function
		$(document).on('click','.videoLinks a.imgGallery, .videoLinks a.viewButton, .videoLinks a.titleLink',function(e){
			e.preventDefault();		//Cancel the link behavior
			var id = $(this).attr('rel'),		//Get the A tag
			targetFile = $(this).attr('href'),
			maskHeight = $(document).height(),//Get the screen height and width
			maskWidth = $(window).width(),
			winH = $(window).height(),		//Get the window height and width
			winW = $(window).width(),
			activWinH=vidHeight + 90;
			activWinW=vidWidth + 90;
			
			$('#maskPerm').after('<div id="'+id+'" class="window stroke"><a href="#" class="closeIcon right close">&nbsp;</a><div class="contentArea"></div></div>');
			$('#mask').css({'width':maskWidth,'height':maskHeight});		//Set heigth and width to mask to fill up the whole screen
		//	$('#mask').fadeIn(1000);			//transition effect		
			$('#mask').fadeTo("slow",0.7);	
			$('#'+id).css('height',  activWinH).css('width',  activWinW).css('top',  winH/2-$('#'+id).height()/2).css('left', winW/2-$('#'+id).width()/2).fadeIn(2000);		//transition effect
			$('<div/>',{
				'id':'videoLayer',
				'class':'videoPlayer'
			}).appendTo('#'+id+' .contentArea');
			addVideo (targetFile);
		});
	});// bodyguard function
</script>