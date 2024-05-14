function openInfo(infoName, elmnt) {
    var i, info, tablinks;
    info = document.getElementsByClassName("info");
    for (i = 0; i < info.length; i++) {
        info[i].style.display = "none";
    }
    tablinks = document.getElementsByClassName("tablink");
    for (i = 0; i < tablinks.length; i++) {
        tablinks[i].style.backgroundColor = "";
    }
    document.getElementById(infoName).style.display = "block";
    elmnt.style.backgroundColor = "#888";
}
