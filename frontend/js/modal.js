await fetch("http://localhost:6081/api/transaction/add", {
  method: "POST",
  headers: { "Content-Type": "application/json" },
  credentials: "include",
  body: JSON.stringify({
    amount,
    category,
    description,
    type: transactionType
  })
});

// 🔥 THIS LINE FIXES EVERYTHING
await loadDashboard();

closeModal();