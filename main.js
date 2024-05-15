// main.js
function openProjects(infoName, elmnt) {
    var i, info, tablinks;
    info = document.getElementsByClassName("projects");
    tablinks = document.getElementsByClassName("tablink");

    for (i = 0; i < info.length; i++) {
        info[i].style.display = "none";
    }

    for (i = 0; i < info.length; i++) {
        if (tablinks[i] === elmnt && tablinks[i].style.backgroundColor === "rgb(136, 136, 136)") {
            tablinks[i].style.backgroundColor = "#555";
            return;
        }
        tablinks[i].style.backgroundColor = "#555";
    }

    document.getElementById(infoName).style.display = "block";
    elmnt.style.backgroundColor = "#888";
}

function openSkills(infoName, elmnt) {
    var i, info, tablinks;
    info = document.getElementsByClassName("skills");
    tablinks = document.getElementsByClassName("tablink");

    for (i = 0; i < info.length; i++) {
        info[i].style.display = "none";
    }

    for (i = 4; i < tablinks.length; i++) {
        if (tablinks[i] === elmnt && tablinks[i].style.backgroundColor === "rgb(136, 136, 136)") {
            tablinks[i].style.backgroundColor = "#555";
            return;
        }
        tablinks[i].style.backgroundColor = "#555";
    }

    document.getElementById(infoName).style.display = "block";
    elmnt.style.backgroundColor = "#888";
}