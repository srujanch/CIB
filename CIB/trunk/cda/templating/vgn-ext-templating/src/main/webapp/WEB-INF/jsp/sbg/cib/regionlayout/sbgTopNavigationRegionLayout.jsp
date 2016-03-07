<%-- declarations --%>
<%@ taglib uri="/WEB-INF/vgnExtTemplating.tld" prefix="templating" %>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>

<templating:initRequestContext var="rc"/>

<div id="navBar">
	<c:forEach items="${rc.componentFragments}" var="component" varStatus="status">			
		<c:out value="${component.data}" escapeXml="false"/>				
	</c:forEach>

	<!--<div class="settings"><a href="#" class="settingIcon" rel="tooltip" rev="150" title="Settings: Adjust the font size lock or unlock the header">&nbsp;</a></div>-->
	<br class="clearBoth" />
</div>