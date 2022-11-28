const container = document.querySelector('.slider-container');
const slide = document.querySelectorAll('.slider-single');
const slideTotal = slide.length - 1;
let slideCurrent = -1;

function initArrows() {
    const iconLeft = document.createElement('i');
    iconLeft.classList.add('fa')
    iconLeft.classList.add('fa-arrow-left')

    const leftArrow = document.createElement('a')
    leftArrow.classList.add('slider-left')
    leftArrow.appendChild(iconLeft)
    leftArrow.addEventListener('click', () => {
        slideLeft();
    })

    const iconRight = document.createElement('i');
    iconRight.classList.add('fa')
    iconRight.classList.add('fa-arrow-right')

    const rightArrow = document.createElement('a')
    rightArrow.classList.add('slider-right')
    rightArrow.appendChild(iconRight)
    rightArrow.addEventListener('click', () => {
        slideRight();
    })

    container.append(leftArrow, rightArrow);
}

function slideInitial() {
    initArrows();
    setTimeout(function () {
        slideTo(++slideCurrent);
    }, 500);
}

const slideLeft = () => slideTo(--slideCurrent);
const slideRight = () => slideTo(++slideCurrent);

function slideTo(direction) {
    slideCurrent = slideCurrent >= 0 ? slideCurrent <= slideTotal ? direction : 0 : slideTotal;
    let pre = slideCurrent > 0 ? slide[slideCurrent - 1] : slide[slideTotal];
    let post = slideCurrent < slideTotal ? slide[slideCurrent + 1] : slide[0];
    const activeSlide = slide[slideCurrent];

    slide.forEach((elem) => {
        const thisSlide = elem;
        if (thisSlide.classList.contains('proactive')) {
            thisSlide.classList.remove('preactivede');
            thisSlide.classList.remove('preactive');
            thisSlide.classList.remove('active');
            thisSlide.classList.remove('proactive');
            thisSlide.classList.add('proactivede');
        }
        if (thisSlide.classList.contains('proactivede')) {
            thisSlide.classList.remove('preactive');
            thisSlide.classList.remove('active');
            thisSlide.classList.remove('proactive');
            thisSlide.classList.remove('proactivede');
            thisSlide.classList.add('preactivede');
        }
    });

    pre.classList.forEach(x => null)
    pre.classList.remove('preactivede');
    pre.classList.remove('active');
    pre.classList.remove('proactive');
    pre.classList.remove('proactivede');
    pre.classList.add('preactive');

    activeSlide.classList.remove('preactivede');
    activeSlide.classList.remove('preactive');
    activeSlide.classList.remove('proactive');
    activeSlide.classList.remove('proactivede');
    activeSlide.classList.add('active');

    post.classList.remove('preactivede');
    post.classList.remove('preactive');
    post.classList.remove('active');
    post.classList.remove('proactivede');
    post.classList.add('proactive');
}

slideInitial();