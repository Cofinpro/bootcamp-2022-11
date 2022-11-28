function addComment(){
let form = {
    name: document.getElementById("name"),
    comment: document.getElementById("comment"),
    isDog: document.getElementById("dogTest")
    }

    if (dogAndHundCheck(form.name) || dogAndHundCheck(form.comment) 
    || form.isDog.checked==false){
        alert("Get off my Page Filthy creature!")
        resetForm(form);
    } else {
        const listOfComments = document.getElementById("listOfComments");
        let today = new Date();
        const dateAsString = today.toDateString() + " " + today.toTimeString().substring(0,5);
    
        let values = {
            name: form.name.value,
            comment: form.comment.value,
            datetime: dateAsString
        }
    let returnedValues = postToBackend(values);
    getDataFromBackendAndResetListToBackendState();
    resetForm(form);
}
}




//checks if dog and/or hund appear in comment/name
function dogAndHundCheck(element){
    return (element.value.toLowerCase().includes("dog") || 
        element.value.toLowerCase().includes("hund"))
}

//function for editing comment and syncing frontend with backend
function editComment(id){
    const comment = document.getElementById(`comment${id}`).innerHTML;
    console.log(comment);
    document.getElementById(`comment${id}`).innerHTML=`
<textarea rows=\"4\" id="editBox">${comment}</textarea>`;
    document.getElementById(`edit${id}`).remove();
    const confirm = document.createElement("button");
    confirm.setAttribute("class",`edit`);
    confirm.setAttribute("id",`confirm${id}`);
    confirm.setAttribute("onclick",`confirmEdit(${id})`);
    confirm.appendChild(document.createTextNode("Confirm"));
    document.getElementById(`namedate${id}`).appendChild(confirm);
}

//confirm edit 
function confirmEdit(id){
    let textDTO={
        text: document.getElementById(`editBox`).value
    };
    document.getElementById(`confirm${id}`).remove();
    putCommentChangesToBackend(textDTO,id);
    getDataFromBackendAndResetListToBackendState();
}

function deleteComment(id) {
    deleteFromBackend(id);
    getDataFromBackendAndResetListToBackendState();
}