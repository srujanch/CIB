var noCloseWin, confMsg, dateFormat, alpha, alphaName, cellNo, phoneNo, locIntTelNo, SAIDRegEx, creditCard;
function callIntroWin(w){
	var id, maskHeight, maskWidth, winH, winW;
		id = $('#'+w);
		maskHeight = $(document).height();
		maskWidth = $(window).width();
		$('#maskPerm').css({'width':maskWidth,'height':maskHeight});
		$('#maskPerm').fadeIn(1000);	
		$('#maskPerm').fadeTo("slow",0.8);	
		winH = $(window).height();
		winW = $(window).width();
		$(id).css('top',  winH/2-$(id).height()/2);
		$(id).css('left', winW/2-$(id).width()/2);
		$(id).fadeIn(2000); 
		noCloseWin = true;
}
function getAge(dateString, ageFormat) {
//	datestring = 01/14/1977
var now, today, yearNow, monthNow, dateNow, dob, yearDob, monthDob, dateDob, age,ageString, yearString, monthString, dayString, monthAge, dateAge;
	now = new Date();
	today = new Date(now.getYear(),now.getMonth(),now.getDate());
	yearNow = now.getYear();
	monthNow = now.getMonth();
	dateNow = now.getDate(); 
	dob = new Date(dateString.substring(6,10),dateString.substring(3,5)-1,dateString.substring(0,2));
	yearDob = dob.getYear();
	monthDob = dob.getMonth();
	dateDob = dob.getDate();
	age = {};
	ageString = "";
	yearString = "";
	monthString = "";
	dayString = "";
	yearAge = yearNow - yearDob;
	if (monthNow >= monthDob){
		monthAge = monthNow - monthDob;
	}else{
		yearAge--;
		monthAge = 12 + monthNow -monthDob;
	}
	if (dateNow >= dateDob){
		dateAge = dateNow - dateDob;
	}else{
		monthAge--;
		dateAge = 31 + dateNow - dateDob;
		if (monthAge < 0) {
			monthAge = 11;
			yearAge--;
		}
	}
	age = {
		years: yearAge,
		months: monthAge,
		days: dateAge
	}; 
	if ( age.years > 1 ){ yearString = " years";}else{ yearString = " year";}
	if ( age.months> 1 ){ monthString = " months";}else{ monthString = " month";}
	if ( age.days > 1 ){ dayString = " days";}else{ dayString = " day";}
 
	if(ageFormat==='arrayed'){
		return {
			ageYears:age.years,
			ageMonths:age.months,
			ageDays:age.days
		};
	}else{
		if ( (age.years > 0) && (age.months > 0) && (age.days > 0) ){
			ageString = age.years + yearString + ", " + age.months + monthString + ", and " + age.days + dayString + " old.";
		}else if ( (age.years === 0) && (age.months === 0) && (age.days > 0) ){
			ageString = "Only " + age.days + dayString + " old!";
		}else if ( (age.years > 0) && (age.months === 0) && (age.days === 0) ){
			ageString = age.years + yearString + " old. Happy Birthday!!";
		}else if ( (age.years > 0) && (age.months > 0) && (age.days === 0) ){
			ageString = age.years + yearString + " and " + age.months + monthString + " old.";
		}else if ( (age.years === 0) && (age.months > 0) && (age.days > 0) ){
			ageString = age.months + monthString + " and " + age.days + dayString + " old.";
		}else if ( (age.years > 0) && (age.months === 0) && (age.days > 0) ){
			ageString = age.years + yearString + " and " + age.days + dayString + " old.";
		}else if ( (age.years === 0) && (age.months > 0) && (age.days === 0) ){
			ageString = age.months + monthString + " old.";
		}else{ ageString = "Oops! Could not calculate age!";}
		return ageString;
	}
}
// This takes a date string that MIGHT have a two digit year
// as the last two digits. If it does, this function replaces
// the two digit year with what it *assumes* is the proper
// four digit year.
function CleanDate(strDate){
	return(// Return the cleaned date.
		strDate.replace(
	// This regular expression will search for a slash
	// followed by EXACTLY two digits at the end of
	// this date string. The two digits are being
	// grouped together for future referencing.
			new RegExp( "/(\\d{2})$", "" ),
	// We are going to pass the match made by the
	// regular expression off to this function literal.
	// Our arguments are as follows:
	// $0 : The entire match found.
	// $1 : The first group within the match.
			function( $0, $1 ){
	// Check to see if our first group begins with
	// a zero or a one. If so, replace with 20 else
	// replace with 19.
				if ($1.match( new RegExp( "^[01]{1}", "" ) )){
					return( "/20" + $1 );	// Replace with 20.
				} else {
					return( "/19" + $1 );// Replace with 19.
				}
			}
		)
	);
}
function cancelApp(){
	var application = confirm("Are you sure that you wish to cancel this application? \n \n Click \"OK\", and you will return to the list of other products that you can apply for. To continue with the application click \"Cancel\".");
	if(application === false){
		window.close();
	}else{
		parent.location='http://www.standardbank.co.za/portal/site/standardbank/menuitem.de435aa54d374eb6fcb695665c9006a0/?vgnextoid=a29b08f82045b210VgnVCM100000c509600aRCRD';
	}
}


//parse URL to get values: var i = getUrlVars()["i"];
function getUrlVars() {
	var vars = [], hash, fieldValue, fv, hashes, i;
	hashes = window.location.href.slice(window.location.href.indexOf('?') + 1).split('&');
	for (i = 0; i < hashes.length; i++) {
		hash = hashes[i].split('&');
		vars.push(hash[0]);
		fieldValue = hash[0];
		fV=fieldValue.split('=');
	//	alert(fV[0]+ ' ' + fV[1]);
		$('#master').append($('<input/>').attr('type', 'hidden').attr('name', fV[0]).val(fV[1]));
		vars[hash[0]] = hash[1];
	}
	introQueryString = vars;
}

function ValidateID_number(ID_number) {
	var tempDate, tempTotal, checkSum, multiplier, i;
	
	//1. numeric
	ID_number = document.getElementById("ID_number").value;
	if (isNaN(ID_number)) {//alert("!numeric");
		document.getElementById("ID_number").focus();
		return false; 
	}
	//13 digits
	if (ID_number.length !== 13){
		//alert("!13 digits");
		document.getElementById("ID_number").focus();
		return false;
	}
	//2. first 6 numbers is a valid date
	tempDate = new Date(ID_number.substring(0, 2), ID_number.substring(2, 4) - 1, ID_number.substring(4, 6));
	if (!((tempDate.getYear() === ID_number.substring(0, 2)) && (tempDate.getMonth() === ID_number.substring(2, 4) - 1) && (tempDate.getDate() === ID_number.substring(4, 6)))) { 
		//alert("!first 6 numbers are not a valid date");
		document.getElementById("ID_number").focus();
		return false; 
	}
	//3. luhn formula
	tempTotal = 0;
	checkSum = 0;
	multiplier = 1;
	for (i = 0; i < 13; i++) {
		tempTotal = parseInt(ID_number.charAt(i)) * multiplier;
		if (tempTotal > 9) { 
			tempTotal = parseInt(tempTotal.toString().charAt(0)) + parseInt(tempTotal.toString().charAt(1)); 
		}
		checkSum = checkSum + tempTotal;
		multiplier = (multiplier % 2 === 0) ? 1 : 2;
	}
	if ((checkSum % 10) === 0) {
		//alert("ALL good!!"); 
		return true;
	} else {
	//alert("it's wrong, what now????");
		document.getElementById("ID_number").focus();
			return false;
	}
//	return true;
}
confMsg = ' <br />Please contact your <a href=\"http://locator.standardbank.co.za\">nearest branch</a> to complete your application.';
dateFormat = /^(((0[1-9]|[12][0-9]|3[01])([/])(0[13578]|10|12)([/])([1-2][0,9][0-9][0-9]))|(([0][1-9]|[12][0-9]|30)([/])(0[469]|11)([/])([1-2][0,9][0-9][0-9]))|((0[1-9]|1[0-9]|2[0-8])([/])(02)([/])([1-2][0,9][0-9][0-9]))|((29)(\.|-|\/)(02)([/])([02468][048]00))|((29)([/])(02)([/])([13579][26]00))|((29)([/])(02)([/])([0-9][0-9][0][48]))|((29)([/])(02)([/])([0-9][0-9][2468][048]))|((29)([/])(02)([/])([0-9][0-9][13579][26])))/;
alpha = /^[a-zA-Z]+$/;
alphaName = /^[a-zA-Z ]+$/;
cellNo = /^(^0[87][23467]((\d{7})|( |-)((\d{3}))( |-)(\d{4})|( |-)(\d{7})))/;
phoneNo = /^[0](\d{9})|([0](\d{2})( |-)((\d{3}))( |-)(\d{4}))|[0](\d{2})( |-)(\d{7})/;
locIntTelNo = /^[0](\d{9})|([0](\d{2})( |-|)((\d{3}))( |-|)(\d{4}))|[0](\d{2})( |-|)(\d{7})|(\+|00|09)(\d{2}|\d{3})( |-|)(\d{2})( |-|)((\d{3}))( |-|)(\d{4})/;
SAIDRegEx = /^(((\d{2}((0[13578]|1[02])(0[1-9]|[12]\d|3[01])|(0[13456789]|1[012])(0[1-9]|[12]\d|30)|02(0[1-9]|1\d|2[0-8])))|([02468][048]|[13579][26])0229))(( |-)(\d{4})( |-)(\d{3})|(\d{7}))/;
//creditCard = /^((67\d{2})|(4\d{3})|(5[1-5]\d{2})|(6011))-?\s?\d{4}-?\s?\d{4}-?\s?\d{4}|3[4,7]\d{13}$/;
creditCard = /^((?:4\d{3})|(?:5[1-5]\d{2})|(?:6011)|(?:3[68]\d{2})|(?:30[012345]\d))[ -]?(\d{4})[ -]?(\d{4})[ -]?(\d{4}|3[4,7]\d{13})$/;

// /^(((\d{2}((0[13578]|1[02])(0[1-9]|[12]\d|3[01])|(0[13456789]|1[012])(0[1-9]|[12]\d|30)|02(0[1-9]|1\d|2[0-8])))|([02468][048]|[13579][26])0229))(( |-)(\d{4})( |-)(\d{3})|(\d{7}))/;
//	var SAIDRegEx = ValidateID_number(ID_number);
			jQuery.validator.addMethod("alpha", function(value, element, params) {
				//return this.optional(element) || value === value.match(/^[a-zA-Z]+$/);
				return this.optional(element) || alpha.test(value);
			},"Only alphabets allowed.");
			jQuery.validator.addMethod("alphaNames", function(value, element, params) {
		//		return this.optional(element) || value === value.match(/^[a-zA-Z \-]+$/);
				return this.optional(element) || alphaName.test(value);
			},"Only alphabets and spaces allowed.");
			jQuery.validator.addMethod('SAIDno', function(value, element, params){
				return SAIDRegEx.test(value);
			}, "Please enter a valid South African Id number.");
			jQuery.validator.addMethod('dateFormat', function(value, element, params){
				return dateFormat.test(value);
			}, "Please enter a valid date.");
			jQuery.validator.addMethod('cellNo', function(value, element, params){
				return this.optional(element) || cellNo.test(value);
			}, "Please enter a valid cellphone number.");
			jQuery.validator.addMethod('homeNo', function(value, element, params){
					return this.optional(element) || phoneNo.test(value);
			}, "Please enter a valid home number.");
			jQuery.validator.addMethod('workNo', function(value, element, params){
				return this.optional(element) || phoneNo.test(value);
			}, "Please enter a valid work number.");
			jQuery.validator.addMethod('creditCard', function(value, element, params){
				return this.optional(element) || creditCard.test(value);
			}, "Please enter a valid Credit Card number.");
			jQuery.validator.addMethod('months2digit', function(value, element, params){
				return this.optional(element) || value < 12 || value === 'mm';
			},"You cannot have more than 12 months in a year");
			jQuery.validator.addMethod('Existing_Customer', function(value, element, params) {
				return $("input[name='Existing_customer']:checked").val() === 'Yes';
			});
			jQuery.validator.addMethod('salaryPaid', function(value, element, params) {
				return $("input[name='Salary_paid_into_account']:checked").val() === 'Yes';
			});
			jQuery.validator.addMethod('FICA_performed', function(value, element, params){
				return $("input[name='FICA_performed']:checked").val() === 'Yes';
			});
			jQuery.validator.addMethod('Debt_review', function(value, element, params){
				return $("input[name='Debt_review']:checked").val() === 'No';
			});
			jQuery.validator.addMethod('adminOrder', function(value, element, params){
				return $("input[name='Administration_order']:checked").val() === 'No';
			});
			jQuery.validator.addMethod('Sequestration', function(value, element, params){
				return $("input[name='Sequestration']:checked").val() === 'No';
			});
			jQuery.validator.addMethod('terms1', function(value, element, params){
				return $("input[name='terms1']:checked").val() === 'Yes';
			});
			jQuery.validator.addMethod('terms2', function(value, element, params){
				return $("input[name='terms2']:checked").val() === 'Yes';
			});
			jQuery.validator.addMethod('terms3', function(value, element, params){
				return $("input[name='terms3']:checked").val() === 'Yes';
			});
			jQuery.validator.addMethod('Limit_increase', function(value, element, params){
				return $("input[name='Limit_increase']:checked").val() === 'Yes';
			});
			jQuery.validator.addMethod('Credit_record_check_consent', function(value, element, params){
				return $("input[name='Credit_record_check_consent']:checked").val() === 'Yes';
			});
			jQuery.validator.addMethod('Identity_fraud_check_consent', function(value, element, params){
				return $("input[name='Identity_fraud_check_consent']:checked").val() === 'Yes';
			});
			jQuery.validator.addMethod('EarnOverN40k', function(value, element, params){
				return $("input[name='EarnOverN40k']:checked").val() === 'Yes';
			});
			jQuery.validator.addMethod('PermanentlyEmployed', function(value, element, params){
				return $("input[name='PermanentlyEmployed']:checked").val() === 'Yes';
			});
			jQuery.validator.addMethod('ActiveAccount', function(value, element, params){
				return $("input[name='ActiveAccount']:checked").val() === 'Yes';
			});
			jQuery.validator.addMethod('SalaryAccIsCurrentAcc', function(value, element, params){
				return $("input[name='SalaryAccIsCurrentAcc']:checked").val() === 'Yes';
			});
			jQuery.validator.addMethod('SalaryUsedOvr3Months', function(value, element, params){
				return $("input[name='SalaryUsedOvr3Months']:checked").val() === 'Yes';
			});

( function($) {// bodyguard function
	$(document).ready(function() { 
			$('#spouseAppr').css('display','none');
			$('#postalAddr').css('display','none');
			$('#unemployedReason').css('display','none');
			$('#otherLoanReason').css('display','none');
			$('#existingAccNo').css('display','none');
			$('#otherTitle').css('display','none');
			$('#additionalOtherTitle').css('display','none');
			$('#additionalCardHolderInfo').css('display','none');
			$('#autoMaticPayment').css('display','none');
			$('#existingSbsaAccount').css('display','none');
			$('#unemployedReason').css('display','none');
				$('input[value="yy"]').bind('focus blur',function(e){
					if ($(this).val()=='yy'){$(this).val('');}else if ($(this).val()==''){$(this).val('yy');}
				});
				$('input[value="mm"]').bind('focus blur',function(e){
					if ($(this).val()=='mm'){$(this).val('');}else if ($(this).val()==''){$(this).val('mm');}
				});
			$('#title').bind('keyup change',function(){
				if ($('#title').val() === 'Other'){
					$('#otherTitle').show('fast');
				}else{
					$('#otherTitle').hide('fast');
					$('#TitleOther').attr('value','');
				}
			});
			$('#Marital_status').bind('keyup change',function(){// Add onclick handler to checkbox w/id checkme
				if ($('#Marital_status').val() === 'Married in community of property') {// If checked
					$('#spouseAppr').show('fast');//show the hidden div
				} else {
					$('#spouseAppr').hide('fast');//otherwise, hide it 
				}
			});
			$('#samePostal').bind('keyup change',function() {
				if ($('#samePostal').is(':checked')){
					$('#postalAddr').show('fast');
				} else {
					$('#postalAddr').hide('fast');
				}
			});
			$('#purposeOfLoan').bind('keyup change',function() {// Add onclick handler to checkbox w/id checkme
				if ($('#purposeOfLoan').val() === 'Other') {// If checked
					$('#otherLoanReason').show('fast');//show the hidden div
				} else {
					$('#otherLoanReason').hide('fast');//otherwise, hide it 
				}
			});
			$('#Email_Address').blur(function(e){
				 $('#sender').val($(this).val());
			});
			$('#identification').bind('keyup change',function(e){	//manipulate #idNumber on select
				var selectedVal = $('select#identification option:selected').val();
				switch (selectedVal){
					case 'SA ID number':
						$("#idNumber").attr('name','ID_no');
						$("#idNumber").attr('maxlength','13');
						$("#idNumber").attr('class','required digits');
						$("#idNumber").attr('value','');
						$("#Citizen").attr('value', '');
					  break;
					default:
						$("#idNumber").attr('name','pass_number');
						$("#idNumber").attr('maxlength','49');
						$("#idNumber").attr('class','required error');
						$("#idNumber").attr('value','');
						$('#Date_of_birth').attr('value', '');
						$('#gender').val("");
						$("#Citizen").attr('value', 'Resident');
				}
			});
//			$('#idNumber').blur(function(e){
//				var idNo = $('#idNumber').val();
//				var dob = idNo.substring(0,6);
//				var gen = idNo.substring(6,7);
//				var cit = idNo.substring(10,11);
//				if (gen >= 0 && gen <= 4){$('#gender').val("Female");}else if (gen >= 5 && gen <= 9){$('#gender').val("Male");}
//				//{123456}{7}{890}{1}{2}{3}
//				//{770114}{5}{140}{0}{8}{9}
//				//{YYMMDD}{G}{SSS}{C}{A}{Z}
//			});
			$('#idNumber').blur(function(e){
				var idNo, dob, gen, cit, yy, mm, dd, dobFormatted, DOB;
				idNo = $('#idNumber').val();
				dob = idNo.substring(0,6);
				gen = idNo.substring(6,7);
				cit = idNo.substring(10,11);
				yy = idNo.substring(0,2);
				mm = idNo.substring(2,4);
				dd = idNo.substring(4,6);
				dobFormatted = dd+'/'+mm+'/'+yy;
				if ($('#identification').val()=='SA ID number'){
					if (gen >= 0 && gen <= 4){$('#gender').val("Female");}else if (gen >= 5 && gen <= 9){$('#gender').val("Male");}
					if (cit==='0'){$('#Citizen').attr('value','Citizen resident in South Africa');}else{$('#Citizen').attr('value','Resident');}
					DOB = CleanDate(dobFormatted);
//					alert(dob);
					$('#Date_of_birth').attr('value', DOB);
				}
			//	$('#showAge').html(getAge($('#Date_of_birth').val(),'readable'));
				//{123456}{7}{890}{1}{2}{3}
				//{770114}{5}{140}{0}{8}{9}
				//{YYMMDD}{G}{SSS}{C}{A}{Z}
				//if (DOB.substring(6,10) < 1988){
//					switch(int.parse(idNo.ToString().substring(10, 1))) {
//							case 0: Race = "White"; break;
//							case 1: Race = "Cape Coloured"; break;
//							case 2: Race = "Malay"; break;
//							case 3: Race = "Griqua"; break;
//							case 4: Race = "Chinese"; break;
//							case 5: Race = "Indian"; break;
//							case 6: Race = "Other Asian"; break;
//							case 7: Race = "Other Coloured"; break;
//							default: Race = "Unknown"; break;
//						}
//				}
			});
			$('#Date_of_birth').blur(function(){
				$('#showAge').html(getAge($('#Date_of_birth').val(),'readable'));
			});
			$('#employType').bind('keyup change',function (e){
				var employType = $('#employType').val();
				if (employType == 'self_employed'){
					if (!$('#inc').is(':visible')){
						$('#inc').show('fast');
						$('#exp').show('fast');
					}
					if ($('#employedByFields').is(':visible')){
						$('#employedByFields').hide('fast');
					}
					if ($('#unemployedReason').is(':visible')){
						$('#unemployedReason').hide('fast');
					}
					$('#jobInfo').show('fast');
					$("label.formLabelSize[for='income']").html('Total annual turnover/income');
					$("label.formLabelSize[for='expenses']").html('Total annual expenses');
					$('#incomeFrequency').attr('value','Annually');
				} else if (employType == 'Not_employed'){
					$('#unemployedReason').show('fast');
					$('#employedByFields').hide('fast');
					$('#jobInfo').hide('fast');
					$('#inc').hide('fast');
					$('#exp').hide('fast');
					$('#incomeFrequency').attr('value','N/A - Unemployed');
				}else{
					if (!$('#inc').is(':visible')){
						$('#inc').show('fast');
						$('#exp').show('fast');
					}
					if ($('#unemployedReason').is(':visible')){
						$('#unemployedReason').hide('fast');
					}
					$('#employedByFields').show('fast');
					$('#jobInfo').show('fast');
					$("label.formLabelSize[for='income']").html('Monthly income before tax');
					$("label.formLabelSize[for='expenses']").html('Total monthly expenses ');
					$('#incomeFrequency').attr('value','Monthly');
				}
			});
			
			
jQuery.validator.setDefaults({ 
	rules:{
		First_name:{alphaNames:true},
		Surname:{alphaNames:true},
		Initials:{alphaNames:true},
		TitleOther:{
			required: function (element){
				return $('#title').val() === 'Other';
			}
		},
		Marriage_spousal_consent:{
			required: function (element){
				return $('#Marital_status').val() === 'Married in community of property';
			}
		},
		Income:{
			required: function (element){
				return $('#employType').val() !== 'Not_employed';
			}
		},
		Expenses:{
			required: function (element){
				return $('#employType').val() !== 'Not_employed';
			}
		},
		Employer:{
			required: function (element){
				return $('#employType').val() !== 'self_employed';
			}
		},
		Years_with_present_employer:{
			required: function (element){
				return $('#employType').val() !== 'self_employed';
			},
			digits: function (element){
				return $('#employType').val() !== 'self_employed';
			}
		},
		Months_with_present_employer:{
			required: function (element){
				return $('#employType').val() !== 'self_employed';
			},
			months2digit: function (element){
				return $('#employType').val() !== 'self_employed';
			},
			digits: function (element){
				return $('#employType').val() !== 'self_employed';
			}
		},
		ID_no:{maxlength:13,minlength:13,SAIDno:true},
		Date_of_birth:{dateFormat:true},
		Business_no:{maxlength:10,minlength:10,workNo:true},
		Home_no:{maxlength:10,minlength:10,homeNo:true},
		Cellphone_no:{maxlength:10,minlength:10,cellNo:true},
		Postal_address1: {required:'#samePostal:checked'},
		Postal_address2: {required:'#samePostal:checked'},
		Postal_address3: {required:'#samePostal:checked'},
		Postal_code: {required:'#samePostal:checked'},
		Months_at_present_address:{months2digit:true},
		Months_with_previous_employer:{months2digit:true}
	},
	messages:{
		Existing_account_number: {
			minlength: "Please enter your 9 digit account number",
			maxlength: "Please enter your 9 digit account number"
		},
		Existing_card_number: {
			minlength: "Please enter your 16 digit card number",
			maxlength: "Please enter your 16 digit card number"
		},
		Existing_customer:{
			required:'Please select "Yes" or "No".'
		},
		Salary_paid_into_account:{
			required:'Please select "Yes" or "No".',
			salaryPaid:'We are unable to process your application due to the following reason: Your salary is not paid into your transaction account.'+confMsg
		},
		FICA_performed:{required:'Please select "Yes" or "No".',
			FICA_performed:'Please go through to your nearest branch to provide proof of your residential address and a consultant will process your loan application.'+confMsg
		},
		Debt_review:{required:'Please select "Yes" or "No".',
			Debt_review:'You can not apply online if you are under debt review.'+confMsg

		},
		Administration_order:{
			required:'Please select "Yes" or "No".',
			adminOrder:'You can not apply online if you are under administration order.'+confMsg
		},
		Sequestration:{
			required:'Please select "Yes" or "No".',
			Sequestration:'You can not apply online if you are under sequestration.'+confMsg
		},
		terms1:{required:'We are unable to process your application due to the following reason: You should read, understand and accept all terms and conditions before you can proceed with this application. If you do not accept, please contact your <a href=\"http://locator.standardbank.co.za\">nearest branch</a> to complete your application.'},
		terms2:{required:'We are unable to process your application due to the following reason: You must agree to credit checks in order for us to process your online application. If you do not agree, please contact your <a href=\"http://locator.standardbank.co.za\">nearest branch</a> to complete your application.'},
		terms3:{required:'We are unable to process your application due to the following reason: You need to agree to fraud prevention checks. If you do not agree, please contact your <a href=\"http://locator.standardbank.co.za\">nearest branch</a> to complete your application.'},
		Review_information_correct:{required:'We are unable to process your application due to the following reason: You must confirm that the information provided is accurate and that no additional information that may affect the decision of the bank has been withheld. If you do not agree, please contact your <a href=\"http://locator.standardbank.co.za\">nearest branch</a> to complete your application.'},
		Language:{required:'Please select "English" or "Afrikaans".'},
		Garage_card:{required:'Please select "Yes" or "No".'},
		Additional_Garage_card: {required:'Please select "Yes" or "No".'},
		Bank_other_financial_institutions:{required:'Please select "Yes" or "No".'},
		Marketing_consent_clause1:{required:'Please select "Yes" or "No".'},
		Marketing_consent_clause2:{required:'Please select "Yes" or "No".'},
		Marketing_consent_clause3:{required:'Please select "Yes" or "No".'},
		Marketing_consent_clause4:{required:'Please select "Yes" or "No".'},
//		Limit_increase:{required:'Please select "Yes" or "No".'},
//		Credit_record_check_consent:{required:'Please select "Yes" or "No".'},
//		Identity_fraud_check_consent:{required:'Please select "Yes" or "No".'},
		Additional_Credit_card: {required:'Please select "Yes" or "No".'},
		Limit_increase:{
		//	required:'Please select "Yes" or "No".',
			required:'We are unable to process your application due to the following reason: Your limit should be increased on an annual basis.'+confMsg
		},
		Credit_record_check_consent:{
			required:'Please select "Yes" or "No".',
			Credit_record_check_consent:'We are unable to process your application due to the following reason: You must agree to credit checks in order for us to process your online application. If you do not agree, please contact your <a href=\"http://locator.standardbank.co.za\">nearest branch</a> to complete your application.'
		},
		Identity_fraud_check_consent:{
			required:'Please select "Yes" or "No".',
			Identity_fraud_check_consent:'We are unable to process your application due to the following reason: You need to agree to fraud prevention checks. If you do not agree, please contact your <a href=\"http://locator.standardbank.co.za\">nearest branch</a> to complete your application.'
		}
	},
//	debug: true,
//	onfocusout: false,
	onsubmit: true,
	focusCleanup: true,
	errorElement: 'label',
	errorPlacement: function(error, element) {
			
		if ( element.is(":radio")){
			element.parent().after(error);
			error.css('display', 'block');
		}else if (element.is(":checkbox")){
		//	if (element.attr('class').contains("required checkRequired error")){
	//			alert("You must accept the terms and conditions declared in the \"Declaration\" checkbox before you can submit");
	//		}else{
		element.parent().before(error);
	//			element.next('label').after(error);
	//			error.css('display', 'block');
	//		}
		}else if (element.is(".inputShort")){
			element.parent().append(error);
			//error.css('display', 'block');
		}else{
			var trigger = element.next('label').next('label');
			error.insertAfter(trigger.length > 0 ? trigger : element);
		}
	},
	invalidHandler: function(form, validator) {
		var errors = validator.numberOfInvalids();
		if (errors) {
			var message = errors === 1
			? 'You have 1 notification. It has been highlighted'
			: 'You have ' + errors + ' notifications. They have been highlighted';
//			alert(message);
		}
	}
});
//			$('#master').bind('change keyup', function() {
//				if($('#master').validate().checkForm()) {
//				  $('#submitButton').removeAttr('disabled');
//				} else {
//				  $('#submitButton').attr('disabled', 'disabled');
//				}
//			});	
	//select all the a tag with name equal to modal
	$('a.modal').click(function(e) {	
	var id, maskHeight, maskWidth, winH, winW; 
		e.preventDefault();		//Cancel the link behavior
		id = $(this).attr('href');		//Get the A tag
		maskHeight = $(document).height();//Get the screen height and width
		maskWidth = $(window).width();
		$('#mask').css({'width':maskWidth,'height':maskHeight});		//Set heigth and width to mask to fill up the whole screen
		$('#mask').fadeIn(1000);			//transition effect		
		$('#mask').fadeTo("slow",0.8);	
		winH = $(window).height();		//Get the window height and width
		winW = $(window).width();
		$(id).css('top',  winH/2-$(id).height()/2);		//Set the popup window to center
		$(id).css('left', winW/2-$(id).width()/2);
		$(id).fadeIn(2000);		//transition effect
	});
	$('a.modalAjax').click(function(e) {	
	var id, targetFile, maskHeight, maskWidth, winH, winW; 
		e.preventDefault();		//Cancel the link behavior
		id = $(this).attr('href');		//Get the A tag
		targetFile = $(this).attr('rel');
		$(id+' .contentArea').load(targetFile);
		maskHeight = $(document).height();//Get the screen height and width
		maskWidth = $(window).width();
		$('#mask').css({'width':maskWidth,'height':maskHeight});		//Set heigth and width to mask to fill up the whole screen
		$('#mask').fadeIn(1000);			//transition effect		
		$('#mask').fadeTo("slow",0.8);	
		winH = $(window).height();		//Get the window height and width
		winW = $(window).width();
		$(id).css('top',  winH/2-$(id).height()/2);		//Set the popup window to center
		$(id).css('left', winW/2-$(id).width()/2);
		$(id).fadeIn(2000);		//transition effect
	});
	$('.window .close').click(function (e) {	//if close button is clicked
		e.preventDefault();		//Cancel the link behavior
		$('#mask').fadeOut('slow');
		$('.window').fadeOut('slow');
	});
	$('#mask').click(function (e) {	//if mask is clicked
		$('#mask').fadeOut('slow');		//$(this).hide();
		$('.window').fadeOut('slow');
	});
	
//id is the ID for the DIV you want to display it as modal window
// launchWindow(id); 
//  if(e.keyCode === 13) {
//  $('#mask').hide();
//  $('.window').hide();
// }
		});// bodyguard function
	}) (jQuery);

			$('#Introduction').bind('change keyup', function(e) {
//				if ($('#Introduction').validate().checkForm()){
//					$('#continue').removeAttr('disabled');
//				} else {
//					$('#continue').attr('disabled', 'disabled');
//				}
			});
	function setval(form){
//		//overdraft.html
//		$('#terms1Yes').attr('checked','checked');
//		$('#terms2Yes').attr('checked','checked');
//		$('#terms3Yes').attr('checked','checked');
//		$('#Existing_CustomerNo').attr('checked','checked');
//		$('#FICA_performedYes').attr('checked','checked');
//		$('#Debt_reviewNo').attr('checked','checked');
//		$('#Administration_orderNo').attr('checked','checked');
//		$('#SequestrationNo').attr('checked','checked');
//		$('#mcc1N').attr('checked','checked');
//		$('#limit').val('6000');
//		$('#Gross').val('10000');
//		$('#Monthly_Expenses').val('2000');
//		$('#initials').val('Int');
		$('#firstName').val('first Name');
		$('#lastName').val('last Name');
		$('#idNumber').val('7701145140089');
		$('#Business_no').val('0111234567');
		$('#Home_no').val('0111234567');
		$('#Cellphone_no').val('0831234567');
//		$('#Email_Address').val('a@b.com');
//		//personalLoan.html
//		$('#product').val('New personal loan');
		$('#Requested_Amount').val('6000');
//		$('#purposeOfLoan').val('Funeral');
//		$('#Marital_status').val('Single');
//		$('#Dependants').val('0');
//		$('#employType').val('self_employed');
//		$('#Occupation').val('occupation');
//		$('#Present_Employers_address1').val('addr1');
//		$('#Present_Employers_address2').val('addr2');
//		$('#Present_Employers_address3').val('addr3');
		$('#income').val('10000');
		$('#expenses').val('12000');
//		$('#Overtime_and_commissionYes').attr('checked','checked');
//		$('#Present_res_address1').val('Funeral');
//		$('#Present_res_address2').val('Funeral');
//		$('#Present_res_address3').val('Funeral');
		$('#Present_res_post_code').val('1234');
		$('#Years_with_present_employer').val('1');
		$('#Months_with_present_employer').val('2');
//		$('#Residential_status').val('owned_by_spouse');
		$('#Years_at_present_address').val('1');
		$('#Months_at_present_address').val('2');
		$('#Mortgage_or_Rental_amount').val('2');
	};
	
//Postal_address1.valid
//Postal_address2.valid
//Postal_address3.valid
//Postal_address4
//input.digits
//Years_at_present_address.requiredyy
//Months_at_present_address.requiredmm
//Mortgage_or_Rental_amount.required
//mcc1Y.requiredYes
//mcc1N.errorNo
//mcc2Y.requiredYes
//mcc2N.errorNo
//mcc3Y.requiredYes
//mcc3N.errorNo
//mcc4Y.requiredYes
//mcc4N.errorNo
//Review_information_correct.requiredIhereby...heBank.
	
	