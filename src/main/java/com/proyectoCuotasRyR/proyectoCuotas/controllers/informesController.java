package com.proyectoCuotasRyR.proyectoCuotas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.ClienteService;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Actividad_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Empresa_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Sucursal_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Usuario_Sucursal_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.Plan_Pago_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.ProveedorService;

@Controller
public class informesController {
	

	@Autowired
	private I_Usuario_Repo usuarioRepo;

	@Autowired
	private I_Empresa_Service empresaService;
	
	@Autowired
	private I_Usuario_Sucursal_Service usuarioSucursalService;
	

	@Autowired
	private ProveedorService proveedor_Service;
	
	@Autowired
	private ClienteService cliente_Service;
	
	@Autowired
	private Plan_Pago_Service planPagoService;
	
	@Autowired
	private I_Sucursal_Service sucursalService;
	

	@Autowired
	private I_Actividad_Service actividadService;
	
	@PreAuthorize("hasAuthority('Admin')")
	@GetMapping("/informes")
	public String informes(Model model, RedirectAttributes redirectAttrs) {
		
		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}
		
		if (empresaService.listar_todo().size() == 0 || sucursalService.listar().size() == 0 || obtenerUsuario().getUsuarios_sucursales().size() == 0) {
			redirectAttrs.addFlashAttribute("error", "Para comenzar a operar en el Sistema debe 1) Tener una empresa registrada, 2) Tener una sucursal central registrada y 3) Tener su usuario asignado a una sucursal."
					+ " Consulte Manual del Usuario ubicado en la parte inferior de la página.");
			return "redirect:/";
		}
		
		Usuario usuario = obtenerUsuario();
		model.addAttribute("empresa", empresaService.listar_todo().get(0));
		model.addAttribute("usuario", usuario);
		
		return "informes";
	}
	
	private Usuario obtenerUsuario() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		System.out.println(userDetail.getUsername());

		return usuarioRepo.findByUsername(userDetail.getUsername());
	}


}
