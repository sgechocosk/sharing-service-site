document.addEventListener("DOMContentLoaded", () => {
  const dialog = document.getElementById("registerDialog");
  const confirmBtn = document.getElementById("confirmRegister");
  const cancelBtn = document.getElementById("cancelRegister");

  let targetForm = null;

  document.querySelectorAll(".register-form").forEach((form) => {
    form.addEventListener("submit", (event) => {
      event.preventDefault();
      targetForm = form;
      dialog.classList.remove("hidden");
    });
  });

  confirmBtn.addEventListener("click", () => {
    if (!targetForm) return;

    const userName = document.getElementById("dialogUserName").value.trim();
    const deptName = document.getElementById("dialogDepartment").value.trim();

    if (userName === "" || deptName === "") {
      alert("ユーザー名と所属部署を入力してください。");
      return;
    }

    const userInput = document.createElement("input");
    userInput.type = "hidden";
    userInput.name = "userName";
    userInput.value = userName;

    const deptInput = document.createElement("input");
    deptInput.type = "hidden";
    deptInput.name = "departmentName";
    deptInput.value = deptName;

    targetForm.appendChild(userInput);
    targetForm.appendChild(deptInput);

    targetForm.submit();
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

window.addEventListener("DOMContentLoaded", () => {
  const toast = document.getElementById("toast");
  if (toast) {
    toast.classList.add("show");
    setTimeout(() => {
      toast.classList.remove("show");
    }, 3000);
  }
});
