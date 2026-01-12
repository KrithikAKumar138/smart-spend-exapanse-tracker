const API = "http://localhost:6081/api/auth";

async function login() {
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  const res = await fetch(API + "/login", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    credentials: "include", // 🔥 VERY IMPORTANT (JWT Cookie)
    body: JSON.stringify({ email, password })
  });

  if (res.ok) {
    window.location.href = "dashboard.html";
  } else {
    document.getElementById("error").innerText = "Invalid credentials";
  }
}

async function register() {
  const name = document.getElementById("name").value;
  const email = document.getElementById("email").value;
  const password = document.getElementById("password").value;

  const res = await fetch(API + "/register", {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    body: JSON.stringify({ name, email, password })
  });

  if (res.ok) {
    window.location.href = "login.html";
  } else {
    document.getElementById("error").innerText = "Email already exists";
  }
}
async function logout() {
  await fetch("http://localhost:6081/api/auth/logout", {
    method: "POST",
    credentials: "include"
  });

  window.location.replace("login.html");
}
