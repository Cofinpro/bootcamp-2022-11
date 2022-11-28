const form = document.getElementById("form");
form.addEventListener("submit", (event) => {
    event.preventDefault();
    let formData = new FormData(document.getElementById("form"));
    if(createGuestbookEntry(formData)) {
        form.reset();
    }
})

/**
 * Creates the comment-entry and adds it to the comment-section, in case the form-input is correct
 * @param formData
 * @returns {boolean}
 */
function createGuestbookEntry(formData) {
    const isInputCorrect = checkInput(formData);
    if(isInputCorrect) {
        let newEntry = document.createElement("div");
        newEntry.classList.add("commentEntry");
        let firstLine = document.createElement("p");
        firstLine.innerText = formData.get("name") + " am " + new Date().toLocaleDateString() + " um " + new Date().toLocaleTimeString().slice(0, 5) + " Uhr";
        let secondLine = document.createElement("p");
        secondLine.innerText = formData.get("comment")
        newEntry.appendChild(firstLine);
        newEntry.appendChild(secondLine);

        // adding the created comment-entry to the top of the comment-section
        document.getElementById("guestbookEntries").prepend(newEntry);
        return true;
    } else {
        alert("Deine Eingabe war nicht korrekt. Wiederhole sie bitte");
        return false;
    }

}

function checkInput(formData) {
    if(formData.get("name").trim() !== ""
        && formData.get("comment").trim() !== ""
        && !formData.get("comment").toLowerCase().includes("hund")
        && !formData.get("comment").toLowerCase().includes("dog")) {
        return true;
    }
    return false;
}