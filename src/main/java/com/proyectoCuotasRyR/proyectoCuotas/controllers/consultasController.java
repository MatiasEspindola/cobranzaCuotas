package com.proyectoCuotasRyR.proyectoCuotas.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Plan_Pago;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Plan_Pago;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.ClienteService;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Actividad_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Empresa_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Historial_Plan_Pago_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Historial_Recibo_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Historial_Sucursal_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Recibo_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Sucursal_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Usuario_Sucursal_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.Plan_Pago_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.ProveedorService;

@Controller
public class consultasController {
	
	@Autowired
	private I_Recibo_Service reciboService;

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
	private I_Historial_Plan_Pago_Service historialPlanPagoService;
	
	@Autowired
	private I_Historial_Recibo_Service historialReciboService;
	
	@Autowired
	private I_Historial_Sucursal_Service historialSucursalService;
	

	@Autowired
	private I_Actividad_Service actividadService;
	
	@PreAuthorize("hasAuthority('Admin')")
	@GetMapping("/caja")
	public String caja(Model model, RedirectAttributes redirectAttrs) {
		
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
		
		model.addAttribute("notificaciones", planPagoService.listarTodo());
		
		return "caja";
	}
	
	@PreAuthorize("hasAuthority('Admin')")
	@GetMapping("/informes_sucursales")
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
		model.addAttribute("historial_sucursal", historialSucursalService.listarTodo());
		
		model.addAttribute("notificaciones", planPagoService.listarTodo());
		
		
		
		return "informes_sucursales";
	}
	
	@PreAuthorize("hasAuthority('Admin')")
	@GetMapping("/informes_planes_pagos")
	public String planes_pagos(Model model, RedirectAttributes redirectAttrs) {
		
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
		model.addAttribute("historial_plan_pago", historialPlanPagoService.listar());
		
		int completado = 0;
		int normal = 0;
		int riesgo_bajo = 0;
		int riesgo_medio = 0;
		int riesgo_alto = 0;
		int irrecuperable = 0;
		
		if(historialPlanPagoService.listar().size() > 0) {
			for(Historial_Plan_Pago historial : historialPlanPagoService.listar()) {
				Plan_Pago plan_pago = historial.getPlan_pago();
				
				if(plan_pago.isCompletado()) {
					completado++;
				}else if(plan_pago.isNormal()) {
					normal++;
				}else if(plan_pago.isRiesgo_bajo()) {
					riesgo_bajo++;
				}else if(plan_pago.isRiesgo_medio()) {
					riesgo_medio++;
				}else if(plan_pago.isRiesgo_alto()) {
					riesgo_alto++;
				}else if(plan_pago.isIrrecuperable()) {
					irrecuperable++;
				}
				
			}
		}
		
		model.addAttribute("completado", completado);
		model.addAttribute("normal", normal);
		model.addAttribute("riesgo_bajo", riesgo_bajo);
		model.addAttribute("riesgo_medio", riesgo_medio);
		model.addAttribute("riesgo_alto", riesgo_alto);
		model.addAttribute("irrecuperable", irrecuperable);
		
		model.addAttribute("notificaciones", planPagoService.listarTodo());
		
		
		return "informes_planes_pagos";
	}
	

	@PreAuthorize("hasAuthority('Admin')")
	@GetMapping("/recibos")
	public String recibos(Model model, RedirectAttributes redirectAttrs) {
		
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
		model.addAttribute("recibos", reciboService.listar());
		model.addAttribute("historial_recibos", historialReciboService.listarTodo());
		model.addAttribute("notificaciones", planPagoService.listarTodo());	
		
		
		return "recibos";
	}
	/*
	@PreAuthorize("hasAuthority('Admin')")
	@GetMapping("/deudas")
	public String deudas(Model model, RedirectAttributes redirectAttrs) {
		
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
		model.addAttribute("recibos", reciboService.listar());
		
		
		
		return "deudas";
	}
	
	*/
	
	@GetMapping("/deshabilitar/{id_plan_pago}")
	public String eliminar(Model model, @PathVariable(name = "id_plan_pago") long id_plan_pago,
			RedirectAttributes redirectAttrs) {

		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}
		
		if (empresaService.listar_todo().size() == 0 || sucursalService.listar().size() == 0 || obtenerUsuario().getUsuarios_sucursales().size() == 0) {

			redirectAttrs.addFlashAttribute("error", "Para comenzar a operar en el Sistema debe 1) Tener una empresa registrada, 2) Tener una sucursal central registrada y 3) Tener su usuario asignado a una sucursal."
					+ " Consulte Manual del Usuario ubicado en la parte inferior de la página.");
			
			return "redirect:/";
			
			
		}
		
		if (planPagoService.buscarPorId(id_plan_pago) == null) {
			redirectAttrs.addFlashAttribute("error", "No existe un plan de pago con ese ID");
			return "redirect:/";
		}
		
		
		
		Plan_Pago plan_pago = planPagoService.buscarPorId(id_plan_pago);
		
		if(plan_pago.isActivo()) {
			plan_pago.setActivo(false);
		}else {
			plan_pago.setActivo(true);
		}
		
		model.addAttribute("notificaciones", planPagoService.listarTodo());
		
		planPagoService.guardar(planPagoService.buscarPorId(id_plan_pago));

		return "redirect:/planes_pagos";
	}
	
	private Usuario obtenerUsuario() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		System.out.println(userDetail.getUsername());

		return usuarioRepo.findByUsername(userDetail.getUsername());
	}


}
