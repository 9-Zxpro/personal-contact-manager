package com.jiba.pcm.controller;

import com.jiba.pcm.model.User;
import com.jiba.pcm.request.RegisterUser;
import com.jiba.pcm.service.user.IUserService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.ObjectError;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

@Controller
public class PageController {
    @Autowired
    private IUserService userService;

    @RequestMapping("/home")
    public String home(Model model) {
        System.out.println("home page hai");
        model.addAttribute("pageName", "This is jiba.com");
        model.addAttribute("github", "https://github.com/9-zxpro");
        return "index";
    }
    @RequestMapping("/about")
    public String about() {
        return "about";
    }
    @RequestMapping("/contact")
    public String contact() {
        return "contact";
    }

    @GetMapping("/login")
    public String login() {
        return "login";
    }
    @GetMapping("/register")
    public String register(Model model) {
        RegisterUser registerUser = new RegisterUser();
        model.addAttribute("registerUser", registerUser);
        return "register";
    }

    @RequestMapping(value = "/user-register", method = RequestMethod.POST)
    public String userRegister(@Valid @ModelAttribute RegisterUser registerUser, BindingResult bindingResult) {
        String username = registerUser.getUsername();
        if(username!=null) {
            boolean isUsernameExist = userService.geUserByUserName(username);
            if (isUsernameExist) {
                ObjectError objectError = new ObjectError("ERROR", "Username already exists");
                bindingResult.addError(objectError);
            }
        }
        if(bindingResult.hasErrors()) {
            return "register";
        }
        System.out.println(registerUser.getEmail());
        User user = User.builder()
                .username(registerUser.getUsername())
                .firstname(registerUser.getFirstName())
                .lastname(registerUser.getLastName())
                .phoneNumber(registerUser.getPhoneNumber())
                .email(registerUser.getEmail())
                .password(registerUser.getPassword())
                .about(registerUser.getAbout())
                .termsAndPrivacy(registerUser.isPrivacyTerms())
                .build();
        User savedUser = userService.saveUser(user);
        System.out.println(savedUser.getEmail());

        return "redirect:/register";
    }

}
