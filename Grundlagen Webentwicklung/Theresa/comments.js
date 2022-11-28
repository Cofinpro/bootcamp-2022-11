const guestbookEntries = [];

const button = document.getElementById("submit");
button.onclick = function() {
    window.event.preventDefault();

    const form = this.form;

    if (form.nickname.value.trim() === "" || form.message.value.trim() === "") {
        alert("You need to enter all values to post a comment!");
    } else if (form.nickname.value.includes("cat") || form.message.value.includes("cat")){
        alert("The civet is not a cat. You can't enter cat!!");
        form.reset();
    } else {
        const date = new Date();
        const min = ('0'+date.getMinutes()).slice(-2);
        const hour = ('0'+date.getHours()).slice(-2);
        const sec = ('0'+date.getSeconds()).slice(-2);

        const arg1 = `${hour}:${min}:${sec} ${date.getDate()}.${date.getMonth()+1}.${date.getFullYear()}`;
        const arg2 = form.nickname.value;
        const arg3 = form.message.value;

        const newEntry = {date: arg1, nickname: arg2, message: arg3};
        guestbookEntries.push(newEntry);

        const table = document.getElementsByTagName('table')[0];



        const row = table.insertRow(1);

        const cell1 = row.insertCell(0);
        const cell2 = row.insertCell(1);
        const cell3 = row.insertCell(2);

        cell1.innerHTML = arg1;
        cell2.innerHTML = arg2;
        cell3.innerHTML = arg3;

        form.reset();
    }
};
