let idx = 0;
const catImageNames= new Map();
catImageNames.set(0,"pictures/cat.webp");
catImageNames.set(1,"pictures/cat_2.png");
catImageNames.set(2,"pictures/cat_3.png");
catImageNames.set(3,"pictures/cat_4.png");
catImageNames.set(4,"pictures/cat_5.png");
catImageNames.set(5,"pictures/cucci.jpeg")
function nextAndSetCat(){
    idx++;
    let picIndex = determinePicIndex(idx,catImageNames);
    let nextImage=catImageNames.get(picIndex);
    setCat(nextImage);
}
function setCat(image){
    cat = document.getElementById("catImage");
    cat.src=image;
}
function prevAndSetCat(){
    idx--;
    let picIndex = determinePicIndex(idx,catImageNames);
    let prevImage = catImageNames.get(picIndex);
    setCat(prevImage);
}
function determinePicIndex(idx,catImageNames){
    let picIndex =idx%catImageNames.size;
    if (picIndex<0) picIndex = Math.abs(catImageNames.size+picIndex);
    console.log(picIndex);
    return picIndex;
}