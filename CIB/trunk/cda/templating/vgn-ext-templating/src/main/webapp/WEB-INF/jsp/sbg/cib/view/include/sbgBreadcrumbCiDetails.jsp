<script type="text/javascript">
	$(function($) {
		var crumbListUL = $('ul#crumbList');
		var vgnCurrentChannel = $('li#vgnCurrentChannel');
		var vgnCurrentChannelCiDetails = $('li#vgnCurrentChannelCiDetails');
		if(crumbListUL && vgnCurrentChannel && vgnCurrentChannelCiDetails) {
			vgnCurrentChannel.hide();
			vgnCurrentChannelCiDetails.show();
			crumbListUL.append("<li>${content.system.name}</li>");
		}
	});
</script>
