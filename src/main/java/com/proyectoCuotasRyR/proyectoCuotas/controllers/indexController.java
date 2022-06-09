package com.proyectoCuotasRyR.proyectoCuotas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;

@Controller
public class indexController {
	
	@Autowired
	private I_Usuario_Repo usuarioRepo;
	
	@GetMapping({"/","/index"})
	public String index(Model model) {
		
		if(obtenerUsuario().getEmpresa() == null) {
			return "redirect:/empresas/formulario";
		}
		
		model.addAttribute("usuario", obtenerUsuario());
		
		return "index";
	}

	private Usuario obtenerUsuario() {

		Authentication auth = SecurityContextHolder
		            .getContext()
		            .getAuthentication();
		   
		UserDetails userDetail = (UserDetails) auth.getPrincipal();
  
		System.out.println(userDetail.getUsername());
		
		return usuarioRepo.findByUsername(userDetail.getUsername());
	}
	
}
