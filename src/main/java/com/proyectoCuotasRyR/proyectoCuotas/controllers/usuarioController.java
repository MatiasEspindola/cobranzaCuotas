package com.proyectoCuotasRyR.proyectoCuotas.controllers;

import java.net.MalformedURLException;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Empresa_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_UploadFile_Service;

@Controller
@SessionAttributes("usuario")
@RequestMapping("/usuarios")
public class usuarioController {
	
	@Autowired
	private I_Empresa_Service empresaService;
	
	@Autowired
	private I_Usuario_Repo usuarioRepo;
	
	@Autowired
    private I_UploadFile_Service upl;
	
	@GetMapping(value = "/uploads/{filename:.+}")
    public ResponseEntity<Resource> verFoto(@PathVariable String filename) {

        Resource recurso = null;

        try {
            recurso = upl.load(filename);
        } catch (MalformedURLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=\"" + recurso.getFilename() + "\"")
                .body(recurso);
    }
	
	@GetMapping("/listar")
	public String listar(Model model) {
		
		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Usuario"))) {
			return "redirect:/401";
		}
		
		if(empresaService.listar_todo().size() == 0) {
			return "redirect:/empresas/registrar"; 
		}
		
		model.addAttribute("empresa", empresaService.listar_todo().get(0));
		model.addAttribute("usuario", obtenerUsuario());
		model.addAttribute("usuarios", usuarioRepo.findAll());
		
		return "usuarios/listar";
	}
	
	@GetMapping("/deshabilitar/{id_usuario}")
	public String listar(Model model, @PathVariable long id_usuario) {
		
		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}
		
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Usuario"))) {
			return "redirect:/401";
		}
		
		if(empresaService.listar_todo().size() == 0) {
			return "redirect:/empresas/registrar"; 
		}
		
		model.addAttribute("empresa", empresaService.listar_todo().get(0));
		
		
		if(usuarioRepo.findById(id_usuario).get().isActivo()) {
			usuarioRepo.findById(id_usuario).get().setActivo(false);
		}else {
			usuarioRepo.findById(id_usuario).get().setActivo(true);
		}
		
		usuarioRepo.save(usuarioRepo.findById(id_usuario).get());
		
		
		return "redirect:/usuarios/listar";
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
