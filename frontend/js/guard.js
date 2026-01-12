(async function () {
  const res = await fetch("http://localhost:6081/api/secure/run", {
    credentials: "include"
  });

  if (res.status === 401) {
    window.location.replace("login.html");
  }
})();
