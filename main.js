// main.js
function openProjects(infoName, elmnt) {
    var i, info, tablinks, t;
    info = document.getElementsByClassName("projects");
    tablinks = document.getElementsByClassName("tablink");
    t = false;

    for (i = 0; i < info.length; i++) {
        if (tablinks[i] === elmnt && document.getElementById(infoName).style.display === "block") {
            info[i].style.display = "none";
            elmnt.style.backgroundColor = "#04AA6D"; // Reset the button color
            t = true;
        }
    }

    for (i = 0; i < info.length; i++) {
        tablinks[i].style.backgroundColor = "#04AA6D";
        info[i].style.display = "none";
    }
    if (t) {
        return;
    }
    document.getElementById(infoName).style.display = "block";
    elmnt.style.backgroundColor = "#3e8e41"; // Set the button color when pressed
}

function openSkills(infoName, elmnt) {
    var i, info, tablinks;
    info = document.getElementsByClassName("skills");
    tablinks = document.getElementsByClassName("tablink");
    t = false;

    for (i = 4; i < tablinks.length; i++) {
        if (tablinks[i] === elmnt && document.getElementById(infoName).style.display === "block") {
            info[i].style.display = "none";
            elmnt.style.backgroundColor = "#04AA6D"; // Reset the button color
            t = true;
        }
    }

    for (i = 0; i < info.length; i++) {
        tablinks[i].style.backgroundColor = "#04AA6D";
        info[i].style.display = "none";
    }

    if (t) {
        return;
    }

    document.getElementById(infoName).style.display = "block";
    elmnt.style.backgroundColor = "#3e8e41"; // Set the button color when pressed

}