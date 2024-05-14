// main.js
function openInfo(infoName, elmnt) {
    var i, info, tablinks;
    info = document.getElementsByClassName("info");
    tablinks = document.getElementsByClassName("tablink");

    for (i = 0; i < info.length; i++) {
        if (info[i].style.display === "block" && elmnt.style.backgroundColor === "#888") {
            info[i].style.display = "none";
            elmnt.style.backgroundColor = "";
            return;
        }
        info[i].style.display = "none";
    }

    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].style.backgroundColor = "";
    }

    document.getElementById(infoName).style.display = "block";
    elmnt.style.backgroundColor = "#888";
}
