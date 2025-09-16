document.addEventListener("DOMContentLoaded", () => {
  const sidebar = document.getElementById("sidebar");
  const overlay = document.querySelector(".overlay");
  const mainContent = document.querySelector(".main-content");
  const toggleBtn = document.getElementById("menuToggle");

  if (!sidebar || !overlay || !toggleBtn) return;

  function openSidebar() {
    sidebar.classList.add("expanded");
    overlay.classList.add("active");
    mainContent.classList.add("shifted");
  }

  function closeSidebar() {
    sidebar.classList.remove("expanded");
    overlay.classList.remove("active");
    mainContent.classList.remove("shifted");
  }

  toggleBtn.addEventListener("click", () => {
    const isOpen = sidebar.classList.contains("expanded");
    if (isOpen) {
      closeSidebar();
    } else {
      openSidebar();
    }
  });

  overlay.addEventListener("click", closeSidebar);
});
