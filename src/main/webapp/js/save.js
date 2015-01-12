function submitTheForm(theNewAction) {
	var theForm = document.forms[0];
	theForm.action = theNewAction;
	theForm.submit();
}

function formValidate(msg) {
	document.getElementById("title_err").innerHTML = "";
	document.getElementById("date_err").innerHTML = "";
	document.getElementById("brief_err").innerHTML = "";
	document.getElementById("content_err").innerHTML = "";
	var limits = [ 100, 500, 2000 ];
	var submit = true;
	var title = document.getElementById("title").value;
	if (title === "") {
		document.getElementById("title_err").innerHTML = msg.emptyField;
		submit = false;
	} else if (title.length > limits[0]) {
		document.getElementById("title_err").innerHTML = msg.tooLong + " "
				+ limits[0] + ": " + title.length;
		submit = false;
	}
	var brief = document.getElementById("brief").value;
	if (brief === "") {
		document.getElementById("brief_err").innerHTML = msg.emptyField;
		submit = false;
	} else if (brief.length > limits[1]) {
		document.getElementById("brief_err").innerHTML = msg.tooLong + " "
				+ limits[1] + ": " + brief.length;
		submit = false;
	}
	var content = document.getElementById("content").value;
	if (content === "") {
		document.getElementById("content_err").innerHTML = msg.emptyField;
		submit = false;
	} else if (content.length > limits[2]) {
		document.getElementById("content_err").innerHTML = msg.tooLong + " "
				+ limits[2] + ": " + content.length;
		submit = false;
	}
	var date = document.getElementById("date").value;
	if (date === "") {
		document.getElementById("date_err").innerHTML = msg.emptyField;
		return false;
	}
	var pattern;
	if (msg.lang.indexOf("ru") === -1) {
		pattern = /^(0?[1-9]|[1][012])\/(0?[1-9]|[12]\d|3[01])\/\d{4}$/;
	} else {
		pattern = /^(0?[1-9]|[12]\d|3[01]).(0?[1-9]|[1][012]).\d{4}$/;
	}
	if (!pattern.test(date)) {
		document.getElementById("date_err").innerHTML = msg.wrongDate;
		return false;
	} else if (validateDate(date, msg.lang) === false) {
		document.getElementById("date_err").innerHTML = msg.noDate;
		return false;
	}
	return submit;
}

function validateDate(date, lang) {
	var splitDate;
	var day;
	var month;
	if (lang.indexOf("ru") > -1) {
		splitDate = date.split('.');
		day = parseInt(splitDate[0]);
		month = parseInt(splitDate[1]);
	} else {
		splitDate = date.split('/');
		day = parseInt(splitDate[1]);
		month = parseInt(splitDate[0]);
	}
	var listofDays = [ 31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31 ];
	if (month !== 2) {
		if (day > listofDays[month - 1]) {
			return false;
		} else {
			return true;
		}
	} else {
		var year = parseInt(splitDate[2]);
		var leapYear = false;
		if ((!(year % 4) && year % 100) || !(year % 400)) {
			leapYear = true;
		}
		if ((leapYear == false) && (day >= 29)) {
			return false;
		} else if ((leapYear == true) && (day > 29)) {
			return false;
		} else {
			return true;
		}
	}
}
