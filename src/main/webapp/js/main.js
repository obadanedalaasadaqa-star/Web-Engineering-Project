document.addEventListener('DOMContentLoaded', function () {
  document.querySelectorAll('.alert').forEach(function (el) {
    setTimeout(function () {
      el.style.opacity = '0';
      el.style.transition = 'opacity 0.3s';
      setTimeout(function () { el.remove(); }, 300);
    }, 4000);
  });
});
