let currentPicIndex = 0;
let pictures = document.getElementsByClassName("pic");

function scrollPic(direction) {
    pictures[currentPicIndex].style.display = "none";
    currentPicIndex += direction;
    if (currentPicIndex === pictures.length) {
        currentPicIndex = 0;
    }
    if (currentPicIndex === -1) {
        currentPicIndex = pictures.length - 1;
    }
    pictures[currentPicIndex].style.display = "block";
}