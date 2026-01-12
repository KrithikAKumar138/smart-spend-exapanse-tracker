const BASE_URL = "http://localhost:6081";
let budgetChart;


/* ======================
   LOAD BUDGET PAGE
====================== */
document.addEventListener("DOMContentLoaded", () => {
  loadBudget();
});

/* ======================
   MAIN BUDGET LOGIC
====================== */
async function loadBudget() {
    
  // 1️⃣ Expense by category
  const expenseRes = await fetch(`${BASE_URL}/api/transaction/expense-chart`, {
    credentials: "include"
  });

  if (!expenseRes.ok) return;

  const expenses = await expenseRes.json();

  // 2️⃣ Summary
  const summaryRes = await fetch(`${BASE_URL}/api/transaction/summary`, {
    credentials: "include"
  });

  const summary = await summaryRes.json();

  let totalExpense = summary.expense;
  let totalIncome = summary.income;

  // 3️⃣ Budget = total expense (for now)
// 3️⃣ Budget (user-defined)
let savedBudget = localStorage.getItem("monthlyBudget");
let totalBudget = savedBudget ? Number(savedBudget) : 0;
let remaining = totalBudget - totalExpense;


  document.getElementById("income").innerText = totalIncome;
  document.getElementById("budget").innerText = totalBudget;
  document.getElementById("remaining").innerText = remaining;
  document.getElementById("totalUsage").innerText = `₹ ${totalExpense}`;
  document.getElementById("budgetInput").value = totalBudget || "";


  renderBudgetChart(totalExpense, totalBudget);
  renderCategoryBudgets(expenses);
}

/* ======================
   CATEGORY CARDS
====================== */
function renderCategoryBudgets(expenses) {

  const container = document.getElementById("categoryBudgets");
  container.innerHTML = "";

  Object.keys(expenses).forEach(category => {

    const used = expenses[category];

    const card = document.createElement("div");
    card.className = "card-box expense";

    card.innerHTML = `
      <span>${category}</span>
      <h2>₹ ${used}</h2>
      <small>Used</small>
    `;

    container.appendChild(card);
  });
}

/* ======================
   BUDGET USAGE CHART
====================== */
function renderBudgetChart(used, total) {

  const ctx = document.getElementById("budgetChart");

  if (budgetChart) budgetChart.destroy();

  budgetChart = new Chart(ctx, {
    type: "doughnut",
    data: {
      labels: ["Used", "Remaining"],
      datasets: [{
        data: [used, Math.max(total - used, 0)],
        backgroundColor: ["#ef4444", "#1f2937"],
        borderWidth: 0
      }]
    },
    options: {
      cutout: "80%",
      plugins: {
        legend: { display: false }
      }
    }
  });
}
function saveBudget() {
  const amount = document.getElementById("budgetInput").value;

  if (!amount || amount <= 0) {
    alert("Enter valid budget amount");
    return;
  }

  // Save budget locally (later we move to backend)
  localStorage.setItem("monthlyBudget", amount);

  closeBudgetModal();   // ✅ ADD THIS
  loadBudget();  // 🔥 refresh UI
}
function openBudgetModal() {
  document.getElementById("budgetModal").classList.remove("hidden");
  document.getElementById("budgetOverlay").classList.remove("hidden");
}

function closeBudgetModal() {
  document.getElementById("budgetModal").classList.add("hidden");
  document.getElementById("budgetOverlay").classList.add("hidden");
}
