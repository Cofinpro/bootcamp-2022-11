document.addEventListener("DOMContentLoaded", () => {

    const $slides = document.getElementsByClassName("slider--slide");
    const $sliderPoints = document.getElementById("slider-points")
    const $nextButton = document.getElementById("next");
    const $prevButton = document.getElementById("prev");
    let currIndex = 0;


    for (let i = 0; i < $slides.length; i++) {
        if(i !== 0) $slides[i].style.display = "none";
        $sliderPoints.insertAdjacentHTML("beforeend", "<div class='slider-point'></div>");
    }

    const $sliderIndicatorPoints = document.getElementsByClassName("slider-point");
    $sliderIndicatorPoints[currIndex].style.backgroundColor = "var(--primary-orange)"

    $nextButton.addEventListener('click', () =>{
        if(currIndex + 1 < $slides.length){
            $slides[currIndex].style.display = "none";
            $slides[currIndex + 1].style.display = null;
            $sliderIndicatorPoints[currIndex].style.backgroundColor = "#f0f0f0";
            currIndex++;
            $sliderIndicatorPoints[currIndex].style.backgroundColor = "var(--primary-orange)";

        }
    })

    $prevButton.addEventListener('click', () => {
        if(currIndex !== 0){
            $slides[currIndex].style.display = "none";
            $slides[currIndex - 1].style.display = null;
            $sliderIndicatorPoints[currIndex].style.backgroundColor = "#f0f0f0";
            currIndex--;
            $sliderIndicatorPoints[currIndex].style.backgroundColor = "var(--primary-orange)";
        }
    })

})

