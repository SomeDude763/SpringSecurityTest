package org.example.springsecuritytest.controllers;

import org.example.springsecuritytest.security.PersonDetails;
import org.example.springsecuritytest.services.AdminService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class HelloController {
    private final AdminService adminService;

    @Autowired
    public HelloController(AdminService adminService) {
        this.adminService = adminService;
    }

    @GetMapping("/hello")
    public String sayHello() {
        return "hello";
    }

    @GetMapping("/showUserInfo")
    public String showUserInfo() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();// получаем объект
        // Authentication из контекста безопасности
        // в нём хранится информация о текущем пользователе
        PersonDetails personDetails = (PersonDetails) authentication.getPrincipal();// получаем principal
        // (это текущий пользователь)
        // приводим его к своему классу PersonDetails
        System.out.println(personDetails.getPerson()); // выводим в консоль объект Person (данные пользователя из БД)
        return "hello";
    }

    @GetMapping("/admin")
    public String adminPage() {
        adminService.doAdminStuff();//2ой способ для роли
        return "admin";
    }
}
