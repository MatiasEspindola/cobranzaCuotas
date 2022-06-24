package com.proyectoCuotasRyR.proyectoCuotas.controllers;

import java.security.Principal;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
public class loginController {


	@GetMapping("/login")
	public String login(
			@RequestParam(value="error", required=false) String error,
			@RequestParam(value="logout", required = false) String logout,
			@RequestParam(value="info", required = false) String info,
			Model model, Principal principal, RedirectAttributes flash) {
		
		if(principal != null) {
			
			flash.addFlashAttribute("info", "Ya ha inciado sesión anteriormente");
			
			return "redirect:/";
		}
		
		if(error != null) {
			model.addAttribute("error", "Error: Nombre de usuario o contraseña incorrecta");
		}
		
		if(logout != null) {
			model.addAttribute("success", "Ha cerrado sesión con éxito!");
		}
	
		
		return "login";
	}
	
}
