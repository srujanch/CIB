<%@ include file="/WEB-INF/jsp/include.jsp" %>
<portlet:defineObjects/>

<%
	String returnURL = renderRequest.getPreferences().getValue("returnURL","DefaultURL");
	pageContext.setAttribute("returnURL",returnURL);
%>

<portlet:actionURL var="formAction">
	<portlet:param name="action" value="config"/>
</portlet:actionURL>
<h3><spring:message code="message.contact.config.currentValue"/> :: <c:out value="${returnURL}"/></h3> <br>
<form:form commandName="contactmeConfig" method="post" action="${formAction}">
	<c:set target="${contactmeConfig}" property="returnURL" value="${returnURL}"/>
	<table cellpadding="4">
		<tr>
			<td><h2><spring:message code="message.contact.config.returnURL"/></h2></td>
			<td><form:input path="returnURL" size="100" maxlength="200"/></td>
			<td><form:errors path="returnURL" cssStyle="color:red" /></td>
		</tr>		
		<tr>
			<td><input type="submit" name="finish" value="Save"/></td>
			<td><input type="reset" name="cancel" value="Cancel"/></td>
		</tr>
	</table>
</form:form>