package com.proyectoCuotasRyR.proyectoCuotas.controllers;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

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

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cliente;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.CtaCteCliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cuota;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Importe;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Localidad;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Medio_Pago;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Plan_Pago;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Provincia;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Responsable_Iva;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Tipo_Documento;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Cliente_Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_CtaCteCliente_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Cuota_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_GeoService;

import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Medio_Pago_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Plan_Pago_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Proveedor_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Responsable_Iva_Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Tipo_Interes_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.Tipo_DocumentoService;

@Controller
@RequestMapping("planes_pagos")
@SessionAttributes({"plan_pago","historial_pago"})
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
	private I_Responsable_Iva_Service responsableIvaService;
	

	
	@Autowired
	private I_Medio_Pago_Service medioPagoService;

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

	@GetMapping("/detalles/{id}")
	public String detalle_plan_pago(@PathVariable Long id, Model model, RedirectAttributes redirAttrs) {

		Plan_Pago plan_pago = planPagoService.buscarPorId(id);

		if (plan_pago == null) {
			redirAttrs.addFlashAttribute("error", "No existe un plan de pago con este id: " + id);
			return "redirect:/planes_pagos/simular_cuotas";
		}

		model.addAttribute("plan_pago", plan_pago);
		
	
		
		model.addAttribute("medios_pagos", medioPagoService.listar());
		


		model.addAttribute("usuario", obtenerUsuario());
		
		float pagado = 0;
		float pendiente = 0;
		
		for(Cuota cuota : plan_pago.getCuotas()) {
			if(cuota.getImportes().size() > 0) {
				for(Importe importe : cuota.getImportes()) {
					pagado += importe.getImporte();
				}
			}
			
			if(!cuota.isPagado()) {
				pendiente += cuota.getPendiente();
			}
		}
		
		model.addAttribute("pagado", pagado);
		model.addAttribute("pendiente", pendiente);
		

		return "planes_pagos/detalles";
	}

	@GetMapping("/simular_cuotas")
	public String simular_cuotas(Model model) {

		Plan_Pago plan_pago = new Plan_Pago();

		model.addAttribute("plan_pago", plan_pago);
		model.addAttribute("tipos_intereses", tipoInteresService.listarTodo());
		model.addAttribute("tipos_documentos", tipo_documento_Service.listarTodo());
		model.addAttribute("proveedores", proveedor_Service.listarTodo());
		model.addAttribute("responsables_iva", responsableIvaService.listar_todo());

		model.addAttribute("usuario", obtenerUsuario());

		editar = false;

		return "planes_pagos/simular_cuotas";
	}

	@GetMapping("/clientes/simular_cuotas/{id_cliente}")
	public String simular_cuotas(Model model, @PathVariable(name = "id_cliente") long id_cliente) {

		Plan_Pago plan_pago = new Plan_Pago();

		this.cliente = clienteService.buscarPorId(id_cliente);

		model.addAttribute("plan_pago", plan_pago);
		model.addAttribute("cliente", this.cliente);
		model.addAttribute("tipos_intereses", tipoInteresService.listarTodo());
		model.addAttribute("tipos_documentos", tipo_documento_Service.listarTodo());
		model.addAttribute("proveedores", proveedor_Service.listarTodo());
		

		model.addAttribute("usuario", obtenerUsuario());

		editar = false;

		return "planes_pagos/clientes/simular_cuotas";
	}

	@GetMapping("/eliminar/{id_plan_pago}")
	public String eliminar(Model model, @PathVariable(name = "id_plan_pago") long id_plan_pago) {

		planPagoService.eliminar(planPagoService.buscarPorId(id_plan_pago));

		return "redirect:/planes_pagos/simular_cuotas";
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
			@RequestParam(name = "total", required = true) Float total,
			@RequestParam(name = "vcuota", required = true) Float vcuota,
			@RequestParam(name = "cliente", required = true) String cliente,
			@RequestParam(name = "nro_documento", required = true) String nro_documento,
			@RequestParam(name = "tipo_documento", required = true) Tipo_Documento tipo_documento,
			@RequestParam(name = "id_responsable", required = true) Responsable_Iva responsable_iva,
			@RequestParam(name = "alta", required = true) @DateTimeFormat(pattern = "yyyy-MM-dd") Date alta,
			@RequestParam(name = "direccion", required = true) String direccion,
			@RequestParam(name = "id_seleccion", required = true) Localidad localidad,
			@RequestParam(name = "tel_fijo", required = true) String tel_fijo,
			@RequestParam(name = "tel_fijo2", required = true) String tel_fijo2,
			@RequestParam(name = "cel", required = true) String cel,
			@RequestParam(name = "cel2", required = true) String cel2,
			@RequestParam(name = "mail", required = true) String mail,
			@RequestParam(name = "mail2", required = true) String mail2

	) {

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
				cuota.setValor((float) (Math.round(Float.valueOf(valor_cuota[i]) * 100d) / 100d));
				cuota.setPendiente((float) (Math.round(Float.valueOf(valor_cuota[i]) * 100d) / 100d));

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

		//clienteService.guardar(c);

		plan_pago.setId_cliente(c);

		plan_pago.setActivo(true);

		plan_pago.setCuotas(cuotas);
		plan_pago.setTotal((float) (Math.round(total * 100d) / 100d)); 
		plan_pago.setValor_cuota((float) (Math.round(vcuota * 100d) / 100d));
		plan_pago.setNro_expediente(generadorNroExpediente());

	

		planPagoService.guardar(plan_pago);
		
		

		CtaCteCliente ctactecliente1 = new CtaCteCliente();

		ctactecliente1.setDebe(0);
		ctactecliente1.setHaber(0);
		ctactecliente1.setSaldo(0);
	
		//ctactecliente1.setFecha(alta);
		
		CtaCteCliente ctactecliente2 = new CtaCteCliente();

		ctactecliente2.setDebe((float) (Math.round(Float.valueOf(total) * 100d) / 100d));
		ctactecliente2.setHaber(0);
		ctactecliente2.setSaldo((float) (Math.round(Float.valueOf(total) * 100d) / 100d));
		
		//ctactecliente2.setFecha(plan_pago.getFecha_inicio());
		
		ctacteclienteService.guardar(ctactecliente1);
		ctacteclienteService.guardar(ctactecliente2);
		
		
		return "redirect:/planes_pagos/detalles/" + plan_pago.getId_plan_pago();
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
			@RequestParam(name = "total", required = true) Float total,
			@RequestParam(name = "vcuota", required = true) Float vcuota

	) {

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
				cuota.setValor((float) (Math.round(Float.valueOf(valor_cuota[i]) * 100d) / 100d));
				cuota.setPendiente((float) (Math.round(Float.valueOf(valor_cuota[i]) * 100d) / 100d));

				Date fecha = new Date();
				fecha.setDate(plan_pago.getFecha_inicio().getDate());
				fecha.setYear(plan_pago.getFecha_inicio().getYear());
				fecha.setMonth(mes + i);

				cuota.setFecha(fecha);

				cuotas.add(cuota);
			}
		}

		plan_pago.setId_cliente(this.cliente);

		plan_pago.setActivo(true);

		plan_pago.setCuotas(cuotas);
		plan_pago.setTotal((float) (Math.round(total * 100d) / 100d)); 
		plan_pago.setValor_cuota((float) (Math.round(vcuota * 100d) / 100d));
		plan_pago.setNro_expediente(generadorNroExpediente());

		

		planPagoService.guardar(plan_pago);
		
		
	
		
		
		//int size = this.cliente.getCtas_ctes_cliente().size();
		
		
		//float saldo_cta_cte = this.cliente.getCtas_ctes_cliente().get(size - 1).getSaldo();
	
		
		CtaCteCliente ctactecliente = new CtaCteCliente();
		
		//ctactecliente.setFecha(plan_pago.getFecha_inicio());
		
		ctactecliente.setDebe((float) (Math.round(Float.valueOf(total) * 100d) / 100d));
		//ctactecliente.setSaldo((float) (Math.round(Float.valueOf(saldo_cta_cte) * 100d) / 100d) + (float) (Math.round(Float.valueOf(total) * 100d) / 100d));
		ctactecliente.setHaber(0);
		
		ctacteclienteService.guardar(ctactecliente);
		
		

		return "redirect:/planes_pagos/detalles/" + plan_pago.getId_plan_pago();
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
