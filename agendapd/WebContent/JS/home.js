function resizeImage() {
	var clientWidth = document.documentElement.clientWidth;
	var clientHeight = document.documentElement.clientHeight;
	let el = document.getElementById("homepage").style;
	el.width = clientWidth + "px";
	el.height = clientHeight + "px";
}