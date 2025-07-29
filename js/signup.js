const form = document.getElementById("signup");
form.addEventListener("submit", async function (e) {
  e.preventDefault();

  const jsonData = {
    name: form.name.value,
    email: form.email.value,
    password: form.password.value
  };

  try {
    const response = await fetch("http://localhost:8080/api/auth/signup", {
      method: "POST",
      headers: {
        "Content-Type": "application/json"
      },
      body: JSON.stringify(jsonData),
    });

    if (!response.ok) {
      const errorText = await response.text();
      alert("登録失敗: " + errorText);
    } else {
      alert("登録完了しました。");
      form.reset();
    }
  } catch (error) {
    alert("通信エラーが発生しました。");
    console.error("エラー:", error);
  }
});
