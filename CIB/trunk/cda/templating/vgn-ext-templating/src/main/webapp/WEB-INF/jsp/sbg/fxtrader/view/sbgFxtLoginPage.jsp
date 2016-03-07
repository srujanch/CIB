<script type ="text/javascript">
	//Enable or disable trading platforms by selecting true or false
	var windows = true,
		java = true,
		html = false,
		clientBrand = "SBSA",	//Input your client brand here
		myCell,
		index,
		runtimeVersion = "2.0.0",
		exp = new Date(),
		expDays = 365; // number of days the cookie should last
	exp.setTime(exp.getTime() + (expDays * 24 * 60 * 60 * 1000));
	
	function checkNoPlatforms(){
		if (!(windows || java || html)){
			alert('At least one Trading Platform must be activated to run this page');
			self.close();
		}
	}
	function placeOptions(w, j, h){
		w = windows;j = java;h = html;
		if (h){$('#tradingPlatform').append('<input type="radio" name="platform" value="2" /><label for="liverb" class="radioLabel">Web version</label>');}
		if (j){$('#tradingPlatform').append('<input type="radio" name="platform" value="1" /><label for="demorb" class="radioLabel">Silverlight version</label>');}
		if (w){$('#tradingPlatform').append('<input type="radio" name="platform" value="0" /><label for="liverb">Windows version</label>');}
	}
	function GetVersion(v){
		var a = v.match(/([0-9]+)\.([0-9]+)\.([0-9]+)/i);
		return a.slice(1);
	}
	function CompareVersions(v1, v2){
		var i,n1,n2;
		for (i = 0; i < v1.length; ++i){
			n1 = new Number(v1[i]);n2 = new Number(v2[i]);
			if (n1 < n2){return -1;}
			if (n1 > n2){return 1;}
		}
		return 0;
	}
	function HasRuntimeVersion(v){
		var va = GetVersion(v),i,a = navigator.userAgent.match(/\.NET CLR [0-9.]+/g);
		if (a !== null){
			for (i = 0; i < a.length; ++i){
				if (CompareVersions(va, GetVersion(a[i])) <= 0){return true;}
			}
			return false;
		}
	}
	function SetCookie(name, value){
		var argv = SetCookie.arguments,
		argc = SetCookie.arguments.length,
		expires = (argc > 2) ? argv[2] : null,
		path = (argc > 3) ? argv[3] : null,
		domain = (argc > 4) ? argv[4] : null,
		secure = (argc > 5) ? argv[5] : false;
		document.cookie = name + "=" + escape(value) + ("; expires=" + exp.toGMTString()) +
			((path === null) ? "" : ("; path=" + path)) +
			((domain === null) ? "" : ("; domain=" + domain)) +
			((secure === true) ? "; secure" : "");
	}
	function DeleteCookie(name){
		var exp = new Date(),
		cval = GetCookie2(name);
		exp.setTime(exp.getTime() - 1);
		document.cookie = name + "=" + cval + "; expires=" + exp.toGMTString();
	}
	function GetCookie(name){
		if (index === 2){
			//if (document.getElementById("remme") === null){
			//		radioClick(index);
			//}
			var arg = name + "=",
				alen = arg.length,
				clen = document.cookie.length,
				i = 0;
			while (i < clen){
				var j = i + alen;
				if (document.cookie.substring(i, j) === arg){
					//return getCookieVal(j);
					//document.Form1.txtLogin2.value = getCookieVal(j);
					$('#txtLogin2').val(getCookieVal(j));
					//if (GetCookie2(name) !== null){
					//		document.getElementById("remme").checked = true;
					//}else{
					//		document.getElementById("remme").checked = false;
					//}
					i = document.cookie.indexOf(" ", i) + 1;
				}
				if (i === 0){ break;}
			}
			return null;
		}
	}
	function amt(){
		var count = GetCookie('count');
		if (count === null){
			SetCookie('count', '1');
			return 1;
		} else {
			var newcount = parseInt(count) + 1;
			DeleteCookie('count');
			SetCookie('count', newcount, exp);
			return count;
		}
	}
	function getCookieVal(offset){
		var endstr = document.cookie.indexOf(";", offset);
		if (endstr === -1){endstr = document.cookie.length;}
		return unescape(document.cookie.substring(offset, endstr));
	}
	function GetCookie2 (name){
		var arg = name + "=",alen = arg.length,clen = document.cookie.length,i = 0;
		while (i < clen){
			var j = i + alen;
			if (document.cookie.substring(i, j) === arg){
				return getCookieVal(j);
				i = document.cookie.indexOf(" ", i) + 1;
			}
			if (i === 0){break;}
		}
		return null;
	}
	function ClearOptions(){
		$('input[name="platform"]').prop('checked',false);
		//document.Form1.platform[0].checked = false;
		//document.Form1.platform[1].checked = false;
		//document.Form1.type[0].checked = false;
		//document.Form1.type[1].checked = false;
	}
	
	function CopyValues(){
		if (index !== 0 && index !== 1 && index !== 2){
			alert("Please select a trading platform");
			document.Form1.platform.focus;
			return false;
		}
		if ((document.Form1.type[0].checked === false) && (document.Form1.type[1].checked === false) && (index !== 0)){
			alert("Please select an account type");
			document.Form1.type.focus;
			return false;
		}
		if ((index === 2) && (document.Form1.txtLogin2 === null)){
			alert('1');
			radioClick('2');
		}
		if ((index === 2) && (document.Form1.type[1].checked)){
			if (document.Form1.txtLogin2.value.length === 0){
				alert("Please enter your User ID");
				document.Form1.txtLogin2.focus();
				return false;
			} else if (document.Form1.txtPass2.value.length === 0){
				alert("Please enter your password");
				document.Form1.txtPass2.focus();
				return false;
			} else {
				//if (document.getElementById("remme").checked){
				//	SetCookie('uiddemo', document.Form1.txtLogin2.value);
				//} else {
				//	DeleteCookie("uiddemo");
				//}
				document.Form1.txtPass.value = document.Form1.txtPass2.value;
				document.Form1.txtLogin.value = document.Form1.txtLogin2.value;
				document.Form1.txtPass2.value = "";               
				demourl = 'http://demo.efxnow.com/webclient/htmlclient/Login.aspx?brand='+clientBrand+'&txtlang=EN&txtLogin=' + document.Form1.txtLogin.value + '&txtPass=' + document.Form1.txtPass.value;          
				window.open(demourl, 'newWindow2', 'height=640,width=1014,toolbars=yes,status=yes,scrollbars=no,resizable=no,top=50,left=50');           
				return false;
			}
		} else if ((index === 2) && (document.Form1.type[0].checked)){
			if (document.Form1.txtLogin2.value.length === 0){
				alert("Please enter your User ID");
				document.Form1.txtLogin2.focus();
				return false;
			} else if (document.Form1.txtPass2.value.length === 0){
				alert("Please enter your password");
				document.Form1.txtPass2.focus();
				return false;
			} else {
				//if (document.getElementById("remme").checked){
				//	SetCookie('uidlive', document.Form1.txtLogin2.value);
				//} else {
				//	DeleteCookie("uidlive");
				//}
				document.Form1.action = 'https://ssl.efxnow.com/webclient/htmlclient/Login.aspx'
				document.Form1.method = 'POST'
				document.Form1.target = '_blank';
				document.Form1.txtPass.value = document.Form1.txtPass2.value;
				document.Form1.txtLogin.value = document.Form1.txtLogin2.value;
				document.Form1.txtPass2.value = "";
			}
		} else if ((index === 1) && (document.Form1.type[1].checked)){
			//Modify this URL with your demo java platform
			document.Form1.action = 'http://demo.efxnow.com/standardfxtraderweb/index.aspx'
			document.Form1.method = 'GET'
			document.Form1.target = '_blank'
		} else if ((index === 1) && (document.Form1.type[0].checked)){
			//Modify this line with the URL for your live java platform
			document.Form1.action = 'https://ssl.efxnow.com/standardfxtraderweb/index.aspx'      
			document.Form1.method = 'GET'
			document.Form1.target = '_blank'
		} else if (index === 0){
			document.Form1.method = 'GET'
			if (HasRuntimeVersion(runtimeVersion)){
				document.Form1.action = 'http://download.efxnow.com/StandardBank/StandardfxTrader.application'
				document.Form1.target = '_top'
			} else {
				//Modify this line with the URL for your windows download page      	  
				document.Form1.action = 'http://www.efxnow.com/sbsa/windows.html'
				document.Form1.target = '_top'
			}
		}
	}

	function getCheckedValue(radioObj){
		var radioLength = radioObj.length;
		if(!radioObj){
			return "";
		}
		if(radioLength === undefined){
			if(radioObj.checked){
				return radioObj.value;
			}else{
				return "";
			}
		}
		for(var i = 0; i < radioLength; i++){
			if(radioObj[i].checked){
				return radioObj[i].value;
			}
		}
		return "";
	}
	function CheckOptions(){
		if ($('input:checked[name="platform"]').attr('value') !== ''){
			radioClick(getCheckedValue(document.Form1.platform));
		}
		if ($('input:checked[name="platform"]').attr('value') !== ''){
			if ($('input:checked[name="type"]').attr('value') === 1){
				GetCookie("uidlive");
			} else {
				GetCookie("uiddemo");
			}
		}
	}
( function($){// bodyguard function
	$(document).ready(function(){
		placeOptions(windows, java, html);
		checkNoPlatforms();
		$('#tradingPlatform input').on('change',function(e){
			var v = $(this).val();
			if (v === 2){
				$('#loginBox').html('<p><label for="txtLogin2" class="formLabelSize">User ID:</label><input type="text" id="txtLogin2" name="txtLogin2" class="required" /><input type="hidden" name="txtLogin" value=""><input type="hidden" name="brand" value="' + clientBrand + '"></p><p><label for="txtPass2" class="formLabelSize">Password:</label><input id="txtPass2" class="required" type="password" name="txtPass2" /><input type="hidden" name="txtPass" value=""/><input type="hidden" name="txtlang" value="EN" /></p>');
			}else{
				$('#loginBox').html('<p>You will be prompted for User ID and Password.</p>');
				if (v === 0){
					//document.Form1.type[0].checked = false;
					//document.Form1.type[1].checked = false;
				}
			}
		});
		$('#accType input').on('change',function(e){
			var typeVal = $(this).val();
			if(typeVal === 1){
				GetCookie("uidlive");
			}else{
				GetCookie("uiddemo");
			}
		});
	});// bodyguard function
}) (jQuery);
	var _gaq = _gaq || [];
	_gaq.push(['_setAccount', 'UA-4811946-52']);
	_gaq.push(['_trackPageview']);
	
	(function(){
		var ga = document.createElement('script'); ga.type = 'text/javascript'; ga.async = true;
		ga.src = ('https:' === document.location.protocol ? 'https://ssl' : 'http://www') + '.google-analytics.com/ga.js';
		var s = document.getElementsByTagName('script')[0]; s.parentNode.insertBefore(ga, s);
	})();
	// Conversion Name: FX Login
	var ebRand = Math.random()+'';
	ebRand = ebRand * 1000000;
	//<![CDATA[ 
	//	document.write('<scr'+'ipt src="HTTP://bs.serving-sys.com/BurstingPipe/ActivityServer.bs?cn=as&amp;ActivityID=109670&amp;rnd=' + ebRand + '"></scr' + 'ipt>');
	//]]>
</script>
<noscript>
	<img width="1" height="1" style="border:0" src="HTTP://bs.serving-sys.com/BurstingPipe/ActivityServer.bs?cn=as&amp;ActivityID=109670&amp;ns=1"/>
</noscript>
<form name="Form1" autocomplete="off" method="post" action="https://ssl.efxnow.com/webclient/htmlclient/Login.aspx" onsubmit="javascript:return CopyValues();" target="_blank">
	<fieldset>
	<legend>Logon to Standard FX Trader platforms</legend>
		<p>Please select and enter the required information below:</p>
		<h2>1. Choose a trading platform:</h2>
			<p><span id="tradingPlatform" class="radioAlign">
			</span></p>
		<h2>2. Choose an account type:</h2>
			<p><span id="accType" class="radioAlign">
				<input type="radio" name="type" value="1" />
				<label for="liverb" class="radioLabel">Live account</label>
				<input type="radio" name="type" value="0" />
				<label for="demorb">Practice account</label>
			</span></p>
		<h2>3. Login:</h2>
		<div id="loginBox"></div>
		<p class="textRight formActionBar">
			<input name="language" type="hidden" value="ENG" />
			<input name="brand" type="hidden" value="SBSA" />
			<input name="page" type="hidden" value="account" />
			<input type="hidden" name="CustType" value="live" />
			<input name="submit" type="submit" style="font-family:Verdana;font-size:10pt;" value="Login" alt="Login" />
		</p>
		<p class="textRight">
			<a href="https://standardbank.secure.force.com/CFXLive/ResetOTP" target="_blank">Forgot your password?</a>
		</p>
	</fieldset>
</form>
