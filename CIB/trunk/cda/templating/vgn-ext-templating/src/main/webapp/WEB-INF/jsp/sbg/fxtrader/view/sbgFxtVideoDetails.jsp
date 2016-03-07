<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:contentItem result="videoContentItem"/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>

<c:set var="videoTitle" value="${videoContentItem.TITLE}"/>
<c:set var="docKey" value="${videoContentItem.REMOTEID}"/>
<center>
	<object width="640.0" height="530.0" align="middle" classid="clsid:d27cdb6e-ae6d-11cf-96b8-444553540000" codebase="http://fpdownload.macromedia.com/pub/shockwave/cabs/flash/swflash.cab#version=8,0,0,0" id="${videoTitle}">
	<param name="allowScriptAccess" value="always"/>
	<param name="movie" value="http://sunnyside.vidavee.com/staging/vidavee/playerv3/vFlasher_debug.swf/p19=${videoTitle}&d=${docKey}&"/>
	<param name="quality" value="high"/>
	<param name="bgcolor" value="#ffffff"/>
	<param name="allowFullScreen" value="true"/>
	<param name="wmode" value="transparent"/>
	
	<embed allowscriptaccess="always" wmode="transparent" width="640.0" height="530.0" type="application/x-shockwave-flash" pluginspage="http://www.macromedia.com/go/getflashplayer" quality="high" name="${videoTitle}" src="http://sunnyside.vidavee.com/staging/vidavee/playerv3/vFlasher_debug.swf/p19=${videoTitle}&d=${docKey}&" allowFullScreen="true"></embed>
	</object>
</center>
