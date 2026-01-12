let currentPage = 0;
const pageSize = 10;
let transactionType = "";
const BASE_URL = "http://localhost:6081";

/* ======================
   LOAD DASHBOARD TOTALS
====================== */
async function loadDashboard() {
  const res = await fetch(`${BASE_URL}/api/transaction/summary`, {
    credentials: "include"
  });

  if (!res.ok) {
    console.error("Summary API failed");
    return;
  }

  const data = await res.json();

  document.getElementById("incomeAmount").innerText = data.income;
  document.getElementById("expenseAmount").innerText = data.expense;
  document.getElementById("remainingAmount").innerText = data.remaining;
}


/* ======================
   PAGE LOAD
====================== */
document.addEventListener("DOMContentLoaded", () => {
  loadDashboard();
  loadTransactions();
  loadExpenseSummary();
  loadExpenseChart();
  

});

/* ======================
   MODAL HANDLING
====================== */
function openIncomeModal() {
  transactionType = "INCOME";
  document.getElementById("modalTitle").innerText = "Add Income";

  // 🔥 HIDE CATEGORY FOR INCOME
  document.getElementById("categoryWrapper").style.display = "none";

  showModal();
}


function openExpenseModal() {
  transactionType = "EXPENSE";
  document.getElementById("modalTitle").innerText = "Add Expense";

  // 🔥 SHOW CATEGORY FOR EXPENSE
  document.getElementById("categoryWrapper").style.display = "block";

  showModal();
}

function showModal() {
  document.getElementById("transactionModal").classList.remove("hidden");
  document.getElementById("modalOverlay").classList.remove("hidden");
}

function closeModal() {
  document.getElementById("transactionModal").classList.add("hidden");
  document.getElementById("modalOverlay").classList.add("hidden");
}

/* ======================
   SAVE TRANSACTION
====================== */
async function saveTransaction(event) {
  event.preventDefault();

  const amount = document.getElementById("amountInput").value;
  const description = document.getElementById("descriptionInput").value;

  // Only take category if EXPENSE
  const category =
    transactionType === "EXPENSE"
      ? document.getElementById("categoryInput").value
      : null;

  if (!amount || amount <= 0) {
    alert("Invalid amount");
    return;
  }

  const res = await fetch(`${BASE_URL}/api/transaction/add`, {
    method: "POST",
    headers: { "Content-Type": "application/json" },
    credentials: "include",
    body: JSON.stringify({
      amount,
      description,
      category,   // null for income ✅
      type: transactionType
    })
  });

  if (!res.ok) {
    alert("Save failed");
    return;
  }

  closeModal();
  await loadDashboard();
  await loadTransactions();
  await loadExpenseChart();
}



  // 🔥 THIS IS WHAT YOU WERE MISSING


async function loadTransactions() {
  const res = await fetch(
    `${BASE_URL}/api/transaction/list?page=${currentPage}&size=${pageSize}`,
    { credentials: "include" }
  );

  if (!res.ok) return;

  const data = await res.json();
  const list = document.getElementById("transactionsList");

  list.innerHTML = "";

  data.content.forEach(tx => {
    const row = document.createElement("div");
    row.className = `transaction-row ${tx.type.toLowerCase()}`;

    row.innerHTML = `
      <span>${tx.type === "INCOME" ? "Income" : tx.category}</span>
      <span>${tx.type === "INCOME" ? "+" : "-"} ₹${tx.amount}</span>
    `;

    list.appendChild(row);
  });

  updatePaginationButtons(data);
}

async function loadExpenseSummary() {
  const res = await fetch(`${BASE_URL}/api/transaction/expense-summary`, {
    credentials: "include"
  });

  if (!res.ok) return;

  const data = await res.json();
  const box = document.getElementById("expenseList");

  box.innerHTML = "";

  data.forEach(e => {
    box.innerHTML += `
      <div class="transaction-row expense">
        <span>${e.category}</span>
        <span>₹ ${e.total}</span>
      </div>
    `;
  });
}

let expenseChart;

async function loadExpenseChart() {
  const res = await fetch(`${BASE_URL}/api/transaction/expense-chart`, {
    credentials: "include"
  });

  if (!res.ok) return;

  const data = await res.json();

  const labels = Object.keys(data);
  const values = Object.values(data);

  const ctx = document.getElementById("expenseChart");

  if (expenseChart) {
    expenseChart.destroy();
  }

expenseChart = new Chart(ctx, {
  type: "pie",
  data: {
    labels,
    datasets: [{
      data: values,
      backgroundColor: [
        "#bfbfbf", // Other
        "#ff6b4a", // Food
        "#e11d48", // EMI
        "#22c55e", // Investment
        "#facc15"  // Fuel
      ],
      borderWidth: 0
    }]
  },
  options: {
    responsive: true,
    maintainAspectRatio: false, // 🔥 IMPORTANT
    plugins: {
      legend: {
        position: "top",
        labels: {
          color: "#ffffff",
          usePointStyle: true,
          padding: 14
        }
      }
    }
  }
});

}
function updatePaginationButtons(data) {
  document.getElementById("pageInfo").innerText =
    `Page ${data.number + 1} of ${data.totalPages}`;

  document.querySelector(".pagination button:first-child").disabled = data.first;
  document.querySelector(".pagination button:last-child").disabled = data.last;
}

function nextPage() {
  currentPage++;
  loadTransactions();
}

function prevPage() {
  if (currentPage > 0) {
    currentPage--;
    loadTransactions();
  }
}

