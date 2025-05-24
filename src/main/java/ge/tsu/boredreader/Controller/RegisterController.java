// RegisterController.java
package ge.tsu.boredreader.Controller;

import ge.tsu.boredreader.Service.UserService;
import ge.tsu.boredreader.form.RegisterForm;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class RegisterController {

    private final UserService userService;

    public RegisterController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/register")
    public String register(Model model) {
        model.addAttribute("registerForm", new RegisterForm());
        return "register";
    }

    @PostMapping("/register")
    public String registerSubmit(@ModelAttribute("registerForm") @Valid RegisterForm registerForm,
                                 BindingResult result,
                                 RedirectAttributes redirectAttributes) {

        if (!registerForm.isPasswordMatching()) {
            result.rejectValue("confirmPassword", "error.password.mismatch", "Passwords do not match");
        }

        if (userService.existsUsername(registerForm.getUsername())) {
            result.rejectValue("username", "error.user.exists", "Username is already taken");
        }

        if (result.hasErrors()) {
            return "register";
        }

        try {
            userService.register(registerForm.getUsername(), registerForm.getPassword());

            redirectAttributes.addAttribute("success", true);
            return "redirect:/login";

        } catch (Exception e) {
            result.reject("error.registration.failed", "Registration failed. Please try again.");
            return "register";
        }
    }
}