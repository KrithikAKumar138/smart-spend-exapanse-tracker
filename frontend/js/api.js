function login() {
  // TEMPORARY
  window.location.href = "dashboard.html";
}

function register() {
  alert("Registered successfully!");
  window.location.href = "index.html";
}
const BASE_URL = "http://localhost:6081";

export async function requireAuth() {
  const res = await fetch(`${BASE_URL}/api/secure/run`, {
    credentials: "include"
  });

  if (res.status === 401) {
    window.location.replace("login.html");
    throw new Error("Unauthorized");
  }

  return res.text();
}
