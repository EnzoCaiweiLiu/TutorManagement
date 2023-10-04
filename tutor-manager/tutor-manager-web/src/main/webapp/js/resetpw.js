// 获取密码输入框元素
const passwordInput = document.querySelector('#password');
const confirmPasswordInput = document.querySelector('#confirm-password');

// 获取密码输入框的提示标签元素
const passwordLabel = document.querySelector('.password-label');
const confirmPasswordLabel = document.querySelector('.confirm-password-label');
const resetPasswordButton = document.querySelector('.btn');

// 添加密码输入事件监听器
passwordInput.addEventListener('input', () => {
    if (passwordInput.value) {
        passwordLabel.classList.add('active');
    } else {
        passwordLabel.classList.remove('active');
    }
});

confirmPasswordInput.addEventListener('input', () => {
    if (confirmPasswordInput.value) {
        confirmPasswordLabel.classList.add('active');
    } else {
        confirmPasswordLabel.classList.remove('active');
    }
});

resetPasswordButton.addEventListener('click', () => {
  // 在用户点击按钮时，将页面重定向到 login.html
  window.location.href = 'login.html';
});