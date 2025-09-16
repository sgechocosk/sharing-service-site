document.addEventListener("DOMContentLoaded", () => {
  const container = document.getElementById("departmentContainer");
  const title = document.getElementById("companyTitle");
  if (!container || !title) return;

  container.addEventListener("click", async (event) => {
    const card = event.target.closest(".department-card");
    if (!card) return;

    const departmentId = card.dataset.id;
    const departmentName = card.textContent;

    title.textContent = departmentName;

    const response = await fetch(`/departments/${departmentId}`);
    const childDepartments = await response.json();

    container.innerHTML = "";

    childDepartments.forEach((dept) => {
      const div = document.createElement("div");
      div.className = "department-card";
      div.dataset.id = dept.departmentId;
      div.textContent = dept.departmentName;
      container.appendChild(div);
    });
  });
});
