var xhttp = new XMLHttpRequest();
xhttp.open("GET", "services/Random/ajax", true);

xhttp.onreadystatechange = function() {
	if (xhttp.readyState == 4 && xhttp.status == 200) {
		document.getElementById("ajax").innerHTML = this.responseText;
	}
};
xhttp.setRequestHeader('X-Requested-With', 'XMLHttpRequest');
xhttp.send();
