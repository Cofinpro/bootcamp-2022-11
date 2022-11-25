

function submitEntry() {
    let name = document.getElementById("name-guest-book").value;
    let comment = document.getElementById("comment-guest-book").value;

    if (isInvalidInput(name, comment)) {
        return;
    }

    let currentDate = createDate();

    createAndAddCommentToHTML(name, currentDate, comment);
    resetForm("form-itself");
}

function isInvalidInput(name, comment) {
    return isEmpty(name) || isEmpty(comment) || containsDog(name) || containsDog(comment);
}

function isEmpty(input) {
    if (input == "") {
        alert("Please fill in a name and a comment");
        return true;
    }
    return false;
}

function containsDog(input){
    input = input.toLowerCase();
    if (input.includes("hund") || input.includes("dog")) {
       alert("We do not allow dogs here!");
       return true;
    }
    return false;
}

function createDate() {
    let date = new Date();
    let day = date.getDate();
    let month = createMonth(date.getMonth() + 1);
    let hours = date.getHours();
    let minutes = date.getMinutes();

    return `${day} ${month} at ${hours}:${minutes}`;
}

function createMonth(number) {
    switch (number) {
    case 1: return "January";
    case 2: return "February";
    case 3: return "March";
    case 4: return "April";
    case 5: return "May";
    case 6: return "June";
    case 7: return "July";
    case 8: return "August";
    case 9: return "September";
    case 9: return "October";
    case 9: return "September";
    case 10: return "October";
    case 11: return "November";
    case 12: return "December";
    }
}

function createAndAddCommentToHTML(name, currentDate, comment){
    const div = document.createElement("div");
    const p1 = document.createElement("p");
    const p2 = document.createElement("p");
    const node1 = document.createTextNode(`${name} on ${currentDate}`);
    const node2 = document.createTextNode(comment);

    p1.appendChild(node1);
    p2.appendChild(node2);
    div.appendChild(p1);
    div.appendChild(p2);
    div.appendChild(document.createElement("hr"));

    document.getElementById("comments-guest-book").prepend(div);
}

function resetForm(form) {
    document.getElementById(form).reset();
}