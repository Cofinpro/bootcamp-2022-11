//sync frontend with backend when window is loaded

window.onload = function() {
    getDataFromBackendAndResetListToBackendState();
}
const backendHome ="http://localhost:8810"

//syncs frontend with backend, called when changes are commited through delete/confirm edit/submit

function getDataFromBackendAndResetListToBackendState(){
    const returnValues = getEverythingFromBackend();
    const listOfComments = document.getElementById("listOfComments");
    listOfComments.innerHTML="";
    for (let i = 0; i < returnValues.length; i++){
        appendCommentsToList(returnValues[i].name,returnValues[i].datetime,
        returnValues[i].comment,returnValues[i].id,listOfComments)
    }
} 

//helper function, retruns object of return parameters

function getEverythingFromBackend(){
    const xhr = new XMLHttpRequest();
    const urlOfGetBackend = backendHome + "/api/comment";
    xhr.open("GET",urlOfGetBackend,false);
    xhr.setRequestHeader('Content-type','application/json','charset=utf-8');
    xhr.send("");
    return JSON.parse(xhr.responseText);
}

//posts changes to backend

function postToBackend(formValues){
    const json = JSON.stringify(formValues);
    console.log(json)
    const xhr = new XMLHttpRequest();
    const urlOfPostBackend = backendHome + "/api/comment/save"
    xhr.open("POST",urlOfPostBackend,false);
    xhr.setRequestHeader('Content-type','application/json','charset=utf-8');
    xhr.send(json);
    return JSON.parse(xhr.responseText);
}

//delete request to backend

function deleteFromBackend(id){
    const xhr = new XMLHttpRequest();
    const urlOfDeleteBackend= backendHome + `/api/comment/delete/${id}`;
    xhr.open("DELETE", urlOfDeleteBackend,false);
    xhr.setRequestHeader('Content-type','application/json','charset=utf-8');
    xhr.send("");
    getDataFromBackendAndResetListToBackendState()
}

function putCommentChangesToBackend(textDTO, id){
    let urlOfPutBackend = backendHome + `/api/comment/edit/${id}`;
    const xhr = new XMLHttpRequest();
    xhr.open("PUT",urlOfPutBackend,false);
    xhr.setRequestHeader('Content-type','application/json','charset=utf-8');
    xhr.send(JSON.stringify(textDTO));
}