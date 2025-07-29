document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("login-form");
    const errorMessage = document.getElementById("error-message");

    form.addEventListener("submit", async (event) => {
        event.preventDefault();

        const email = document.getElementById("email").value.trim();
        const password = document.getElementById("password").value.trim();

        try{
            const response = await fetch("http://localhost:8080/api/auth/login", {
                method: "POST",
                headers: {
                    "Content-Type": "application/json",
                },
                body: JSON.stringify({email, password})
            });

            if(!response.ok){
                const error = await response.json();
                throw new Error(error.message || "ログインに失敗しました。");
            }

            const data = await response.json();
            const token = data.token;

            localStorage.setItem("jwtToken", token);
            localStorage.setItem("justLoggedIn", "true");
            window.location.href = "../html/index.html";
        }catch (error){
            errorMessage.textContent = error.message;
        }
    });
    
});