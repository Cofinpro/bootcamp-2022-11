function appendCommentsToList(name, dateAsString, comment, id, listOfComments){
    let commentOut = document.createElement('li');
    commentOut.appendChild(document.createTextNode(`${comment}`));
    commentOut.setAttribute("class","commentOut");
    commentOut.setAttribute("id",`comment${id}`);
    listOfComments.prepend(commentOut);

    let nameDate = document.createElement('li');
    nameDate.appendChild(document.createTextNode(
    `${name}, at ${dateAsString}`));
    nameDate.setAttribute("class", "nameDate");
    nameDate.setAttribute("id",`namedate${id}`);
    const deleteButton =createButton('delete',id)
    const editButton = createButton('edit',id)
    nameDate.appendChild(deleteButton);
    nameDate.appendChild(editButton);
    listOfComments.prepend(nameDate);
}


function createButton(label, id){
    const button = document.createElement('button');
    button.setAttribute("class",label);
    button.setAttribute("id", `${label}${id}`);
    button.setAttribute("onclick",`${label}Comment(${id})`)
    button.appendChild(document.createTextNode(label));
    return button;
}
//reset form

function resetForm(formValues){
    formValues.name.value="";
    formValues.comment.value="";
    formValues.isDog.checked=false;
}