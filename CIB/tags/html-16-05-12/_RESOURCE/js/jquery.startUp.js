( function($) {// bodyguard function
	$(document).ready(function() {
		var $scrollingDiv = $('.leftNav2ColSmlRightBar.maxi #rightContent .float'),
		padLeft = '6px',
		padRight = '6px',
		defpadLeft = $('#scroller li a').css('paddingLeft'),
		defpadRight = $('#scroller li a').css('paddingRight');
		$('#topBar ul.styleSelector').wrap('<div id="scrollWrap" class="styleSelector" >');
		$('#navBar').wrapInner('<div class="topNav">');
		$('#scroller').wrap('<div id="scrollWrap" class="styleSelector">');
	//	$('#scroller a').wrapInner('<span class="label">');
		$('#topBarFrame').wrapInner('<div id="topBar">');
		$('#crumbList').wrapInner('<div id="breadCrumbHolder" class="breadCrumbHolder module" >');
		$('#breadCrumbBar').wrap('<div id="crumbBar">');
		$('#navBar ul.styleSelector').wrap('<div class="topNavL1 left">');
		$('#accoladeList').wrap('<div class="accoladeItems">');
		$('.shortLinks li:last').addClass('last');
		$('ul.crossBanners li:last').addClass('last'); $('ul.crossBanners li:first').addClass('first');//banners for home page only
		$('.product a').wrapInner('<span class="prodText">');
		$('.product a').prepend('<span class="prodIcon">&nbsp;</span>');//for product page only
		$('#footerLegal').wrap('<div id="footerLegalBar">');
		$('ul#scroller').fadeIn('fast');
		sitemapstyler('leftNavMenu');
		$('#deals .portletItem').next('.portletItem').hide();
		$('#news .portletItem').next('.portletItem').hide();
		$('#breadCrumbItem').jBreadCrumb({easing:'swing'});
		$('a.settingIcon').click(function(e){
			e.preventDefault();
			$(this).toggleClass('over');
				if ($('#blankBar').children().is('div#settingsToolBar.tools')) {
					$('#settingsToolBar').remove();
					$('.removableClear').remove();
				} else {
					var $pin = $('<a href="#" class="pin" rel="tooltip" title="Pin or unpin the main navigation bar">&nbsp;</a>'),
					$textSizer = $('<ul class="textSize"><li><a href="Javascript:resizer(11);" class="p-s11" rel="tooltip" title="Adjust the text size to small font">Small font</a></li><li><a href="Javascript:resizer(13);" class="p-s13" rel="tooltip" title="Adjust the text size to large font">Large font</a></li></ul>'),
					$navColor = $('<a href="#" class="navColor" rel="tooltip" title="Switch navigation menu color">&nbsp;</a>'),
					$tools = $('<div id="tools">').append($pin, $textSizer, $navColor, $clearFloats), $clearFloats = $('<br class="clearBoth removableClear" />'),$settingsTb = $('<div id="settingsToolBar" class="tools">').append($tools);
					$('#blankBar').append($settingsTb, $clearFloats);
				}
				$('#crumbBar').toggleClass('expanded');
				$('#blankBar').slideDown('medium').toggleClass('expanded');
				$('#pageContentFrame').toggleClass('expanded');
		});
		$('#blankBar').on('click','a.pin, a.unPin',function(e){
			e.preventDefault();
			$(this).toggleClass('unPin').toggleClass('pin');
			$('#page').toggleClass('scroll').toggleClass('fixed');
		});
		$('#blankBar').on('click','a.navColor',function(e){$('#topBarFrame').toggleClass('altColor')});
		$('#allInfo').click(function(e){
			e.preventDefault();
			$('#information').toggleClass("expanded").toggleClass("collapsed").children().find('.portlet').slideToggle("medium");
			$('#deals .portletItem').next('.portletItem').slideToggle("medium");
			$('#news .portletItem').next('.portletItem').slideToggle("medium");
		});
		$('#page').on('mouseover','a[rel=tooltip]',function(e) {//Select all anchor tag with rel set to tooltip
			var tip, width, height;
			width = $(this).attr('rev'); //Grab the width from 'rev' attribute if specified
			tip = $(this).attr('title');//Grab the title attribute's value and assign it to a variable
			$(this).prepend('<div id="tooltip" class="callout">' + tip + '<div class="notchBorder"></div><div class="notch"></div></div>');//Append the tooltip template and its value
			$(this).attr('title','');//Remove the title attribute's to avoid the native tooltip from the browser
			if(width){
				$('#tooltip').css('width', width);
				height = $('#tooltip').height()+350;
				$('#tooltip').css('top', e.pageY + -height);
		//		alert(e.pageY);
			}else{
				$('#tooltip').css('top', e.pageY - 65 );//Set the X and Y axis of the tooltip
			}
			$('#tooltip').css('left', e.pageX + -40,'position','absolute' );
			$('#tooltip').fadeIn('500');//Show the tooltip with fadeIn effect
			$('#tooltip').fadeTo('10',0.9);
		}).on('mousemove','a[rel=tooltip]',function(e) {
			var width = $(this).attr('rev'); 
			if(width){
				$('#tooltip').css('width', width);
				if($(this).parent().is('.settings')){
					height = $('#tooltip').height()+50;
					$('#tooltip').css('top', e.pageY - height);
				}else{$('#debugging_status').html($(this).parent().attr('class'));
					height = $(this).offset().top;
					$('#tooltip').css('top',$(this).position().top-50);
				}
			}else{
				$('#tooltip').css('top', e.pageY - 65 );//Keep changing the X and Y axis for the tooltip, thus, the tooltip move along with the mouse
			}
			$('#tooltip').css('left', e.pageX + -40 );
		}).on('mouseout','a[rel=tooltip]',function() {
			$(this).attr('title',$('#tooltip').text());//Put back the title attribute's value
			$(this).children('div#tooltip').remove();//Remove the appended tooltip template
		});
		$('#leftNav a:contains(Products & services)').next().slideDown('medium');// This is used to open up the Products & services menu
		$('.styleSelector a').click(function(e){
		//	if($(this).parent('ul').contains('.noFollow')){
				e.preventDefault();
		//	};
			var style = $(this).attr('title');
			if($('#page').hasClass('website')){
				$('#page').attr('class', style+' website fixed');
			}else{
				$('#page').attr('class', style+' hpgVer2 fixed');
			}
		//	$('#pin a').attr('class','pin');
			$('.styleSelector a').parents('li.activeTab').attr('class','');
			$(this).parents('li').attr('class','activeTab')
			switch(style){
			case 'headerSBG_CIB':
				$('.banner').each(function(index) {
					 $(this).attr('src', 'banners/CIB/hpgBanner'+index+'.jpg');
				});
				break;
			case 'headerSBG_Priv':
				$('.banner').each(function(index) {
					 $(this).attr('src', 'banners/PC/hpgBanner'+index+'.jpg');
				});
				break;
			default:
				$('.banner').each(function(index) {
					 $(this).attr('src', 'banners/PBB/hpgBanner'+index+'.jpg');
				});
				break;
			}
		});
		
	//	 $('#topBar').append('<div id="debugging_mouse_axis" class="right">','<div id="debugging_status" class="right">');
		/////////////////////**************************TEST for product page option3 Expanding divs********************************88
		$('.leftNav2Columns.option3 .portletItem h1').append('<span class="arrowDn">&nbsp;</span>');
		$('.leftNav2Columns.option3 #products').on('mouseover',' h1',function(e){
			if($(this).children().is('.arrowDn')){
				$(this).children('.arrowDn').toggleClass('arrowDnOver');
			}else if($(this).children().is('.arrowUp')){
				$(this).children('.arrowUp').toggleClass('arrowUpOver');
			}
		}).click(function(e){
			$(this).next().slideToggle('medium');
			$(this).children('.arrowDn').toggleClass('arrowUp');//.toggleClass('arrowDn');
		});
		/////////////////////**************************TEST for Deals and news search results page option3********************************88
		$('ul#searchResults li h2').prepend('<span class="articleIcon">');
//scrolling div for search bar on deals and news pages affects only $('.leftNav2ColSmlRightBar.maxi #rightContent') because of class float 
		$(window).scroll(function(){
			if($('#page').is('.fixed')){
				var topOff = 0,
				topOff2 = 0;
			}else if($('#page').is('.scroll')){
				var topOff = -0,
				topOff2 = 0;
			}
			var y = $(this).scrollTop(),
			maxY = $('#footer').offset().top,
			startPos = $(this).scrollTop(),
			topFrameOff = $('#topBarFrame').offset().top,
			topFrameH = $('#topBarFrame').height(),
			scrollHeight = $scrollingDiv.height();	
	//		$('#debugging_status').html('topFrameOff:'+topFrameOff+' | topFrameH:'+topFrameH+' | scrollHeight:'+scrollHeight+' | maxY:'+maxY);
			if(y< maxY-scrollHeight || topFrameOff>topFrameH){
				$scrollingDiv.stop()//.delay(800)
					.animate({"marginTop": ($(window).scrollTop()+topOff) + "px"}, 6000 );
			}
			if(topFrameOff<topFrameH){
				$scrollingDiv.stop()//.delay(800)
						.animate({"marginTop": ($(window).scrollTop()+topOff2) + "px"}, 6000 );
			}
		});
		/////////////////////**************************TEST Alternate home page menu********************************88
		function clearNav(){
			$('p.arrowUpCb').remove();
			$('#topNavMenu li a').removeClass('active');
			$('#topMenuSub').empty();
		}
		$('ul#topNavMenu li a').click(function(e){
			var $this=$(this),
			maskHeight = $(document).height(),//Get the screen height and width
			maskWidth = $(window).width(),
			winH = $(window).height(),
			winW = $(window).width();//Get the window height and width
	//		$('pre.lazyLoad').lazyLoad();
			if ($this.next('div.navContent').length>0 && $('#topMenuSub').length === 0){
				e.preventDefault();
				if($('#topMenuSub').length === 0){
					$('#topMenuBar').after('<div id="topMenuSub"  class="topMenu">');
				}
	//		$('#debugging_status').html($('.active'));
				$('#mask').css({'width':maskWidth,'height':maskHeight,'top':-62});//Set height and width to mask to fill up the whole screen
				$('#mask').fadeTo("slow",0.7); 
				$this.addClass('active').append('<p class="arrowUpCb">');
				$this.next('div.navContent').clone().appendTo('#topMenuSub');
				$('#topMenuSub').append('<div id="closeMenu"><a href class="textCenter" rel="tooltip" title="Close navigation">&nbsp;</a></div><br class="clearBoth" />').slideDown('slow');
			}else if($('#topMenuSub').length > 0){
				clearNav();
//				$('p.arrowUpCb').remove();
//				$('#topNavMenu li a').removeClass('active');
//				$('#topMenuSub').empty();
				if ($this.next('div.navContent').length>0){
					e.preventDefault();
					$this.addClass('active').append('<p class="arrowUpCb">');
					$this.next('div.navContent').clone().appendTo('#topMenuSub');
					$('#topMenuSub').append('<div id="closeMenu"><a href class="textCenter" rel="tooltip" title="Close navigation">&nbsp;</a></div><br class="clearBoth" />').slideDown('slow');
				}else{
					$('#topMenuSub').slideUp('slow', function(e){$('#topMenuSub').remove()});
					$('#mask').fadeOut('medium');
				}
			}
		});
		$('#closeMenu a').click(function (e) {//if close button is clicked
			e.preventDefault();//Cancel the link behavior
			clearNav();
			$('#topMenuSub').slideUp('slow', function(e){$('#topMenuSub').remove()});
			$('#mask').fadeOut('medium');
		});
		$('#mask').click(function () {//if mask is clicked
			clearNav();
			$('#topMenuSub').slideUp('slow', function(e){$('#topMenuSub').remove()});
			$(this).fadeOut('medium');
		});   
		/////////////////////**************************Contact details page********************************88
		$('ul.countryContacts h3').wrapInner('<a href="#" class="region"><span class="label">');
		$('ul.countryContacts .region').append('<span class="arrowDn">&nbsp;</span>');
		$('ul.countryContacts .person').prepend('<span class="contactIcon">&nbsp;</span>');
		$('ul.countryContacts .phone').prepend('<span class="telephoneIcon">&nbsp;</span>');
		$('ul.countryContacts .email').prepend('<span class="emailIcon">&nbsp;</span>');
		$('ul.countryContacts ul li').hover(function(e){
			$(this).toggleClass('hover');
		});
		$('ul.countryContacts').on('click','.region',function(e){
			e.preventDefault();
			$(this).children('.arrowDn').toggleClass('arrowUp');
			$(this).parent().next('ul').slideToggle('medium');
		});
// Country Scroller
	$('#scroller li a').clone().appendTo('#scrollWrap').attr("title",function(e){return $(this).html();}).addClass('scrollDot').html('');
	$('#topBarFrame').mousewheel(function(event, delta, deltaX, deltaY) {
		var o = '',
		top_value;
		if (delta > 0){
			o = '#test2: up ('+delta+') #scroller.position ('+$('#scroller').position().top+')';
			if($('#scroller').position().top < 0){
				top_value=$('#scroller').position().top+30;
			}
		}else if (delta < 0){
			o = '#test2: down ('+delta+') #scroller.position ('+$('#scroller').position().top+')';
			if($('#scroller').position().top > -470){
				top_value=$('#scroller').position().top-30;
			}
		}
		if( o != '' ){
			//$('#debugging_status').html(o);
		}
		$('#scroller').animate({
			top: top_value
		}, {
			queue: false,
			duration: 100
		});	
		return false; // prevent default
	});
	$('#scroller li').click(function() {//Make LI clickable
		window.location = $(this).find('a').attr('href');
	}).mouseover(function() {//mouse over LI and look for A element for transition
	var $this = $(this).find('a')
	$this.hover(function() {
	//	$('#debugging_status').html($('.'+$(this).attr('class')));
		$('.'+$(this).attr('class')).addClass('hover');
	});
		$this.animate({
			paddingLeft: padLeft,
			paddingRight: padRight
		}, {
			queue: false,
			duration: 500
		}).animate({
	//		backgroundColor: colorOver
		}, {
			queue: false,
			duration: 500
		});
	}).mouseout(function() {
		$('#scrollWrap a').removeClass('hover');
		$(this).find('a').animate({//mouse out LI and look for A element and discard the mouse over transition
			paddingLeft: defpadLeft,
			paddingRight: defpadRight
		}, {
			queue: false,
			duration: 500
		}).animate({
	//		backgroundColor: colorOut
		}, {
			queue: false,
			duration: 500
		});
//		$('.scrollDot').remove();
	});//Animate the LI on mouse over, mouse out
	$('#scroller').mousemove(function(e) {
		var s_top = parseInt($('#scrollWrap').offset().top+2),
		s_bottom = parseInt($('#scrollWrap').height() + s_top),
	//	mheight= parseInt($('#scroller li').height() * $('#scroller li').length),top_value= Math.round(((s_top - e.pageY) / 100) * mheight / 2);
		mheight= parseInt(1550),top_value= Math.round(((s_top - e.pageY) / 100) * mheight / 2);
		//Animate the #menu by chaging the top value
		$('#scroller').animate({
			top: top_value
		}, {
			queue: false,
			duration: 4000
		});
	})
	
	
	
	});// bodyguard function
}) (jQuery);
function resizer(v){
	$('html, body, div, dl, dt, dd, ul, ol, li,  h3, h4, h5, h6, pre, form, fieldset, input, select, p, blockquote, th, td').css('font-size',v);//h1, h2,
};
function closeOverlay(id){
	$(id).remove();
}
function sitemapstyler(elementId){
	var sitemap = document.getElementById(elementId);
	if(sitemap){
		this.listItem = function(li){
			if(li.getElementsByTagName("ul").length > 0){
				var ul = li.getElementsByTagName("ul")[0];
				ul.style.display = "none";
				var span = document.createElement("span");
				span.className = "collapsed";
				span.onclick = function(){
					ul.style.display = (ul.style.display == "none") ? "block" : "none";
					this.className = (ul.style.display == "none") ? "collapsed" : "expanded";
				};
				li.appendChild(span);
			};
		};
		var items = sitemap.getElementsByTagName("li");
		for(var i=0;i<items.length;i++){
			listItem(items[i]);
		};
	};	
};

