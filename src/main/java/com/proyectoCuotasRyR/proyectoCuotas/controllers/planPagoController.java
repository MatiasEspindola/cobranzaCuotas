package com.proyectoCuotasRyR.proyectoCuotas.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
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

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Actividad_Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cliente;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.CtaCteCliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cuota;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Alta_Cliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Plan_Pago;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Importe;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Localidad;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Medio_Pago;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Plan_Pago;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Provincia;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Responsable_Iva;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Tipo_Documento;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Actividad_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Cliente_Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_CtaCteCliente_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Cuota_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Empresa_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_GeoService;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Historial_Plan_Pago_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Medio_Pago_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Plan_Pago_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Proveedor_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Responsable_Iva_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Sucursal_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Tipo_Interes_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Usuario_Sucursal_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.Tipo_DocumentoService;

@Controller
@RequestMapping("planes_pagos")
@SessionAttributes({ "plan_pago", "historial_pago" })
public class planPagoController {

	@Autowired
	private I_Tipo_Interes_Service tipoInteresService;

	@Autowired
	private I_Plan_Pago_Service planPagoService;

	@Autowired
	private I_Usuario_Repo usuarioRepo;

	@Autowired
	private I_Cuota_Service cuotaService;

	@Autowired
	private Tipo_DocumentoService tipo_documento_Service;

	@Autowired
	private I_Proveedor_Service proveedor_Service;

	@Autowired
	private I_Cliente_Service clienteService;

	@Autowired
	private I_GeoService geoService;

	@Autowired
	private I_CtaCteCliente_Service ctacteclienteService;

	@Autowired
	private I_Historial_Plan_Pago_Service historialPlanPagoService;

	@Autowired
	private I_Responsable_Iva_Service responsableIvaService;

	@Autowired
	private I_Empresa_Service empresaService;

	@Autowired
	private I_Medio_Pago_Service medioPagoService;

	@Autowired
	private I_Actividad_Service actividadService;

	@Autowired
	private I_Sucursal_Service sucursalService;

	@Autowired
	private I_Usuario_Sucursal_Service usuarioSucursalService;

	private Cliente cliente;

	@GetMapping(value = "/provincias", produces = { "application/json" })
	public @ResponseBody List<Provincia> provincias() {
		return geoService.provincias();
	}

	@GetMapping(value = "/localidades", produces = { "application/json" })
	public @ResponseBody List<Localidad> localidades(@RequestParam String id_provincia) {
		return geoService.buscarPorId(Integer.valueOf(id_provincia)).getLocalidades();
	}

	private boolean editar;
	
	Logger logger = Logger.getLogger(planPagoController.class.getName());

	@GetMapping("/detalles/{id}/{id_cliente}")
	public String detalle_plan_pago(@PathVariable Long id, @PathVariable Long id_cliente, Model model,
			RedirectAttributes redirectAttrs) {

		if (!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}

		if (empresaService.listar_todo().size() == 0 || sucursalService.listar().size() == 0
				|| obtenerUsuario().getUsuarios_sucursales().size() == 0) {

			redirectAttrs.addFlashAttribute("error",
					"Para comenzar a operar en el Sistema debe 1) Tener una empresa registrada, 2) Tener una sucursal central registrada y 3) Tener su usuario asignado a una sucursal."
							+ " Consulte Manual del Usuario ubicado en la parte inferior de la página.");

			return "redirect:/";

		}

		if (planPagoService.buscarPorId(id) == null) {
			redirectAttrs.addFlashAttribute("error", "No existe un plan de pago con ese ID");
			return "redirect:/";
		}

		if (clienteService.buscarPorId(id_cliente) == null) {
			redirectAttrs.addFlashAttribute("error", "No existe un cliente con ese ID");
			return "redirect:/";
		}

		if (!planPagoService.buscarPorId(id).isActivo()) {
			redirectAttrs.addFlashAttribute("error", "Plan de Pago deshabilitado");
			return "redirect:/clientes/detalles/" + id_cliente;
		}

		Plan_Pago plan_pago = planPagoService.buscarPorId(id);

		Cliente cliente = clienteService.buscarPorId(id_cliente);

		this.cliente = cliente;

		model.addAttribute("cliente", cliente);

		model.addAttribute("plan_pago", plan_pago);

		model.addAttribute("medios_pagos", medioPagoService.listar());

		model.addAttribute("usuario", obtenerUsuario());

		float pagado = 0;
		float pendiente = 0;

		int vencidas = 0;
		int pagadas = 0;
		int saldo_pendiente = 0;
		int al_dia = 0;

		for (Cuota cuota : plan_pago.getCuotas()) {
			if (!cuota.isPagado()) {
				if (!cuota.isVencida()) {
					Date hoy = new Date();
					Date vencimiento_cuota = cuota.getFecha();

					if (hoy.after(vencimiento_cuota)) {
						cuota.setVencida(true);
						cuotaService.guardar(cuota);
						System.out.println("Hoy: " + hoy);
						System.out.println("Vencimiento cuota: " + vencimiento_cuota);
						System.out.println("Cuota ID: " + cuota.getId_cuota());
						System.out.println("Plan Pago ID: " + cuota.getId_plan_pago().getId_plan_pago());
					}

				}
			}
		}

		for (Cuota cuota : plan_pago.getCuotas()) {
			if (cuota.getImportes().size() > 0) {
				for (Importe importe : cuota.getImportes()) {
					pagado += importe.getImporte();
				}
			}

			if (!cuota.isPagado()) {
				pendiente += cuota.getPendiente();
				if (cuota.isVencida()) {

					vencidas++;
				} else {
					if (cuota.getPendiente() > 0 && cuota.getPendiente() < cuota.getValor()) {
						saldo_pendiente++;

					} else {
						al_dia++;
					}
				}
			} else {
				pagadas++;
			}
		}

		if (!plan_pago.isCompletado()) {
			if (plan_pago.getCuotas().size() == pagadas) {

				plan_pago.setNormal(false);
				plan_pago.setRiesgo_bajo(false);
				plan_pago.setRiesgo_medio(false);
				plan_pago.setRiesgo_alto(false);
				plan_pago.setIrrecuperable(false);
				plan_pago.setCompletado(true);

				planPagoService.guardar(plan_pago);
			} else {

				int dias = plan_pago.estado_deudor();
				if (dias <= 31) {
					plan_pago.setNormal(true);
					plan_pago.setRiesgo_bajo(false);
					plan_pago.setRiesgo_medio(false);
					plan_pago.setRiesgo_alto(false);
					plan_pago.setIrrecuperable(false);
					planPagoService.guardar(plan_pago);
				} else if (dias > 31 && dias <= 90) {
					plan_pago.setNormal(false);
					plan_pago.setRiesgo_bajo(true);
					plan_pago.setRiesgo_medio(false);
					plan_pago.setRiesgo_alto(false);
					plan_pago.setIrrecuperable(false);
					planPagoService.guardar(plan_pago);
				} else if (dias > 90 && dias <= 180) {
					plan_pago.setNormal(false);
					plan_pago.setRiesgo_bajo(false);
					plan_pago.setRiesgo_medio(true);
					plan_pago.setRiesgo_alto(false);
					plan_pago.setIrrecuperable(false);
					planPagoService.guardar(plan_pago);
				} else if (dias > 180 && dias <= 365) {
					plan_pago.setNormal(false);
					plan_pago.setRiesgo_bajo(false);
					plan_pago.setRiesgo_medio(false);
					plan_pago.setRiesgo_alto(true);
					plan_pago.setIrrecuperable(false);
					planPagoService.guardar(plan_pago);
				} else {
					plan_pago.setNormal(false);
					plan_pago.setRiesgo_bajo(false);
					plan_pago.setRiesgo_medio(false);
					plan_pago.setRiesgo_alto(false);
					plan_pago.setIrrecuperable(true);
					planPagoService.guardar(plan_pago);
				}
				
				System.out.println("dias: "+ dias); 
			}
		}

		model.addAttribute("pagado", pagado);
		model.addAttribute("pendiente", pendiente);
		model.addAttribute("vencidas", vencidas);
		model.addAttribute("pagadas", pagadas);
		model.addAttribute("al_dia", al_dia);
		model.addAttribute("saldo_pendiente", saldo_pendiente);

		Usuario usuario = obtenerUsuario();

		model.addAttribute("empresa", empresaService.listar_todo().get(0));
		model.addAttribute("usuario", usuario);
		
		model.addAttribute("notificaciones", planPagoService.listarTodo());

		return "planes_pagos/detalles";
	}

	@GetMapping("/simular_cuotas")
	public String simular_cuotas(Model model, RedirectAttributes redirectAttrs) {

		if (!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}

		if (empresaService.listar_todo().size() == 0 || sucursalService.listar().size() == 0
				|| obtenerUsuario().getUsuarios_sucursales().size() == 0) {

			redirectAttrs.addFlashAttribute("error",
					"Para comenzar a operar en el Sistema debe 1) Tener una empresa registrada, 2) Tener una sucursal central registrada y 3) Tener su usuario asignado a una sucursal."
							+ " Consulte Manual del Usuario ubicado en la parte inferior de la página.");

			return "redirect:/";

		}

		Usuario usuario = obtenerUsuario();

		model.addAttribute("empresa", empresaService.listar_todo().get(0));
		model.addAttribute("usuario", usuario);

		Plan_Pago plan_pago = new Plan_Pago();

		model.addAttribute("plan_pago", plan_pago);
		model.addAttribute("tipos_intereses", tipoInteresService.listarTodo());
		model.addAttribute("tipos_documentos", tipo_documento_Service.listarTodo());
		model.addAttribute("proveedores", proveedor_Service.listarTodo());
		model.addAttribute("responsables_iva", responsableIvaService.listar_todo());

		model.addAttribute("usuario", obtenerUsuario());

		editar = false;
		
		model.addAttribute("notificaciones", planPagoService.listarTodo());

		return "planes_pagos/simular_cuotas";
	}

	@GetMapping("/clientes/simular_cuotas/{id_cliente}")
	public String simular_cuotas(Model model, @PathVariable(name = "id_cliente") long id_cliente,
			RedirectAttributes redirectAttrs) {

		if (!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}

		if (empresaService.listar_todo().size() == 0 || sucursalService.listar().size() == 0
				|| obtenerUsuario().getUsuarios_sucursales().size() == 0) {
			redirectAttrs.addFlashAttribute("error",
					"Para comenzar a operar en el Sistema debe 1) Tener una empresa registrada, 2) Tener una sucursal central registrada y 3) Tener su usuario asignado a una sucursal."
							+ " Consulte Manual del Usuario ubicado en la parte inferior de la página.");
			return "redirect:/";
		}

		if (clienteService.buscarPorId(id_cliente) == null) {
			redirectAttrs.addFlashAttribute("error", "No existe un cliente con ese ID");
			return "redirect:/";
		}

		Plan_Pago plan_pago = new Plan_Pago();

		this.cliente = clienteService.buscarPorId(id_cliente);

		model.addAttribute("plan_pago", plan_pago);
		model.addAttribute("cliente", this.cliente);

		model.addAttribute("tipos_intereses", tipoInteresService.listarTodo());
		model.addAttribute("tipos_documentos", tipo_documento_Service.listarTodo());
		model.addAttribute("proveedores", proveedor_Service.listarTodo());

		Usuario usuario = obtenerUsuario();

		model.addAttribute("empresa", empresaService.listar_todo().get(0));
		model.addAttribute("usuario", usuario);

		model.addAttribute("usuario", obtenerUsuario());

		editar = false;
		
		model.addAttribute("notificaciones", planPagoService.listarTodo());

		return "planes_pagos/clientes/simular_cuotas";
	}

	@PostMapping("/simular_cuotas")
	public String simular_cuotas(@Valid Plan_Pago plan_pago,

			@RequestParam(name = "n_cuota[]", required = true) String[] n_cuota,
			@RequestParam(name = "cuota_nominal[]", required = true) String[] cuota_nominal,
			@RequestParam(name = "interes_cuota[]", required = true) String[] interes_cuota,
			@RequestParam(name = "saldo_capital[]", required = true) String[] saldo_capital,
			@RequestParam(name = "iva_interes[]", required = true) String[] iva_interes,
			@RequestParam(name = "honorarios[]", required = true) String[] honorarios,
			@RequestParam(name = "iva_honorarios[]", required = true) String[] iva_honorarios,
			@RequestParam(name = "gastos_administrativos[]", required = true) String[] gastos_administrativos,
			@RequestParam(name = "v_cuota[]", required = true) String[] valor_cuota,
			@RequestParam(name = "total", required = true) float total,
			@RequestParam(name = "vcuota", required = true) float vcuota,
			@RequestParam(name = "cliente", required = true) String cliente,
			@RequestParam(name = "nro_documento", required = true) String nro_documento,
			@RequestParam(name = "tipo_documento", required = true) Tipo_Documento tipo_documento,
			@RequestParam(name = "id_responsable", required = true) Responsable_Iva responsable_iva,

			@RequestParam(name = "direccion", required = true) String direccion,
			@RequestParam(name = "id_seleccion", required = true) Localidad localidad,
			@RequestParam(name = "tel_fijo", required = true) String tel_fijo,
			@RequestParam(name = "tel_fijo2", required = true) String tel_fijo2,
			@RequestParam(name = "cel", required = true) String cel,
			@RequestParam(name = "cel2", required = true) String cel2,
			@RequestParam(name = "mail", required = true) String mail,
			@RequestParam(name = "mail2", required = true) String mail2, RedirectAttributes redirectAttrs

	) {

		if (!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}

		if (empresaService.listar_todo().size() == 0 || sucursalService.listar().size() == 0
				|| obtenerUsuario().getUsuarios_sucursales().size() == 0) {
			redirectAttrs.addFlashAttribute("error",
					"Para comenzar a operar en el Sistema debe 1) Tener una empresa registrada, 2) Tener una sucursal central registrada y 3) Tener su usuario asignado a una sucursal."
							+ " Consulte Manual del Usuario ubicado en la parte inferior de la página.");
			return "redirect:/";
		}

		List<Cuota> cuotas = new ArrayList<>();

		int mes = plan_pago.getFecha_inicio().getMonth();

		for (int i = 0; i < n_cuota.length; i++) {
			if (i > 0) {

				Cuota cuota = new Cuota();
				cuota.setNro_cuota(Integer.valueOf(n_cuota[i]));
				cuota.setCuota_nominal(Float.valueOf(cuota_nominal[i]));
				cuota.setDeuda_nominal(Float.valueOf(saldo_capital[i]));
				cuota.setInteres(Float.valueOf(interes_cuota[i]));
				cuota.setIva_interes(Float.valueOf(iva_interes[i]));
				cuota.setHonorarios(Float.valueOf(honorarios[i]));
				cuota.setIva_honorarios(Float.valueOf(iva_honorarios[i]));
				cuota.setGastos(Float.valueOf(gastos_administrativos[i]));
				cuota.setValor(Float.valueOf(valor_cuota[i]));
				cuota.setPendiente(Float.valueOf(valor_cuota[i]));

				Date fecha = new Date();
				fecha.setDate(plan_pago.getFecha_inicio().getDate());
				fecha.setYear(plan_pago.getFecha_inicio().getYear());
				fecha.setMonth(mes + i);

				cuota.setFecha(fecha);

				cuotas.add(cuota);
			}
		}

		Cliente c = new Cliente();
		c.setCliente(cliente);

		c.setCel(cel);
		c.setCel2(cel2);
		c.setDireccion(direccion);
		c.setId_localidad1(localidad);
		c.setMail(mail);
		c.setMail2(mail2);
		c.setNro_documento(nro_documento);
		c.setTel_fijo(tel_fijo);
		c.setTel_fijo2(tel_fijo2);
		c.setTipo_documento(tipo_documento);
		c.setId_responsable(responsable_iva);

		if (!clienteService.existente(c, editar)) {
			clienteService.guardar(c, true);
		} else {
			redirectAttrs.addFlashAttribute("error",
					"Se están repitiendo los datos con un cliente que ya existe en el Sistema.");

			return "redirect:/clientes/listar";
		}

		// plan_pago.setId_cliente(c);

		plan_pago.setActivo(true);

		plan_pago.setCuotas(cuotas);
		
		logger.log(Level.INFO, "El total es: " + total);
		
		plan_pago.setTotal(total);
		plan_pago.setValor_cuota(vcuota);
		plan_pago.setNro_expediente(generadorNroExpediente());

		planPagoService.guardar(plan_pago);

		CtaCteCliente ctactecliente1 = new CtaCteCliente();

		ctactecliente1.setDebe(0);
		ctactecliente1.setHaber(0);
		ctactecliente1.setSaldo(0);
		ctactecliente1.setCliente(c);

		CtaCteCliente ctactecliente2 = new CtaCteCliente();

		ctactecliente2.setDebe(total);
		ctactecliente2.setHaber(0);
		ctactecliente2.setSaldo(total);
		ctactecliente2.setCliente(c);

		ctacteclienteService.guardar(ctactecliente1);
		ctacteclienteService.guardar(ctactecliente2);

		Actividad_Usuario actividad = new Actividad_Usuario();
		actividad.setFecha(new Date());
		actividad.setHora(new Date());
		actividad.setUsuario(obtenerUsuario().getUsuarios_sucursales().get(0));
		actividad.setDescripcion(
				"Alta Cliente " + c.getId_cliente() + " por usuario: " + obtenerUsuario().getUsername());

		actividadService.guardar_actividad(actividad);

		Historial_Alta_Cliente historial = new Historial_Alta_Cliente();

		historial.setCtactecliente(ctactecliente1);
		historial.setConcepto("TRANSPORTE CLI /" + c.getId_cliente());
		historial.setActividad_usuario(actividad);

		clienteService.guardar_historial(historial);

		Actividad_Usuario actividad2 = new Actividad_Usuario();
		actividad2.setFecha(new Date());
		actividad2.setHora(new Date());
		actividad2.setUsuario(obtenerUsuario().getUsuarios_sucursales().get(0));
		actividad2.setDescripcion(
				"Alta plan de pago " + plan_pago.getId_plan_pago() + " por usuario: " + obtenerUsuario().getUsername());

		actividadService.guardar_actividad(actividad2);

		Historial_Plan_Pago historial_alta_plan_pago = new Historial_Plan_Pago();

		historial_alta_plan_pago.setActividad_usuario(actividad2);
		historial_alta_plan_pago.setCtactecliente(ctactecliente2);
		historial_alta_plan_pago.setPlan_pago(plan_pago);
		historial_alta_plan_pago.setConcepto("PLAN PAGO /" + plan_pago.getId_plan_pago());

		historialPlanPagoService.guardar(historial_alta_plan_pago);

		return "redirect:/planes_pagos/detalles/" + plan_pago.getId_plan_pago() + "/" + c.getId_cliente();
	}

	@PostMapping("/clientes/simular_cuotas")
	public String clientes_simular_cuotas(@Valid Plan_Pago plan_pago,

			@RequestParam(name = "n_cuota[]", required = true) String[] n_cuota,
			@RequestParam(name = "cuota_nominal[]", required = true) String[] cuota_nominal,
			@RequestParam(name = "interes_cuota[]", required = true) String[] interes_cuota,
			@RequestParam(name = "saldo_capital[]", required = true) String[] saldo_capital,
			@RequestParam(name = "iva_interes[]", required = true) String[] iva_interes,
			@RequestParam(name = "honorarios[]", required = true) String[] honorarios,
			@RequestParam(name = "iva_honorarios[]", required = true) String[] iva_honorarios,
			@RequestParam(name = "gastos_administrativos[]", required = true) String[] gastos_administrativos,
			@RequestParam(name = "v_cuota[]", required = true) String[] valor_cuota,
			@RequestParam(name = "total", required = true) float total,
			@RequestParam(name = "vcuota", required = true) float vcuota, RedirectAttributes redirectAttrs

	) {

		if (cliente == null) {
			redirectAttrs.addFlashAttribute("error", "No se asignó correctamente un cliente");
			return "redirect:/clientes/listar";
		}

		if (!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}

		if (empresaService.listar_todo().size() == 0 || sucursalService.listar().size() == 0
				|| obtenerUsuario().getUsuarios_sucursales().size() == 0) {
			redirectAttrs.addFlashAttribute("error",
					"Para comenzar a operar en el Sistema debe 1) Tener una empresa registrada, 2) Tener una sucursal central registrada y 3) Tener su usuario asignado a una sucursal."
							+ " Consulte Manual del Usuario ubicado en la parte inferior de la página.");
			return "redirect:/";
		}

		List<Historial_Plan_Pago> historiales = historialPlanPagoService.listar();

		for (Historial_Plan_Pago historial : historiales) {
			if (historial.getCtactecliente().getCliente().getId_cliente() == cliente.getId_cliente()
					&& historial.getPlan_pago().getId_proveedor().getId_proveedor() == plan_pago.getId_proveedor()
							.getId_proveedor()) {
				redirectAttrs.addFlashAttribute("error",
						"Este cliente ya tiene un plan de pago asignado con este proveedor");
				return "redirect:/clientes/detalles/" + cliente.getId_cliente();

			}
		}

		List<Cuota> cuotas = new ArrayList<>();

		int mes = plan_pago.getFecha_inicio().getMonth();

		for (int i = 0; i < n_cuota.length; i++) {
			if (i > 0) {
				
				

				Cuota cuota = new Cuota();
				cuota.setNro_cuota(Integer.valueOf(n_cuota[i]));
				cuota.setCuota_nominal(Float.valueOf(cuota_nominal[i]));
				cuota.setDeuda_nominal(Float.valueOf(saldo_capital[i]));
				cuota.setInteres(Float.valueOf(interes_cuota[i]));
				cuota.setIva_interes(Float.valueOf(iva_interes[i]));
				cuota.setHonorarios(Float.valueOf(honorarios[i]));
				cuota.setIva_honorarios(Float.valueOf(iva_honorarios[i]));
				cuota.setGastos(Float.valueOf(gastos_administrativos[i]));
				cuota.setValor(Float.valueOf(valor_cuota[i]));
				cuota.setPendiente(Float.valueOf(valor_cuota[i]));

				Date fecha = new Date();
				fecha.setDate(plan_pago.getFecha_inicio().getDate());
				fecha.setYear(plan_pago.getFecha_inicio().getYear());
				fecha.setMonth(mes + i);

				cuota.setFecha(fecha);

				cuotas.add(cuota);
			}
		}

		// plan_pago.setId_cliente(this.cliente);

		plan_pago.setActivo(true);

		plan_pago.setCuotas(cuotas);
		
		
		
		System.out.println("Total: " + total);
		
		plan_pago.setTotal(total);
		plan_pago.setValor_cuota(vcuota);
		plan_pago.setNro_expediente(generadorNroExpediente());

		System.out.println(cliente.toString());

		List<CtaCteCliente> ctas_ctes = ctacteclienteService.buscarPorCliente(cliente);

		int size = ctas_ctes.size();

		float saldo_cta_cte = ctas_ctes.get(size - 1).getSaldo();

		planPagoService.guardar(plan_pago);

		CtaCteCliente ctactecliente = new CtaCteCliente();
		ctactecliente.setDebe(total);
		ctactecliente.setSaldo(saldo_cta_cte
				+ total);
		ctactecliente.setHaber(0);
		ctactecliente.setCliente(cliente);

		ctacteclienteService.guardar(ctactecliente);

		Actividad_Usuario actividad = new Actividad_Usuario();

		actividad.setFecha(new Date());
		actividad.setUsuario(obtenerUsuario().getUsuarios_sucursales().get(0));
		actividad.setHora(new Date());
		actividad.setDescripcion(
				"Alta plan de pago " + plan_pago.getId_plan_pago() + " por usuario: " + obtenerUsuario().getUsername());

		actividadService.guardar_actividad(actividad);

		Historial_Plan_Pago historial = new Historial_Plan_Pago();

		historial.setActividad_usuario(actividad);
		historial.setConcepto("PLAN PAGO /" + plan_pago.getId_plan_pago());
		historial.setPlan_pago(plan_pago);
		historial.setCtactecliente(ctactecliente);

		historialPlanPagoService.guardar(historial);

		return "redirect:/clientes/detalles/" + cliente.getId_cliente();

		// return "redirect:/planes_pagos/detalles/" + plan_pago.getId_plan_pago();
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
