package com.proyectoCuotasRyR.proyectoCuotas.controllers;

import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Localidad;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Plan_Pago;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Provincia;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.ClienteService;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_GeoService;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Plan_Pago_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.Tipo_DocumentoService;

@Controller
@RequestMapping("/escenarios")
@SessionAttributes("cliente")
public class escenarioController {

	@Autowired
	private I_Plan_Pago_Service plan_pago_Service;
	
	@Autowired
	private I_Usuario_Repo usuarioRepo;
	
	@Autowired
	private Tipo_DocumentoService tipo_documento_Service;
	
	@Autowired
	private I_GeoService geoService;
	
	@Autowired
	private ClienteService cliente_Service;
	
	@GetMapping(value = "/provincias", produces = { "application/json" })
	public @ResponseBody List<Provincia> provincias() {
		return geoService.provincias();
	}

	@GetMapping(value = "/localidades", produces = { "application/json" })
	public @ResponseBody List<Localidad> localidades(@RequestParam String id_provincia) {
		return geoService.buscarPorId(Integer.valueOf(id_provincia)).getLocalidades();
	}
	
	@GetMapping("/seleccionar_opcion/{id_plan_pago}")
	public String seleccionarOpcion(Model model, @PathVariable(name="id_plan_pago") long id_plan_pago) {
		
		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}
		
		model.addAttribute("usuario", obtenerUsuario());
		model.addAttribute("plan_pago", plan_pago_Service.buscarPorId(id_plan_pago));
		
		model.addAttribute("notificaciones", plan_pago_Service.listarTodo());
		
		return "escenarios/seleccionar_opcion";
	}
	
	@GetMapping("clientes/formulario/{id_plan_pago}")
	public String formulario(Model model, @PathVariable(name="id_plan_pago") long id_plan_pago) {
		
		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}
		
		model.addAttribute("plan_pago", plan_pago_Service.buscarPorId(id_plan_pago));
		
		Cliente cliente = new Cliente();
		
		model.addAttribute("tipos_documentos", tipo_documento_Service.listarTodo());
		model.addAttribute("usuario", obtenerUsuario());
		
		model.addAttribute("cliente", cliente);
		
		model.addAttribute("notificaciones", plan_pago_Service.listarTodo());
		
		return "escenarios/clientes/formulario";
	}
	
	@PostMapping("clientes/formulario")
	public String guardar(@Valid Cliente cliente, RedirectAttributes redirAttrs) {
		
		//cliente_Service.guardar(cliente);
		

		return "redirect:/clientes/listar";
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
