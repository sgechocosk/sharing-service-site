document.addEventListener("DOMContentLoaded", () => {
  const dialog = document.getElementById("confirmDialog");
  const confirmBtn = document.getElementById("confirmDelete");
  const cancelBtn = document.getElementById("cancelDelete");

  let targetForm = null;

  document.querySelectorAll(".delete-form").forEach((form) => {
    form.addEventListener("submit", (event) => {
      event.preventDefault();
      targetForm = form;
      dialog.classList.remove("hidden");
    });
  });

  confirmBtn.addEventListener("click", () => {
    if (targetForm) targetForm.submit();
    dialog.classList.add("hidden");
    targetForm = null;
  });

  cancelBtn.addEventListener("click", () => {
    dialog.classList.add("hidden");
    targetForm = null;
  });

  dialog.addEventListener("click", (event) => {
    if (event.target === dialog) {
      dialog.classList.add("hidden");
      targetForm = null;
    }
  });
});
