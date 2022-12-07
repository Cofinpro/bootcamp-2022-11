let currentImageIndex = 0;
let images = [];
let imageNames = ["IMG1", "IMG2", "IMG3"];
let imageSource = ["https://www.ardeaprints.com/p/172/cat-football-kicking-volley-11688073.jpg.webp",
    "https://catvills.com/wp-content/uploads/2020/08/sporty-cat-names-male-850x446.jpg?ezimgfmt=rs:330x173/rscb1/ngcb1/notWebP",
    "https://i0.wp.com/theverybesttop10.com/wp-content/uploads/2016/09/Top-10-Fit-Active-Cats-Who-Love-Sports-10-510x366.jpg?resize=600%2C430"];

for(let i = 0; i < imageNames.length; i++) {
    let element = document.createElement("img");
    element.setAttribute("src", imageSource[i]);
    element.setAttribute("alt", imageNames[i]);
    images.push(element);
}

document.getElementById("imageSliderImage").setAttribute("src", images.at(currentImageIndex).src);

function changeImage(clickValue) {
    currentImageIndex = (currentImageIndex + clickValue) % (images.length);
    document.getElementById("imageSliderImage").setAttribute("src",  images.at(currentImageIndex).src);
}