document.addEventListener("DOMContentLoaded", () => {
    const form = document.getElementById("edit-profile-form");
    const message = document.getElementById("message");
    const token = localStorage.getItem("jwtToken");

    if(!token){
        alert("ログインしてません。")
        return;
    }

    // ユーザー情報の取得
    fetch("http://localhost:8080/api/users/me", {
        method: "GET",
        headers: {
            Authorization: `Bearer ${token}`,
        },
    })
        .then((res) => {
            if (!res.ok) throw new Error("ユーザー情報の取得に失敗しました");
            return res.json();
        })
        .then((user) => {
            document.getElementById("name").value = user.name || "";
            document.getElementById("email").value = user.email || "";
            document.getElementById("carLifeYear").value = user.carLifeYear || "";
            document.getElementById("gender").value = user.gender ?? "";
            document.getElementById("hobbies").value = user.hobbies || "";
            document.getElementById("introduction").value = user.introduction || "";
        })
        .catch((error) => {
            message.textContent = error.message;
            console.error(error);
        });

    form.addEventListener("submit", async (e) => {
        e.preventDefault();
        const formData = new FormData(form);

        try {
            const response = await fetch("http://localhost:8080/api/users/me", {
                method: "PUT",
                headers: {
                    Authorization: `Bearer ${token}`,
                },
                body: formData,
            });

            if (!response.ok) {
                const error = await response.json();
                throw new Error(error.message || "更新に失敗しました");
            }

            message.textContent = "プロフィールが更新されました！";
            message.style.color = "green";

            setTimeout(() => {
                window.location.href = "../html/profile.html"
            }, 1000);
        } catch (error) {
            message.textContent = error.message;
            message.style.color = "red";
        }
    });
});
