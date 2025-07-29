document.getElementById("logoutButton").addEventListener("click", function() {
    localStorage.removeItem("token");
    alert("ログアウトしました。")
    window.location.href = "../html/login.html";
})