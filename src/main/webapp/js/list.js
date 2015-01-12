function confirmDeleteChecked(form, msg) {
	var inputs = document.getElementsByTagName("input");
	var checked = [];
	for (var i = 0; i < inputs.length; i++) {
		if (inputs[i].type == "checkbox" && inputs[i].checked) {
			checked.push(inputs[i]);
		}
	}
	var quantity = checked.length;
	if (quantity > 0) {
		if (confirm(msg.deteteAll + " " + quantity)) {
			return true;
		} else {
			return false;
		}
	} else {
		alert(msg.deleteInfo);
		return false;
	}

}