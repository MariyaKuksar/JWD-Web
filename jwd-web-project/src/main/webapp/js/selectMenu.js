function selectMenu(index){
	var menu = document.getElementById("menu1");
	if (menu.hasChildNodes()) {
		if (menu.children[index].hasChildNodes()) {
			var children = menu.children[index].children;
			children[0].style.color = "#008080";
			children[0].style.backgroundColor = "#fff";
		}
	}
}