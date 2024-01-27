package com.francesco.ecommercespring.controller;

import com.francesco.ecommercespring.support.authentication.Utils;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.web.bind.annotation.*;



@RestController
@CrossOrigin
@RequestMapping("/home")
public class HomeController {
//restituisce il messaggio di benvenutp
    @GetMapping("/user")
    @PreAuthorize("hasAuthority('Utente')")
    public ResponseEntity homeUser(@AuthenticationPrincipal Authentication authentication) {
        String message = "Benvenuto " + Utils.getUsername() +"!";
        return ResponseEntity.ok(message);
    }

    @GetMapping("/admin")
    @PreAuthorize("hasAuthority('Admin')")
    public ResponseEntity<String> homeAdmin(@AuthenticationPrincipal Authentication authentication) {
        String message = "Benvenuto come admin " + Utils.getUsername() + "!";
        System.out.println();
        return new ResponseEntity(message, HttpStatus.OK);
    }
}


//utils .getemail preleva dal token l indirizzo email