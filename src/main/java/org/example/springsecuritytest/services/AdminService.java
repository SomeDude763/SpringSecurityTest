package org.example.springsecuritytest.services;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Service;

@Service
public class AdminService {
@PreAuthorize("hasRole('ROLE_ADMIN')")//Аннотация для доступа только админу 2ой способ
    public void doAdminStuff() {
        System.out.println("Only admin here");
    }
}
