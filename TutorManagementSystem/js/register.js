const inputs = document.querySelectorAll(".input");

function addcl() {
    let parent = this.parentNode.parentNode;
    parent.classList.add("focus");
}

function remcl() {
    let parent = this.parentNode.parentNode;
    if (this.value === "") {
        parent.classList.remove("focus");
    }
}

inputs.forEach(input => {
    input.addEventListener("focus", addcl);
    input.addEventListener("blur", remcl);
});

// Specific functionality for the registration page
const registerForm = document.querySelector('form'); // Replace with the selector for your registration form

if (registerForm) {
    registerForm.addEventListener('submit', function (event) {
        event.preventDefault(); // Prevent the default form submission

        // Get the values of registration form fields
        const username = registerForm.querySelector('.username-input').value;
        const email = registerForm.querySelector('.email-input').value;
        const password = registerForm.querySelector('.password-input').value;
        const confirmPassword = registerForm.querySelector('.confirm-password-input').value;

        // Perform form validation
        if (username === '' || email === '' || password === '' || confirmPassword === '') {
            alert('Please fill in all fields.');
        } else if (password !== confirmPassword) {
            alert('Password and confirm password do not match.');
        } else {
            // Form validation passed, you can submit the form or perform other actions
            alert('Registration successful!');
            // Here, you can add actual form submission code or other actions
        }
    });
}
