//Dark mode
var isDarkMode = true;

//Lightswitch
function lightswitchOnClick() {
    isDarkMode = !isDarkMode;

    //Enable dark mode
    if (isDarkMode) {
        //Log
        console.log("Enable dark mode");

        //Color
        document.documentElement.style.setProperty('--col_dark', '#111');
        document.documentElement.style.setProperty('--col_normal', '#333');
        document.documentElement.style.setProperty('--col_text', '#fff');

        //Image
        document.getElementById('lightswitch').style.backgroundImage = "url(images/lightmode_lightswitch_icon.png)";

        //Animation
        Array.from(document.getElementsByClassName('float_chapter_image')).forEach(cat => cat.style.animation = "cat_turn_2 1s linear forwards");
    }
    //Enable light mode
    else {
        //Log
        console.log("Enable light mode");

        //Color
        document.documentElement.style.setProperty('--col_dark', '#aaa');
        document.documentElement.style.setProperty('--col_normal', '#fff');
        document.documentElement.style.setProperty('--col_text', '#000');

        //Image
        document.getElementById('lightswitch').style.backgroundImage = "url(images/darkmode_lightswitch_icon.png)";

        //Animation
        Array.from(document.getElementsByClassName('float_chapter_image')).forEach(cat => cat.style.animation = "cat_turn_1 1s linear forwards");
    }
}