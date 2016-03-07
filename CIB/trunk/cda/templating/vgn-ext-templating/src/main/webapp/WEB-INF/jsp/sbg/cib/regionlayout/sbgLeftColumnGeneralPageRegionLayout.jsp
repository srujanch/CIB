<%-- declarations --%>
<%@ taglib uri="/WEB-INF/vgnExtTemplating.tld" prefix="templating" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="fn" uri="http://java.sun.com/jsp/jstl/functions" %>

<templating:initRequestContext var="rc"/>
<c:set var="channelPage" value="${rc.requestedChannelBean}"/>

<c:if test="${not empty rc.componentFragments}">
	<%@include file="/WEB-INF/jsp/sbg/cib/view/include/getChannelFriendlyName.jsp"%>
	<div id="${channelFriendlyName}ColLeft" class="colLeft col_1">
		<c:forEach items="${rc.componentFragments}" var="component" varStatus="status">			
			<c:out value="${component.data}" escapeXml="false"/>				
		</c:forEach>
	</div>
</c:if>