class RegisterFormHandler {
    constructor() {
        this.form = document.querySelector('.login-form');
        if (!this.form) return;

        this.usernameInput = document.getElementById('floatingUsername');
        this.passwordInput = document.getElementById('floatingPassword');
        this.confirmPasswordInput = document.getElementById('floatingConfirmPassword');
        this.termsCheck = document.getElementById('termsCheck');

        this.form.addEventListener('submit', (e) => this.handleSubmit(e));
    }

    handleSubmit(event) {
        if (!this.validateForm()) {
            event.preventDefault();
        }
    }

    validateForm() {
        let valid = true;
        valid = this.validateUsername() && valid;
        valid = this.validatePassword() && valid;
        valid = this.validateConfirmPassword() && valid;
        return valid;
    }

    validateUsername() {
        const username = this.usernameInput.value.trim();
        const min = 3, max = 50;

        if (!username) {
            this.showError(this.usernameInput, 'Username is required');
            return false;
        }
        if (username.length < min || username.length > max) {
            this.showError(this.usernameInput, `Username must be ${min}-${max} characters`);
            return false;
        }
        if (!/^[a-zA-Z0-9_]+$/.test(username)) {
            this.showError(this.usernameInput, 'Only letters, numbers, and underscores allowed');
            return false;
        }
        this.clearError(this.usernameInput);
        return true;
    }

    validatePassword() {
        const password = this.passwordInput.value;
        if (password.length < 6) {
            this.showError(this.passwordInput, 'Password must be at least 6 characters');
            return false;
        }
        this.clearError(this.passwordInput);
        return true;
    }

    validateConfirmPassword() {
        if (this.passwordInput.value !== this.confirmPasswordInput.value) {
            this.showError(this.confirmPasswordInput, 'Passwords must match');
            return false;
        }
        this.clearError(this.confirmPasswordInput);
        return true;
    }



    showError(input, message) {
        const container = input.closest('.form-floating, .form-check');
        input.classList.add('is-invalid');

        let errorDiv = container.querySelector('.invalid-feedback');
        if (!errorDiv) {
            errorDiv = document.createElement('div');
            errorDiv.className = 'invalid-feedback';
            container.appendChild(errorDiv);
        }
        errorDiv.textContent = message;
    }

    clearError(input) {
        const container = input.closest('.form-floating, .form-check');
        input.classList.remove('is-invalid');
        const errorDiv = container?.querySelector('.invalid-feedback');
        if (errorDiv) errorDiv.textContent = '';
    }
}

document.addEventListener('DOMContentLoaded', () => new RegisterFormHandler());