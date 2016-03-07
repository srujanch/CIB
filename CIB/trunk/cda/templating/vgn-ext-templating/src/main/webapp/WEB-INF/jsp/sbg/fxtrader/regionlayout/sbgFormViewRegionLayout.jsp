<%-- declarations --%>
<%@ taglib uri="/WEB-INF/vgnExtTemplating.tld" prefix="templating" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>
<c:set var="currentChannelFriendlyName" value="${currentChannel.friendlyName}"/>
<c:if test="${empty currentChannelFriendlyName}">
	<c:set var="currentChannelFriendlyName" value="${currentChannel.system.name}"/>
</c:if>
 <c:set var="currentChannelFriendlyName" value='${fn:replace(currentChannelFriendlyName," ","")}'/>

<div id="${currentChannelFriendlyName}" class="formPopup">
	<c:forEach items="${rc.componentFragments}" var="component" varStatus="status">			
		<c:out value="${component.data}" escapeXml="false"/>				
	</c:forEach>
</div>
