/*
class ImageSlider {
    images;
    slider;
    currentImageIndex = 0;
    constructor(images) {
        this.images = images;
        this.slider = document.querySelector("#imageSlider");
    }

    createSlider() {
        let buttonNext = document.createElement("button");
        buttonNext.innerHTML = "Next";
        let buttonPrev = document.createElement("button");
        buttonPrev.innerHTML = "Previous";
        buttonPrev.onclick = this.onPrev;
        buttonNext.onclick = this.onNext;

        console.log(this.currentImageIndex);
        console.log(this.images.length)
        this.slider.appendChild(this.images[this.currentImageIndex+1]);
        this.slider.appendChild(buttonPrev);
        this.slider.appendChild(buttonNext);
    }

    onNext() {
        console.log(this.currentImageIndex);
        this.currentImageIndex++;
        console.log(this.currentImageIndex);
        this.slider.appendChild(this.images[this.currentImageIndex]);
    }

    onPrev() {
        this.currentImageIndex--;
        this.slider.appendChild(this.images[this.currentImageIndex]);
    }
}

let element1 = document.createElement("img");
element1.setAttribute("src", "https://www.ardeaprints.com/p/172/cat-football-kicking-volley-11688073.jpg.webp");
element1.setAttribute("alt", "IMG11");

let element2 = document.createElement("img");
element2.setAttribute("src", "https://www.ardeaprints.com/p/172/cat-football-kicking-volley-11688073.jpg.webp");
element2.setAttribute("alt", "IMG11");

let images = [element1, element2];

let imageSlider = new ImageSlider(images);
imageSlider.createSlider();

 */