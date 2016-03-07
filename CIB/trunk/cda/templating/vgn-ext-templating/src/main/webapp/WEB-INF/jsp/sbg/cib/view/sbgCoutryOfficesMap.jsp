<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>
<div id="mapContainer" class="mapContainer"> 
	<img src="/vgn-ext-templating/sbg/cib/sharedImages/siteElements/backgrounds/blank.gif" width="760" height="378" class="worldMap" border="0" usemap="#Map" />
	<map name="Map" id="Map">
		<area shape="poly" title="Africa" alt="africa" href="/cib/africaRegion?vgnextnoice=1" coords="299,178,299,205,315,220,360,219,359,240,364,241,364,275,370,279,369,296,376,300,375,305,379,310,379,316,390,316,416,290,415,274,419,270,426,271,426,240,451,215,450,208,432,207,424,198,420,189,415,184,415,178,410,177,410,163,395,164,396,158,385,159,382,163,372,163,370,158,360,158,361,142,355,142,353,146,342,147,334,148,325,147,312,161" />
		<area shape="poly" title="Europe/Asia Pacific" alt="europeAsiaPacific" href="/cib/euAsiaRegion?vgnextnoice=1" coords="442,128,430,128,421,119,416,123,410,123,404,117,401,128,381,128,380,123,346,123,331,142,321,143,320,133,315,132,315,127,335,127,335,114,330,103,315,103,315,91,325,92,325,82,331,82,331,90,335,90,336,104,330,104,334,109,340,107,351,96,357,96,362,91,385,92,385,81,390,81,390,75,385,72,385,66,389,66,390,61,395,56,394,51,386,57,380,64,376,67,377,78,372,79,371,87,369,79,362,77,357,77,356,88,354,77,350,77,349,66,359,64,366,58,365,46,371,41,382,41,385,36,405,36,415,42,420,46,425,47,427,52,438,52,441,45,447,42,490,41,489,32,494,31,494,27,500,27,501,31,519,30,535,16,549,16,549,12,591,11,591,23,594,26,625,26,630,31,654,31,654,18,644,18,652,11,658,16,660,21,661,30,675,31,678,36,695,36,696,40,737,41,741,45,745,48,742,58,739,68,720,77,716,71,708,71,707,79,701,92,690,103,689,86,695,82,700,75,699,67,690,71,685,78,655,77,640,92,643,95,650,97,651,113,645,114,645,123,635,128,624,131,626,149,619,149,618,133,613,134,610,142,607,145,605,154,610,157,610,163,605,168,598,173,595,182,587,178,575,181,570,187,576,192,581,198,581,202,575,209,570,208,564,202,561,204,557,198,552,195,549,188,545,182,537,179,531,181,524,187,518,195,516,203,518,209,521,212,520,219,510,205,505,197,505,189,505,183,496,183,496,174,476,173,475,168,468,168,462,171,451,163,446,157,440,158,441,162,448,170,454,176,463,177,471,177,471,183,460,190,451,195,444,198,436,198,426,181,423,173,419,167,415,161,420,154,421,145,416,144,402,142,397,148,401,138,402,132,393,132,389,132,386,143,385,131,406,131,418,132,429,132" />
		<area shape="poly" title="Europe/Asia Pacific" alt="europeAsiaPacific" href="/cib/euAsiaRegion?vgnextnoice=1" coords="658,102,660,128,652,127,647,138,649,143,644,149,633,148,630,152,628,160,633,154,640,153,640,148,648,148,651,143,655,142,658,135,660,126,661,105" />
		<area shape="poly" title="Europe/Asia Pacific" alt="europeAsiaPacific" href="/cib/euAsiaRegion?vgnextnoice=1" coords="564,218,565,230,569,233,569,244,577,248,589,247,594,253,620,253,620,255,599,256,590,248,582,249,566,241,553,224,559,215" />
		<area shape="poly"  title="Americas" alt="americas" href="/cib/americasRegion?vgnextnoice=1" coords="164,375,159,370,158,361,154,360,154,344,159,344,159,313,163,314,164,274,160,269,154,269,154,260,149,259,149,254,138,244,143,245,144,228,149,224,148,215,134,215,130,209,125,205,132,200,115,200,115,194,99,194,98,189,89,189,90,184,84,183,84,175,78,174,78,169,73,169,71,174,68,168,65,163,59,157,60,151,49,150,48,140,44,140,44,108,35,108,35,103,24,103,25,84,14,84,15,42,30,43,35,38,39,34,40,27,44,27,44,22,59,22,62,27,83,27,84,22,84,33,90,37,95,37,98,42,105,42,108,33,99,33,95,27,94,22,99,19,99,13,80,14,75,18,64,18,65,14,44,13,44,11,103,11,144,12,143,20,146,27,154,32,160,33,161,37,169,37,169,42,174,48,179,47,179,58,174,69,169,68,164,61,155,58,158,48,149,48,148,38,142,40,137,45,131,40,126,35,124,27,130,22,129,17,122,17,112,23,110,29,115,39,119,43,129,47,134,46,144,68,113,71,113,84,138,94,143,105,145,95,154,93,148,80,148,67,164,67,164,77,169,83,176,81,178,77,183,82,185,89,188,93,195,93,194,105,205,115,205,118,200,114,194,115,190,115,185,120,181,125,169,123,164,130,158,134,150,143,149,147,145,154,138,156,133,165,113,164,105,172,105,181,107,187,113,188,119,182,124,183,125,198,134,198,134,207,139,213,149,214,155,209,171,209,183,209,186,212,193,216,202,219,206,221,209,223,212,233,219,234,221,239,233,238,235,244,245,243,244,255,243,259,236,260,235,280,227,289,218,290,214,299,207,305,198,315,193,325,186,325,178,327,179,334,176,337,174,346,168,352,167,361,168,368,172,372,177,371,182,371" />
	</map>

	<c:if test="${not empty currentChannel.subChannels}">
		<div class="countryList">
			<c:forEach items="${currentChannel.subChannels}" var="subChannel" varStatus="status">
				<templating:contentLink var="subChannelUrl" oid="${subChannel.system.id}"/>
				<c:choose>
					<c:when test="${fn:contains(subChannelUrl,'?')}">
						<c:set var="paramSep" value="&"/>
					</c:when>
					<c:otherwise>
						<c:set var="paramSep" value="?"/>
					</c:otherwise>
				</c:choose>
				<sbg-templating:descriptorProperty var="noOfColumns" channelId="${subChannel.system.id}" attributeName="<%=DPMConstants.CHANNEL_DESC_NAV_COLUMNS%>"/>
				<c:set var="noOfColumns">${noOfColumns}</c:set>
				<c:set var="noOfColumns" value="${not empty noOfColumns?noOfColumns:'1'}"/>
				<div class="column col_${noOfColumns} ${status.last?'last':''}">
					<h1><a href="${subChannelUrl}${paramSep}vgnextnoice=1" class="countryTitle" name="${subChannel.friendlyName}">${subChannel.system.name}</a></h1>
					<c:if test="${subChannel.contentInstanceCount>0}">
						<templating:sort result="sortedItems" items="${subChannel.contentInstances}" properties="title" order="ascending" />
						<ul class="world">
							<c:forEach items="${sortedItems}" var="countryOffice">
								<templating:contentLink var="itemURL" channelId="${subChannel.system.id}" oid="${countryOffice.system.id}"/>
								<li><a href="${itemURL}" class="${countryOffice.system.name}">${countryOffice.system.name}</a></li>
							</c:forEach>
						</ul>
					</c:if>
				</div>
			</c:forEach>
			<br class="clearBoth" />
		</div>
	</c:if>
</div>

<script type="text/javascript">
	$(document).ready(function() { // bodyguard function
		$('ul.world a').wrapInner('<span class="label">');
		$('ul.world a').append('<span class="dot">&nbsp;<\/span>');
		var $selectedImage=$('.worldMap, .africa, .americas, .eurAsia');
		$selectedImage.maphilight({
			fill: true,
			fillColor: '000000',
			fillOpacity: 0.1,
			stroke: false,
			strokeColor: 'ff0000',
			strokeOpacity: 1,
			strokeWidth: 1,
			fade: true,
			alwaysOn: false,
			neverOn: false,
			groupBy: 'alt',
			wrapClass: true,
			shadow: false,
			shadowX: 0,
			shadowY: 0,
			shadowRadius: 6,
			shadowColor: '000000',
			shadowOpacity: 0.8,
			shadowPosition: 'outside',
			shadowFrom: false
		});
		$('h1 a.countryTitle[name="africa"]').mouseover(function(e){$('area[name="africa"]').mouseover();}).mouseout(function(e) {$('area[name="africa"]').mouseout();});
		$('h1 a.countryTitle[name="americas"]').mouseover(function(e){$('area[name="americas"]').mouseover();}).mouseout(function(e) {$('area[name="americas"]').mouseout();});
		$('h1 a.countryTitle[name="europeAsiaPacific"]').mouseover(function(e) {$('area[name="europeAsiaPacific"]').mouseover();}).mouseout(function(e) {$('area[name="europeAsiaPacific"]').mouseout();});
		$('area, a.countryTitle').click(function(e){
			e.preventDefault();
			var $selectedLink=$(this).attr('href'),
			$selectedName;
			if($(this).is('area')){
				$selectedName=$(this).attr('alt');
			}else{
				$selectedName=$(this).attr('name');
			};
			$('#mapContainer').fadeOut('medium');
			switch($selectedName){
			case 'africa':
				if($('#africaMap').length==0){
					$('#mapContainer').after('<div id="africaMap" class="mapContainer">');
					$('#africaMap').load($selectedLink).fadeIn('medium');
				}else{$('#africaMap').fadeIn('medium')}
			  break;
			case 'americas':
				if($('#americaMap').length==0){
					$('#mapContainer').after('<div id="americaMap" class="mapContainer">');
					$('#americaMap').load($selectedLink).fadeIn('medium');
				}else{$('#americaMap').fadeIn('medium')}
			  break;
			case 'europeAsiaPacific':
				if($('#euroMap').length==0){
					$('#mapContainer').after('<div id="euroMap" class="mapContainer">');
					$('#euroMap').load($selectedLink).fadeIn('medium');
				}else{$('#euroMap').fadeIn('medium')}
			  break;
			default:
			 $('#mapContainer').fadeIn('medium');
			}
		});
		$('.mapContainer div.return a').live('click',function(e){
			e.preventDefault();
			$('.mapContainer').fadeOut('medium');
			$('#mapContainer').fadeIn('medium');
		});
		
		
	});// bodyguard function
</script>