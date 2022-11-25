const TEXT_INVALID = "Bitte füllen Sie das Feld aus";
const EMAIL_INVALID = "Die eingegebene E-Mail ist nicht valide";

let comments = [
    {
        id: 1,
        title: "Hunde sind toll",
        comment: "Alle Hunde sind unglaublich toll",
        date: new Date(Date.now()),
        name: "Max Mustermann"
    },
    {
        id: 2,
        title: "Hunde sind toll",
        comment: "Alle Hunde sind unglaublich toll",
        date: new Date(Date.now()),
        name: "Max Mustermann"
    },
]

document.addEventListener("DOMContentLoaded", (event) => {

    let valid = false;
    const localStorageComments = JSON.parse(localStorage.getItem("comments"));
    if (localStorageComments == null) {
        localStorage.setItem("comments", JSON.stringify(comments));
    }

    let $commentHTML = document.getElementById("comments");

    localStorageComments.forEach((comment) => {
        $commentHTML.insertAdjacentHTML("beforeend", printComment(comment))
    })


    let $commentForm = document.getElementById("comment-form")
    $commentForm.addEventListener('submit', function (event) {
        event.preventDefault();

        let inputFields = event.target.elements;
        for (let i = 0; i < inputFields.length; i++) {
            if(inputFields[i].type !== "submit"){
                valid = isValid(inputFields[i])
            }
        }

        const email = event.target.elements["email"].value;
        const name = event.target.elements["name"].value;
        const title = event.target.elements["title"].value;
        const comment = event.target.elements["comment"].value;

        const newComment = {
            id: localStorageComments.length + 1,
            title: title,
            comment: comment,
            date: new Date(Date.now()),
            name: name
        }

        if (valid) {
            $commentHTML.insertAdjacentHTML("beforeend", printComment(newComment))
            localStorageComments.push(newComment);
            localStorage.setItem("comments", JSON.stringify(localStorageComments));
            $commentForm.reset()
            for (let i = 0; i < inputFields.length; i++) {
                inputFields[i].parentNode.querySelector("small").innerText = ""
            }
        }



    })


})

function printComment(comment) {
    return `
            <div>
            <b>${comment.title}</b>
            <p>Verfasst am: ${new Date(comment.date).toDateString()}</p>
            <p>${comment.comment}</p>
            <small>- ${comment.name}</small>
            </div>
        `
}

function isValid(input) {

    if (input.value.trim() === "" && input.type !== "email") {
        showMessage(input, TEXT_INVALID);
        return false;
    }

    if(input.type === "email") {
        showMessage(input, EMAIL_INVALID);
        return false;
    }

    return true;

}

function showMessage(input, message) {
    const $errorElement = input.parentNode.querySelector("small");
    $errorElement.innerText = message;
}


