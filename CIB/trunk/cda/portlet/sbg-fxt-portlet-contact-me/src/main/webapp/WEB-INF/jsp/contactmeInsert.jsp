<%@ include file="/WEB-INF/jsp/include.jsp" %>
<portlet:defineObjects/>
<%
	String returnURL = renderRequest.getPreferences().getValue("returnURL","https://www.salesforce.com/servlet/servlet.WebToCase?encoding=UTF-8");
%>
<!-- 
<portlet:actionURL var="formAction">
	<portlet:param name="action" value="insert"/>
</portlet:actionURL>
-->
<div class="col_2 colRight portlet actionBar">
	<h2><spring:message code="message.contact.insert.callback"/></h2>	
	<!-- form:form commandName="contactmeInfo" method="post" action="${formAction}" -->
	<!-- form:errors path="*" cssStyle="color:red" /-->
	<form id="contactUsForm" action="https://www.salesforce.com/servlet/servlet.WebToCase?encoding=UTF-8" method="post" class="form contactUsForm">
		<input value="00D200000000Uno" name="orgid" type="hidden" />
		<input value="<%=returnURL%>" name="retURL" type="hidden" />
		<input name="type" value="Callback" type="hidden" />
		<p>
			<label for="name"><spring:message code="message.contact.insert.name"/></label>
			<!-- form:input id="name" maxlength="80" path="name" cssClass="required" / -->
			<input id="name" maxlength="80" name="name" type="text" class="required" />
		</p>
		<p>
			<label for="email"><spring:message code="message.contact.insert.email"/></label>
			<input id="email" maxlength="80" name="email" type="text" class="required email"/>
		</p>
		<p>
			<label for="phone"><spring:message code="message.contact.insert.phone"/> </label>
			<input id="phone" maxlength="10" name="phone" type="text" class="required digits" />
		</p>
		<p>
			<label for="subject"><spring:message code="message.contact.insert.subject"/></label>
			<input id="subject" maxlength="80" name="subject" type="text" class="required" />
		</p>
		<p>
			<label for="description"><spring:message code="message.contact.insert.description"/></label>
			<textarea name="description" cols="28" rows="4" class="required"></textarea>
		</p>
		<p>
			<input name="submit" id="submit" type="submit" value="Submit" />
		</p>
	</form>
</div>
<br class="clearBoth" />

<script type="text/javascript">
	$(document).ready(function() { // bodyguard function
//		$('#contactUsForm').on('submit', function (e){
//		//	console.log('submit called');
//		});
		$("#contactUsForm").validate({
				rules:{
					//Requested_Amount:{Requested_Amount:true},
					//Occupation_status:{notEmployed:true}
				},
//				submitHandler :function(form){
//					queryString = introQueryString+$('#master').serialize();
//					alert('Stored: '+queryString+' to var introQueryString for later use');
//						return true;
			//		$(form).preventDefault();
					//console.log('submit called');
//				}
			//https://www.standardbank.co.za/cgi-bin/formmail.pl
			}).checkForm();
	});// bodyguard function

</script>