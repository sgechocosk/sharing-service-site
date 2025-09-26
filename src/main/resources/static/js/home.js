document.addEventListener("DOMContentLoaded", () => {
  const title = document.getElementById("companyTitle");
  const breadcrumb = document.getElementById("breadcrumb");
  const container = document.getElementById("departmentContainer");
  const departmentInput = document.getElementById("selectedDepartmentId");

  if (!title || !breadcrumb || !container) return;

  const companyName = title.textContent.trim();
  let breadcrumbStack = [{ id: null, name: companyName }];

  function updateDepartmentInput() {
    const selectedDepartment = breadcrumbStack[breadcrumbStack.length - 1];
    departmentInput.value = selectedDepartment.id || "";
    const messageButton = document.getElementById("messageButton");
    if (messageButton) {
      messageButton.style.display = selectedDepartment.id ? "inline" : "none";
    }
  }

  function renderBreadcrumb() {
    breadcrumb.innerHTML = "";

    breadcrumbStack.forEach((item, index) => {
      const span = document.createElement("span");
      span.textContent = item.name;
      span.classList.add("breadcrumb-item");

      if (index < breadcrumbStack.length - 1) {
        span.classList.add("breadcrumb-link");
        if (index === 0) {
          span.classList.add("breadcrumb-company");
        }
        span.addEventListener("click", async () => {
          breadcrumbStack = breadcrumbStack.slice(0, index + 1);

          title.textContent = item.name;

          if (index === 0) {
            await fetchDepartments(null);
          } else {
            await fetchDepartments(item.id);
          }
          renderBreadcrumb();
        });
      }

      breadcrumb.appendChild(span);
    });

    updateDepartmentInput();
  }

  async function fetchDepartments(departmentId = null) {
    const url = departmentId ? `/departments/${departmentId}` : `/departments`;
    const response = await fetch(url);
    const departments = await response.json();

    container.innerHTML = "";

    departments.forEach((dept) => {
      const div = document.createElement("div");
      div.className = "department-card";
      div.dataset.id = dept.departmentId;
      div.textContent = dept.departmentName;
      container.appendChild(div);
    });
  }

  container.addEventListener("click", async (event) => {
    const card = event.target.closest(".department-card");
    if (!card) return;

    const departmentId = card.dataset.id;
    const departmentName = card.textContent;

    breadcrumbStack.push({ id: departmentId, name: departmentName });
    renderBreadcrumb();

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

  renderBreadcrumb();
});
