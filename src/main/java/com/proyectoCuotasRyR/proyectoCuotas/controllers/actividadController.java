package com.proyectoCuotasRyR.proyectoCuotas.controllers;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario_Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Empresa_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Plan_Pago_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Sucursal_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Usuario_Sucursal_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.UsuarioService;

@Controller
@RequestMapping("actividades")
public class actividadController {
	
	@Autowired
	private I_Empresa_Service empresaService;
	
	@Autowired
	private I_Usuario_Repo usuarioRepo;
	
	@Autowired
	private UsuarioService usuarioService;
	
	
	@Autowired
	private I_Usuario_Sucursal_Service usuarioSucursalService;
	
	@Autowired
	private I_Sucursal_Service sucursalService;
	
	@Autowired
	private I_Plan_Pago_Service planPagoService;
	

	@GetMapping("/actividad")
	public String actividad(Model model,
			RedirectAttributes redirectAttrs) {
		
		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}
		
		model.addAttribute("usuario", obtenerUsuario());
		
		if (empresaService.listar_todo().size() == 0 || sucursalService.listar().size() == 0 || obtenerUsuario().getUsuarios_sucursales().size() == 0) {
			redirectAttrs.addFlashAttribute("error", "Para comenzar a operar en el Sistema debe 1) Tener una empresa registrada, 2) Tener una sucursal central registrada y 3) Tener su usuario asignado a una sucursal."
					+ " Consulte Manual del Usuario ubicado en la parte inferior de la página.");
			return "redirect:/";
		}
				
		
		model.addAttribute("empresa", empresaService.listar_todo().get(0));
		
		model.addAttribute("actividades", usuarioSucursalService.buscarPorUsuario(obtenerUsuario()).get(0).getActividades()); 
		
		model.addAttribute("notificaciones", planPagoService.listarTodo());
		
		return "actividades/actividad";
	}
	
	@GetMapping("/actividad/{id_usuario}")
	public String actividad(Model model, @PathVariable long id_usuario, RedirectAttributes redirectAttrs) {
		
		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}
		
		if(usuarioService.buscarPorId(id_usuario) == null) {
			redirectAttrs.addFlashAttribute("error", "No existe un usuario con este ID");
			return "redirect:/";
		}
		
		if (empresaService.listar_todo().size() == 0 || sucursalService.listar().size() == 0 || obtenerUsuario().getUsuarios_sucursales().size() == 0) {
			redirectAttrs.addFlashAttribute("error", "Para comenzar a operar en el Sistema debe 1) Tener una empresa registrada, 2) Tener una sucursal central registrada y 3) Tener su usuario asignado a una sucursal."
					+ " Consulte Manual del Usuario ubicado en la parte inferior de la página.");
			return "redirect:/";
		}
				
		
		Usuario usuario = usuarioService.buscarPorId(id_usuario);
	
		
		
		model.addAttribute("empresa", empresaService.listar_todo().get(0));
		
		model.addAttribute("actividades", usuarioSucursalService.buscarPorUsuario(usuario).get(0).getActividades()); 
		
		model.addAttribute("usuario", obtenerUsuario());
		
		model.addAttribute("notificaciones", planPagoService.listarTodo());
		
		return "actividades/actividad";
	}
	
	private Usuario obtenerUsuario() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		System.out.println(userDetail.getUsername());

		return usuarioRepo.findByUsername(userDetail.getUsername());
	}

	
}
