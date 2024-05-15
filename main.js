// main.js
function openInfo(infoName, elmnt) {
    var i, info, tablinks;
    info = document.getElementsByClassName("info");
    tablinks = document.getElementsByClassName("tablink");

    for (i = 0; i < info.length; i++) {
        if (info[i].style.display === "block" && elmnt.style.backgroundColor === "rgb(136, 136, 136)") {
            info[i].style.display = "none";
            elmnt.style.backgroundColor = "#555";
            return;
        }
        info[i].style.display = "none";
    }
    document.getElementById(infoName).style.display = "block";
    elmnt.style.backgroundColor = "#888";
}
