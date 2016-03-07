<%@ page contentType="text/html; charset=UTF-8" pageEncoding="UTF-8" language="java"%>
<%@include file="/WEB-INF/jsp/sbg/common/include/sbgHeader.jsp"%>

<%-- initialize component & results --%>
<templating:initComponent/>
<templating:initRequestContext var="rc"/>
<c:set var="currentChannel" value="${rc.requestedChannelBean}"/>
<c:set var="parentChannel" value="${currentChannel.parentChannel}"/>
<c:set var="currentCiName" value="${rc.primaryRequestedObjectBean.system.name}"/>
<templating:contentLink var="parentChannelUrl" oid="${parentChannel.system.id}"/>

<c:set var="imagePath" value="${defaultImagePath}"/>
<c:if test="${not empty component.thumbnail}">
	<c:set var="bannerPath" value="${component.thumbnail.placementPath}"/>
	<c:if test="${not empty bannerPath}">
		<c:set var="imagePath" value="${SFPathPrefix}${bannerPath}"/>
	</c:if>
</c:if>

<c:if test="${not empty component.results}">
	<div id="countryOffices" class="search portlet">
		<h1>${component.title}</h1>
		<p><a href="${parentChannelUrl}" onclick="return trackBannerClick('worldmap')"><img src="${imagePath}" alt="World Map" /></a></p>
		<p><select name="country_menu_list" onchange="return gotoCoutryDetails(this);" />
				<option value="ignore" selected="">Select a country</option>
				 <c:forEach items="${component.results}" var="content">
					<templating:contentLink var="linkUrl" oid="${content.system.id}"/>
					<option value="${linkUrl}">${content.title}</option>
				</c:forEach>
			</select></p>
	</div>
</c:if>

<script type="text/javascript">
	function gotoCoutryDetails(thisEle) {
		var selectedCountryURL = $("option:selected", thisEle).val();
		if(selectedCountryURL !='ignore'){
			var selectedCountry = $("option:selected", thisEle).text();
			if(selectedCountry == '${currentCiName}') {
				alert('You are already on '+selectedCountry+" Country Details Page.");
			} else {
				var confirmation=confirm("Are you sure you want to go "+selectedCountry+" Country Details Page?");
				if (confirmation==true)
				{
					window.location = selectedCountryURL;
				}
			}
		} else {
			alert('Please select a country to go to its details page');
		}
	}
</script>