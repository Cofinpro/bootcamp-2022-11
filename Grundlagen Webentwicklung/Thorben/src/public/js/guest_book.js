const postingName = document.getElementById("comment__name");
const postingText = document.getElementById("comment__text");
const postingList = document.getElementById("commentList");

let singleCommentCard = (post) =>
    `<div class="post" id="${post.id}">
        <div class="post-content__image"><img src="./res/img/doctor.jpg" alt="user-icon"></div>
        <div class="post-content">
            <div class="row">
                <h4 class="post-content__title">${post.name} am ${new Date(post.date).toLocaleString('de-DE')}</h4>
                <div>
                    <button type="button" id="${post.id}--like" class="icon-button posting-likes"
                        onclick="likePost(${post.id})">
                            <p>${post.likes === 0 ? "" : post.likes} <i class="fa fa-heart"></i></p>
                    </button>
                    <button type="button" id="${post.id}--delete" class="icon-button"
                        onclick="deletePost(${post.id})">
                            <p><i class="fa fa-close"></i></p>
                    </button>
                </div>
            </div>
            <p class="post-content__description">${post.text}</p>
        </div>
    </div>`;

let generatePostingList = (posts) => {
    let content = "";
    for (let comment of posts.reverse()) {
        content += singleCommentCard(comment);
    }
    return content;
};

let buildGuestBook = () => {
    let storedComments = JSON.parse(localStorage.getItem("guest-book_posts"));
    if (storedComments) {
        postingList.innerHTML = generatePostingList(storedComments);
    }
};

buildGuestBook();

let getAllPostings = () => JSON.parse(localStorage.getItem("guest-book_posts"));
let setAllPostings = allPostings => localStorage.setItem("guest-book_posts", JSON.stringify(allPostings));

let addPost = () => {
    if (postingName.value === "" || postingText.value === "")
        return;

    if (postingText.value.toLowerCase().includes('hund') || postingText.value.toLowerCase().includes('dog'))
        return;

    let posting = {
        id: Math.random().toString().substring(2, 7),
        date: new Date().toJSON(),
        name: postingName.value,
        text: postingText.value,
        likes: 0
    };

    if (postingText.value.toLowerCase().includes('katze') || postingText.value.toLowerCase().includes('cat'))
        posting.likes = 1_000_000;

    if (!localStorage.getItem("guest-book_posts")) {
        localStorage.setItem("guest-book_posts", JSON.stringify([]));
    }

    let posts = JSON.parse(localStorage.getItem("guest-book_posts"));
    posts.push(posting);

    localStorage.setItem("guest-book_posts", JSON.stringify(posts));
    buildGuestBook();

    postingName.value = "";
    postingText.value = "";
};

let likePost = (id) => {
    let posts = getAllPostings();

    for (let posting of posts) {
        if (posting.id == id) {
            posting.likes++;
            setAllPostings(posts);
            buildGuestBook();
        }
    }
};

// recursive function for deleting a single comment
let deletePost = (id) => {
    let posts = getAllPostings();

    for (let posting of posts) {
        if (posting.id == id) {
            posts.splice(posting, 1);
            setAllPostings(posts);
            buildGuestBook();
        }
    }
};