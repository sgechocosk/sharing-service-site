// document.querySelectorAll(".role-form").forEach((form) => {
//   form.addEventListener("submit", async function (event) {
//     event.preventDefault();

//     const formData = new FormData(form);

//     const response = await fetch(form.action, {
//       method: "POST",
//       body: formData,
//     });

//     if (response.ok) {
//       const result = await response.json();
//       alert(`ロールが「${result.newRoleName}」に変更されました。`);
//     } else {
//       alert("ロール変更に失敗しました。");
//     }
//   });
// });
