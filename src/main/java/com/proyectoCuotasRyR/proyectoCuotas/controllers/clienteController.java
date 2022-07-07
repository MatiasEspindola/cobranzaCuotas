package com.proyectoCuotasRyR.proyectoCuotas.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.annotation.Secured;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import javax.validation.Valid;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Actividad_Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cliente;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.CtaCteCliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cuota;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Alta_Cliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Localidad;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Plan_Pago;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Proveedor;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Provincia;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.ClienteService;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Actividad_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Cliente_Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_CtaCteCliente_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Empresa_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_GeoService;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Responsable_Iva_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Sucursal_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Tipo_Documento_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_UploadFile_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Usuario_Sucursal_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.Plan_Pago_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.ProveedorService;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.Tipo_DocumentoService;

import java.net.MalformedURLException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Controller
@SessionAttributes("cliente")
@RequestMapping("/clientes")
public class clienteController {

	@Autowired
	private ClienteService cliente_Service;

	@Autowired
	private ProveedorService proveedor_Service;

	@Autowired
	private Tipo_DocumentoService tipo_documento_Service;

	@Autowired
	private Plan_Pago_Service planPagoService;

	@Autowired
	private I_Usuario_Repo usuarioRepo;

	private UserDetails userDetail;

	@Autowired
	private I_GeoService geoService;

	private List<Localidad> localidades;

	@Autowired
	private I_Empresa_Service empresaService;
	
	@Autowired
	private I_Usuario_Sucursal_Service usuarioSucursalService;

	private boolean editar;

	private Cliente cliente;

	private Proveedor proveedor;

	private Plan_Pago plan_pago;

	@Autowired
	private I_CtaCteCliente_Service ctacteclienteService;

	@Autowired
	private I_Actividad_Service actividadService;

	@Autowired
	private I_Responsable_Iva_Service responsableIvaService;
	
	@Autowired
	private I_Sucursal_Service sucursalService;
	
	
	@GetMapping(value = "/provincias", produces = { "application/json" })
	public @ResponseBody List<Provincia> provincias() {
		return geoService.provincias();
	}

	@GetMapping(value = "/localidades", produces = { "application/json" })
	public @ResponseBody List<Localidad> localidades(@RequestParam String id_provincia) {
		return geoService.buscarPorId(Integer.valueOf(id_provincia)).getLocalidades();
	}

	private static final Logger log = LoggerFactory.getLogger(clienteController.class);

	@GetMapping(value = "/cargar_cliente/{term}", produces = { "application/json" })
	public @ResponseBody List<Cliente> autocomplete(String term) {

		log.info("Esto esta funcionando {{autocomplete cliente}}");

		return cliente_Service.buscarPorTerm(term);
	}

	@GetMapping("/detalles/{id_cliente}")
	public String detalle(Model model, @PathVariable("id_cliente") long id_cliente, RedirectAttributes redirectAttrs) {
		
		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}
		
		if (empresaService.listar_todo().size() == 0 || sucursalService.listar().size() == 0 || obtenerUsuario().getUsuarios_sucursales().size() == 0) {
			redirectAttrs.addFlashAttribute("error", "Para comenzar a operar en el Sistema debe 1) Tener una empresa registrada, 2) Tener una sucursal central registrada y 3) Tener su usuario asignado a una sucursal."
					+ " Consulte Manual del Usuario ubicado en la parte inferior de la página.");
			return "redirect:/";
		}
		
		if(cliente_Service.buscarPorId(id_cliente) == null) {
			redirectAttrs.addFlashAttribute("error", "Cliente con ese ID no existe");
			
			
			
			return "redirect:/";
		}
		
		Cliente cliente = cliente_Service.buscarPorId(id_cliente);
				

		model.addAttribute("cliente", cliente);
		Usuario usuario = obtenerUsuario();
		model.addAttribute("empresa", empresaService.listar_todo().get(0));
		model.addAttribute("usuario", usuario);
 
		List<CtaCteCliente> ctas_ctes = ctacteclienteService.buscarPorCliente(cliente_Service.buscarPorId(id_cliente));
		
		model.addAttribute("ctas_ctes", ctas_ctes);
		
		model.addAttribute("notificaciones", planPagoService.listarTodo());
		
		double saldo_pendiente = 0;
		
		if(ctas_ctes.size() > 0) {
			for(CtaCteCliente cta_cte : ctas_ctes) {
				saldo_pendiente = cta_cte.getSaldo();
			}
		}
		
		model.addAttribute("saldo_pendiente", saldo_pendiente);
		
		
		return "clientes/detalles";
	}

	@GetMapping("/listar")
	public String listar(Model model, RedirectAttributes redirectAttrs) {
		
		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}
		
		if (empresaService.listar_todo().size() == 0 || sucursalService.listar().size() == 0 || obtenerUsuario().getUsuarios_sucursales().size() == 0) {
			redirectAttrs.addFlashAttribute("error", "Para comenzar a operar en el Sistema debe 1) Tener una empresa registrada, 2) Tener una sucursal central registrada y 3) Tener su usuario asignado a una sucursal."
					+ " Consulte Manual del Usuario ubicado en la parte inferior de la página.");
			return "redirect:/";
		}
				

		model.addAttribute("clientes", cliente_Service.listarTodo());
		model.addAttribute("usuario", obtenerUsuario());
		model.addAttribute("empresa", empresaService.listar_todo().get(0));
		
		model.addAttribute("notificaciones", planPagoService.listarTodo());

		return "clientes/listar";
	}

	@GetMapping("/registrar/{id}")
	public String formulario(Model model, @PathVariable(name = "id") long id_cliente, RedirectAttributes redirectAttrs) {
		
		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}
		
		if(cliente_Service.buscarPorId(id_cliente) == null) {
			redirectAttrs.addFlashAttribute("error", "Cliente con ese ID no existe");
			
			return "redirect:/";
		}

		if (empresaService.listar_todo().size() == 0 || sucursalService.listar().size() == 0 || obtenerUsuario().getUsuarios_sucursales().size() == 0) {
			redirectAttrs.addFlashAttribute("error", "Para comenzar a operar en el Sistema debe 1) Tener una empresa registrada, 2) Tener una sucursal central registrada y 3) Tener su usuario asignado a una sucursal."
					+ " Consulte Manual del Usuario ubicado en la parte inferior de la página.");
			return "redirect:/";
		}
				

		model.addAttribute("cliente", cliente_Service.buscarPorId(id_cliente));
		model.addAttribute("tipos_documentos", tipo_documento_Service.listarTodo());
		model.addAttribute("usuario", obtenerUsuario());
		model.addAttribute("responsables_iva", responsableIvaService.listar_todo());
		model.addAttribute("empresa", empresaService.listar_todo().get(0));
		
		model.addAttribute("notificaciones", planPagoService.listarTodo());

		editar = true;

		return "clientes/registrar";
	}
	
	
	@PreAuthorize("hasAuthority('Admin')")
	@GetMapping("/deshabilitar/{id}")
	public String deshabilitar(Model model, @PathVariable(name = "id") long id_cliente, RedirectAttributes redirectAttrs) {
		
		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}
		
		if(cliente_Service.buscarPorId(id_cliente) == null) {
			redirectAttrs.addFlashAttribute("error", "Cliente con ese ID no existe");
			
			return "redirect:/";
		}

		if (empresaService.listar_todo().size() == 0 || sucursalService.listar().size() == 0 || obtenerUsuario().getUsuarios_sucursales().size() == 0) {
			redirectAttrs.addFlashAttribute("error", "Para comenzar a operar en el Sistema debe 1) Tener una empresa registrada, 2) Tener una sucursal central registrada y 3) Tener su usuario asignado a una sucursal."
					+ " Consulte Manual del Usuario ubicado en la parte inferior de la página.");
			return "redirect:/";
		}
				
	

		Cliente cliente = cliente_Service.buscarPorId(id_cliente);

		Actividad_Usuario actividad = new Actividad_Usuario();

		actividad.setFecha(new Date());
		actividad.setHora(new Date());
		actividad.setUsuario(obtenerUsuario().getUsuarios_sucursales().get(0));

		if (cliente.isActivo()) {
			cliente_Service.deshabilitar(cliente, false);
			actividad.setDescripcion("Cliente " + cliente.getId_cliente() + " dado de baja por usuario: "
					+ obtenerUsuario().getUsername());
		} else {
			cliente_Service.deshabilitar(cliente, true);
			actividad.setDescripcion("Cliente " + cliente.getId_cliente() + " dado de alta por usuario: "
					+ obtenerUsuario().getUsername());
		}

		actividadService.guardar_actividad(actividad);
		
		model.addAttribute("notificaciones", planPagoService.listarTodo());

		return "redirect:/clientes/listar";
	}

	@GetMapping("/registrar")
	public String formulario(Model model, RedirectAttributes redirectAttrs) {
		
		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}

		if (empresaService.listar_todo().size() == 0 || sucursalService.listar().size() == 0 || obtenerUsuario().getUsuarios_sucursales().size() == 0) {
			redirectAttrs.addFlashAttribute("error", "Para comenzar a operar en el Sistema debe 1) Tener una empresa registrada, 2) Tener una sucursal central registrada y 3) Tener su usuario asignado a una sucursal."
					+ " Consulte Manual del Usuario ubicado en la parte inferior de la página.");
			return "redirect:/";
		}
				

		Cliente cliente = new Cliente();

		model.addAttribute("tipos_documentos", tipo_documento_Service.listarTodo());
		model.addAttribute("usuario", obtenerUsuario());
		model.addAttribute("responsables_iva", responsableIvaService.listar_todo());

		model.addAttribute("empresa", empresaService.listar_todo().get(0));

		model.addAttribute("cliente", cliente);

		editar = false;
		
		model.addAttribute("notificaciones", planPagoService.listarTodo());

		return "clientes/registrar";
	}

	@PostMapping("/registrar")
	public String guardar(@Valid Cliente cliente, RedirectAttributes redirectAttrs) {
		
		
		
		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}
		
		if (empresaService.listar_todo().size() == 0 || sucursalService.listar().size() == 0 || obtenerUsuario().getUsuarios_sucursales().size() == 0) {
			redirectAttrs.addFlashAttribute("error", "Para comenzar a operar en el Sistema debe 1) Tener una empresa registrada, 2) Tener una sucursal central registrada y 3) Tener su usuario asignado a una sucursal."
					+ " Consulte Manual del Usuario ubicado en la parte inferior de la página.");
			return "redirect:/";
		}
				
		

		
		if (!cliente_Service.existente(cliente, editar)) {

			if (!editar) {

				cliente_Service.guardar(cliente, true);

				CtaCteCliente ctactecliente = new CtaCteCliente();

				ctactecliente.setDebe(0);
				ctactecliente.setHaber(0);
				ctactecliente.setSaldo(0);
				ctactecliente.setCliente(cliente);

				Actividad_Usuario actividad = new Actividad_Usuario();

				actividad.setFecha(new Date());
				actividad.setHora(new Date());
				actividad.setUsuario(obtenerUsuario().getUsuarios_sucursales().get(0));
				actividad.setDescripcion(
						"Alta Cliente " + cliente.getId_cliente() + " por usuario: " + obtenerUsuario().getUsername());

				Historial_Alta_Cliente historial = new Historial_Alta_Cliente();

				historial.setCtactecliente(ctactecliente);
				historial.setConcepto("TRANSPORTE CLI/" + cliente.getId_cliente());
				historial.setActividad_usuario(actividad);

				ctacteclienteService.guardar(ctactecliente);
				actividadService.guardar_actividad(actividad);
				cliente_Service.guardar_historial(historial);
			} else {
				cliente_Service.guardar(cliente, cliente.isActivo());
			}

		} else {
			redirectAttrs.addFlashAttribute("error", "Se están repitiendo los datos con un cliente que ya existe en el Sistema.");
			
		}

		return "redirect:/clientes/listar";
	}

	private Usuario obtenerUsuario() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		System.out.println(userDetail.getUsername());

		return usuarioRepo.findByUsername(userDetail.getUsername());
	}

	public String generadorNroExpediente() {

		long num = planPagoService.listarTodo().size() + 1000;
		Date fecha = new Date();

		return num + "/" + (fecha.getYear() - 100);
	}
	



}
