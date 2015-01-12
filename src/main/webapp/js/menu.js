function changeHrefsColors() {
	var matches = /(ListNews)|(DeleteNews)/.test(location.pathname);
	if (matches) {
		document.getElementById("menu-list-link").style.color = "#ff7200";
	} else {
		document.getElementById("menu-list-link").style.color = "blue";
	}
	matches = /(AddNews)/.test(location.pathname);
	if (matches) {
		document.getElementById("menu-add-link").style.color = "#ff7200";

	} else {
		document.getElementById("menu-add-link").style.color = "blue";
	}
}