document.addEventListener("DOMContentLoaded", async () => {
    const token = localStorage.getItem("jwtToken");

    if (!token) {
        alert("ログインが必要です");
        window.location.href = "/html/login.html";
        return;
    }

    try {
        const response = await fetch("http://localhost:8080/api/users/me", {
            method: "GET",
            headers: {
                Authorization: `Bearer ${token}`,
            },
        });

        if (!response.ok) {
            throw new Error("ユーザー情報の取得に失敗しました");
        }

        const user = await response.json();

        document.getElementById("name").textContent = user.name || "";
        document.getElementById("email").textContent = user.email || "";
        document.getElementById("carLifeYear").textContent = user.carLifeYear ?? "未登録";
        document.getElementById("gender").textContent = convertGender(user.gender);
        document.getElementById("hobbies").textContent = user.hobbies || "未登録";
        document.getElementById("introduction").textContent = user.introduction || "未登録";

        const imagePath = document.getElementById("profileImage");

        console.log("取得したユーザー情報:", user);

        if (user.profileImage) {
            profileImage.src = `/images/${user.profileImage}?v=${Date.now()}`;
            console.log("プロフィール画像のURL:", profileImage.src);
        } else {
            profileImage.src = "/images/default-icon.png"; 
            console.log("デフォルト画像のURL:", profileImage.src);
        }

        // 編集ボタンで編集画面に遷移
        document.getElementById("editButton").addEventListener("click", () => {
            window.location.href = "/html/edit-profile.html";
        });

    } catch (error) {
        console.error("エラー:", error);
        alert("ユーザー情報の取得中にエラーが発生しました");
    }
});

function convertGender(value) {
    switch (value) {
        case 0: return "男性";
        case 1: return "女性";
        case 2: return "その他";
        default: return "未登録";
    }
}
