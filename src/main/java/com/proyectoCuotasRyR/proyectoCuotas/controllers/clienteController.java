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

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Comprobante;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.CtaCteCliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cuota;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Localidad;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Plan_Pago;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Proveedor;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Provincia;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.ClienteService;

import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Cliente_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Comprobante_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_CtaCteCliente_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_GeoService;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Responsable_Iva_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Tipo_Comprobante_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Tipo_Documento_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_UploadFile_Service;

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

	private boolean editar;

	private Cliente cliente;

	private Proveedor proveedor;

	private Plan_Pago plan_pago;

	@Autowired
	private I_CtaCteCliente_Service ctacteclienteService;

	@Autowired
	private I_Comprobante_Service comprobanteService;

	@Autowired
	private I_Tipo_Comprobante_Service tipoComprobanteService;
	
	@Autowired
	private I_Responsable_Iva_Service responsableIvaService;

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
	public String detalle(Model model, @PathVariable("id_cliente") long id_cliente) {
		model.addAttribute("cliente", cliente_Service.buscarPorId(id_cliente));
		Usuario usuario = obtenerUsuario();

		model.addAttribute("usuario", usuario);
		return "clientes/detalles";
	}

	@GetMapping("/listar")
	public String listar(Model model) {

		model.addAttribute("clientes", cliente_Service.listarTodo());
		model.addAttribute("usuario", obtenerUsuario());

		return "clientes/listar";
	}

	@GetMapping("/formulario/{id}")
	public String formulario(Model model, @PathVariable(name = "id") long id_cliente) {

		model.addAttribute("cliente", cliente_Service.buscarPorId(id_cliente));
		model.addAttribute("tipos_documentos", tipo_documento_Service.listarTodo());
		model.addAttribute("usuario", obtenerUsuario());
		model.addAttribute("responsables_iva", responsableIvaService.listar_todo());

		editar = true;

		return "clientes/formulario";
	}

	@GetMapping("/eliminar/{id}")
	public String eliminar(Model model, @PathVariable(name = "id") long id_cliente) {

		cliente_Service.eliminar(cliente_Service.buscarPorId(id_cliente));

		return "redirect:/clientes/listar";
	}

	@GetMapping("/formulario")
	public String formulario(Model model) {

		Cliente cliente = new Cliente();

		model.addAttribute("tipos_documentos", tipo_documento_Service.listarTodo());
		model.addAttribute("usuario", obtenerUsuario());
		model.addAttribute("responsables_iva", responsableIvaService.listar_todo());

		model.addAttribute("cliente", cliente);

		return "clientes/formulario";
	}

	@PostMapping("/formulario")
	public String guardar(@Valid Cliente cliente, RedirectAttributes redirAttrs) {

		cliente_Service.guardar(cliente);

		Comprobante comprobante1 = new Comprobante();

		String comprobante;

		comprobante = tipoComprobanteService.listar().get(0).getTipo_comprobante().concat("/") + cliente.getId_cliente();

		comprobante1.setComprobante(comprobante);
		comprobante1.setTipo_comprobante(tipoComprobanteService.listar().get(0));
		
		comprobanteService.guardar(comprobante1);
		
		CtaCteCliente ctactecliente1 = new CtaCteCliente();
		
		ctactecliente1.setDebe(0);
		ctactecliente1.setHaber(0);
		ctactecliente1.setSaldo(0);
		ctactecliente1.setComprobante(comprobante1);
		ctactecliente1.setCliente(cliente);
		ctactecliente1.setFecha(cliente.getAlta());
		
		ctacteclienteService.guardar(ctactecliente1);

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
