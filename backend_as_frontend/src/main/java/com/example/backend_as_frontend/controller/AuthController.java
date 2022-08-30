package com.example.backend_as_frontend.controller;

import com.example.backend_as_frontend.dto.LoginDTO;
import com.example.backend_as_frontend.dto.ResetPasswordDTO;
import com.example.backend_as_frontend.dto.SessionDTO;
import com.example.backend_as_frontend.service.AuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
public class AuthController {


    private final AuthService service;

    @Autowired
    public AuthController(AuthService service) {
        this.service = service;
    }

    @GetMapping("/login")
    public String loginForm(Model model) {
        model.addAttribute("user", new LoginDTO());
        return "login";
    }


    @PostMapping("/login")
    public String login(LoginDTO dto, HttpServletResponse response, HttpServletRequest request) {
        SessionDTO sessionDTO = service.login(dto);

        Cookie accessToken = new Cookie("ACCESSTOKEN", sessionDTO.getAccessToken());
        accessToken.setMaxAge(86400);
        accessToken.setSecure(true);
        accessToken.setHttpOnly(true);
        response.addCookie(accessToken);

        Cookie refreshToken = new Cookie("REFRESHTOKEN", sessionDTO.getRefreshToken());
        refreshToken.setMaxAge(86400);
        refreshToken.setSecure(true);
        refreshToken.setHttpOnly(true);
        response.addCookie(refreshToken);

        return "redirect:/";
    }

    @GetMapping("/forget-password")
    public String forgetPassword(Model model) {
        return "forget-password";
    }

    @PostMapping("/send-code")
    public String forgetPassword(@RequestParam String username) {
        service.sendCode(username);
        return "check-email";
    }

    @GetMapping("/reset-password")
    public String getResetPasswordPage(@RequestParam String token, Model model) {
        model.addAttribute("resetPassword", new ResetPasswordDTO());
        model.addAttribute("token", token);
        return "reset-password";
    }

    @PostMapping("/reset-password")
    public String resetPassword(ResetPasswordDTO dto, @RequestParam String token) {
        service.resetPassword(dto, token);
        return "redirect:/login";
    }

    @GetMapping("/logout")
    public String logout(HttpServletResponse response) {
        service.logout(response);
        return "redirect:/login";
    }


}
