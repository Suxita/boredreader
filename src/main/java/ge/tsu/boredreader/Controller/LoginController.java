package ge.tsu.boredreader.Controller;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
public class LoginController {

    @GetMapping("/login")
    public String login(@RequestParam(value = "error", required = false) String error,
                        @RequestParam(value = "logout", required = false) String logout,
                        @RequestParam(value = "success", required = false) String success,
                        Model model) {


        if (error != null) {
            model.addAttribute("errorMessage", "Invalid username or password");
        }

        if (logout != null) {
            model.addAttribute("logoutMessage", "You have been logged out successfully");
        }

        if (success != null) {
            model.addAttribute("successMessage", "Registration successful! Please log in.");
        }

        return "login";
    }
}