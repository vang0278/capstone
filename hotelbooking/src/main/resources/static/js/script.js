
function navbarActivate(id) {
	console.log("navbarActivate: " + id)
	let navitem = document.getElementById(id);
	if (navitem != null) {
		console.log(id + " activated!")
		navitem.classList.add("active");
	}
}
