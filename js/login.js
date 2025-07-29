document.addEventListener("DOMContentLoaded", () => {
    const justLoggedIn = localStorage.getItem("justLoggedIn");

    if(justLoggedIn === "true"){
        alert("ログインしました。");
        localStorage.removeItem("justLoggedIn");
    }
})