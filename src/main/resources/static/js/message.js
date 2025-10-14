document.addEventListener("DOMContentLoaded", () => {
  let currentEditingCard = null;

  document.addEventListener("click", (e) => {
    const editBtn = e.target.closest(".edit-btn");
    if (editBtn) {
      const card = editBtn.closest(".message-card");
      toggleEditMode(card);
    }

    const saveBtn = e.target.closest(".save-btn");
    if (saveBtn) {
      const card = saveBtn.closest(".message-card");
      saveEdit(card);
    }

    const cancelBtn = e.target.closest(".cancel-btn");
    if (cancelBtn) {
      const card = cancelBtn.closest(".message-card");
      cancelEdit(card);
    }
  });

  function toggleEditMode(card) {
    if (currentEditingCard && currentEditingCard !== card) {
      cancelEdit(currentEditingCard);
    }

    if (currentEditingCard === card) {
      cancelEdit(card);
      return;
    }

    enterEditMode(card);
  }

  function enterEditMode(card) {
    if (currentEditingCard && currentEditingCard !== card) {
      cancelEdit(currentEditingCard);
    }

    const view = card.querySelector(".message-content-view");
    const edit = card.querySelector(".message-content-edit");
    const textarea = edit.querySelector(".edit-textarea");

    const originalText = view.textContent.trim();
    textarea.value = originalText;

    const lineCount = (originalText.match(/\n/g) || []).length + 1;
    textarea.rows = Math.min(Math.max(lineCount, 2), 20) + 1;

    view.classList.add("hidden");
    edit.classList.remove("hidden");
    textarea.focus();

    currentEditingCard = card;
  }

  function saveEdit(card) {
    const view = card.querySelector(".message-content-view");
    const edit = card.querySelector(".message-content-edit");
    const textarea = edit.querySelector(".edit-textarea");

    const newText = textarea.value.trim();
    if (newText === "") {
      alert("メッセージを空にはできません。");
      return;
    }

    view.classList.remove("hidden");
    edit.classList.add("hidden");

    currentEditingCard = null;
  }

  function cancelEdit(card) {
    const view = card.querySelector(".message-content-view");
    const edit = card.querySelector(".message-content-edit");
    view.classList.remove("hidden");
    edit.classList.add("hidden");

    if (currentEditingCard === card) {
      currentEditingCard = null;
    }
  }
});

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

window.addEventListener("load", () => {
  const container = document.getElementById("mainContent");
  if (scrollToEnd) {
    container.scrollIntoView({ behavior: "instant", block: "end" });
  } else {
    setTimeout(() => {
      container.scrollIntoView({ behavior: "smooth", block: "end" });
    }, 300);
  }
});
