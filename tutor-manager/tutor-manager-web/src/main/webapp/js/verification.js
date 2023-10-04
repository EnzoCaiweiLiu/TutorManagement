document.addEventListener("DOMContentLoaded", function () {
    const verificationForm = document.getElementById("verification-form");

    verificationForm.addEventListener("submit", function (event) {
        event.preventDefault(); // 阻止表单默认提交行为

        // 获取验证码输入框的值
        const verificationCode = document.querySelector('input[name="verification_code"]').value;

        // 在此添加验证验证码的逻辑，如果验证成功，则执行页面跳转
        // 例如，您可以使用 AJAX 发送验证请求，验证成功后执行以下页面跳转代码

        window.location.href = "resetpw.html"; // 执行页面跳转
    });
});