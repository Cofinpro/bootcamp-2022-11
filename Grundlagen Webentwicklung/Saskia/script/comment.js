const TEXT_INVALID = "Bitte füllen Sie das Feld aus";
const EMAIL_INVALID = "Die eingegebene E-Mail ist nicht valide";
const TEXT_CONTAINS_CAT = "Katzen sind hier nicht erlaubt!";


let comments = [
    {
        id: "1",
        title: "Hunde sind toll",
        comment: "Alle Hunde sind unglaublich toll",
        date: new Date(Date.now()),
        name: "Max Mustermann",
        email: "max.mustermann@email.de",
        like: 10,
        dislike: 2,
    },
    {
        id: "2",
        title: "Hunde sind toll",
        comment: "Diese Website hat mir gezeigt, wie unglaublich toll Hunde sind. Sie sind viel besser als Katzen!",
        date: new Date(Date.now()),
        name: "Maxine Musterfrau",
        email: "Maxine.Musterfrau@email.de",
        like: 112,
        dislike: 5,
    },
]

document.addEventListener("DOMContentLoaded", () => {

    const localStorageComments = JSON.parse(localStorage.getItem("comments"));
    const $like = document.getElementsByClassName("like");
    const $dislike = document.getElementsByClassName("dislike");


    if (localStorageComments == null) {
        localStorage.setItem("comments", JSON.stringify(comments));
        location.reload();
    }

    let $commentHTML = document.getElementById("comments");

    localStorageComments.forEach((comment) => {
        $commentHTML.insertAdjacentHTML("beforeend", printComment(comment))
    })


    let $commentForm = document.getElementById("comment-form")
    $commentForm.addEventListener('submit', function (event) {
        if (checkForm(event)) {
            addComment(event, localStorageComments, $commentHTML, $commentForm);
        }
    })

    for (const element of $like) {
        element.addEventListener("click", addLike)
    }

    for (const element of $dislike) {
        element.addEventListener("click", addDislike)
    }


})

/**
 * Check if form is valid and
 * @param event
 */
function checkForm(event) {

    event.preventDefault();

    let valid = false;

    let inputFields = event.target.elements;
    for (let i = 0; i < inputFields.length; i++) {
        if (inputFields[i].type !== "submit") {
            valid = isValid(inputFields[i])
        }
    }

    return valid;
}

/**
 * Add a new comment in localstorage
 * @param event
 * @param localStorageComments
 * @param $commentHTML
 * @param $commentForm
 */
function addComment(event, localStorageComments, $commentHTML, $commentForm){
    const newComment = {
        id: (localStorageComments.length + 1).toString(),
        title: event.target.elements["title"].value,
        comment: event.target.elements["comment"].value,
        date: new Date(Date.now()),
        name: event.target.elements["name"].value,
        email: event.target.elements["email"].value,
        like: 0,
        dislike: 0,
    }

    $commentHTML.insertAdjacentHTML("beforeend", printComment(newComment))
    localStorageComments.push(newComment);
    localStorage.setItem("comments", JSON.stringify(localStorageComments));
    $commentForm.reset()
    location.reload();
    for (let i = 0; i < event.target.elements.length; i++) {
        resetShowMessage(event.target.elements[i]);
    }

}

/**
 * Add a new like to correct comment
 * @param event contains the element of the specific like button
 */
function addLike(event) {
    const localStorageComments = JSON.parse(localStorage.getItem("comments"));
    localStorageComments.forEach((comment) => {
        if (comment.id === event.target.id.split("comment_").pop()) {
            comment.like++;

        }
    })
    localStorage.setItem("comments", JSON.stringify(localStorageComments));
    location.reload()
}

/**
 * Add a new dislike to correct comment
 * @param event contains the element of the specific dislike button
 */
function addDislike(event) {
    const localStorageComments = JSON.parse(localStorage.getItem("comments"));
    localStorageComments.forEach((comment) => {
        if (comment.id === event.target.id.split("comment_").pop()) {
            comment.dislike++;
            console.log(comment)
        }
    })
    localStorage.setItem("comments", JSON.stringify(localStorageComments));
    location.reload()
}

/**
 * Prints a comment
 * Containing the html structure for a comment, which contains all relevant data of the comment
 *
 * @param comment
 * @returns {string}
 */
function printComment(comment) {
    return `
            <div class="comment-wrapper">
            <img src="./assets/dogs/dog.jpg" alt="">
            <div class="comment-text">
            <b>${comment.name}</b>
            <p class="comment-title">${comment.title}</p>
            <p>${comment.comment}</p>
            <div class="comment-buttons">
                <span class="like" id="comment_${comment.id}"><i class="fa fa-thumbs-up"></i>${comment.like}</span>
                <span class="dislike" id="comment_${comment.id}"><i class="fa fa-thumbs-down"></i>${comment.dislike}</span>
                <small>${printDateFormat(comment.date)}</small>
            </div>
            </div>
            </div>
        `
}

/**
 * Print date format prettier
 * @param date contains date in timestamp format
 * @returns {string}
 */
function printDateFormat(date) {
    const newDate = new Date(date);
    const formattedDate = `${newDate.getDay()}.${newDate.getMonth() + 1}.${newDate.getFullYear()}`
    const formattedTime = `${newDate.getHours()}:${String(newDate.getMinutes()).padStart(2, '0')} Uhr`
    return `${formattedDate} ${formattedTime}`
}


/**
 * Check if given input is valid depending on type
 * @param input
 * @returns {boolean}
 */
function isValid(input) {
    resetShowMessage(input);
    const value = input.value.toLowerCase();

    if (value.trim() === "" && input.type !== "email") {
        showMessage(input, TEXT_INVALID);
        return false;
    }

    if (input.type === "email" && !validateEmail(value)) {
        showMessage(input, EMAIL_INVALID);
        return false;
    }

    if (input.type !== "email" && value.includes("katze") || value.includes("cat")) {
        showMessage(input, TEXT_CONTAINS_CAT);
        return false;
    }

    return true;

}

/**
 * Validate given email string if it contains all relevant characters for email
 * @param email
 * @returns {boolean}
 */
function validateEmail(email) {
    const re = /\S+@\S+\.\S+/;
    return re.test(email);
}

/**
 * Reset inner text of error
 * @param input
 */
function resetShowMessage(input) {
    const $errorElement = input.parentNode.querySelector("small");
    $errorElement.innerText = "";
}

/**
 * Insert inner text of error with given message
 * @param input
 * @param message
 */
function showMessage(input, message) {
    const $errorElement = input.parentNode.querySelector("small");
    $errorElement.innerText = message;
}


