package com.proyectoCuotasRyR.proyectoCuotas.controllers;

import java.math.BigInteger;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Pattern;

import javax.validation.Valid;

import org.apache.tomcat.jni.Time;
import org.aspectj.weaver.patterns.ThisOrTargetAnnotationPointcut;
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
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Actividad_Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cliente;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.CtaCteCliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cuota;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Detalle_Recibo;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Recibo;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Importe;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Medio_Pago;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Recibo;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Actividad_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Cliente_Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Concepto_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_CtaCteCliente_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Cuota_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_DetalleRecibo_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Empresa_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Historial_Recibo_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Importe_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Medio_Pago_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Plan_Pago_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Recibo_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Sucursal_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Usuario_Sucursal_Service;


@Controller
@RequestMapping("pagos")
public class pagoController {
 
	@Autowired
	private I_Usuario_Repo usuarioRepo;

	@Autowired
	private I_Plan_Pago_Service planPagoService;

	@Autowired
	private I_Medio_Pago_Service medioPagoService;

	@Autowired
	private I_Cuota_Service cuotaService;

	private List<Cuota> cuotas;

	@Autowired
	private I_Cliente_Service clienteService;

	@Autowired
	private I_Importe_Service importeService;
	

	@Autowired
	private I_CtaCteCliente_Service ctacteclienteService;
	
	@Autowired
	private I_DetalleRecibo_Service detallesReciboService;
	
	@Autowired
	private I_Recibo_Service reciboService;
	
	@Autowired
	private I_Concepto_Service conceptoService;
	
	@Autowired
	private I_Empresa_Service empresaService;
	
	@Autowired
	private I_Actividad_Service actividadService;
	
	@Autowired
	private I_Historial_Recibo_Service historialService;
	
	@Autowired
	private I_Sucursal_Service sucursalService;
	
	private Cliente cliente;
	
	@Autowired
	private I_Usuario_Sucursal_Service usuarioSucursalService;

	

	@GetMapping("/pagos/listar")
	public String listar(Model model) {
		
		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}
		
		if (empresaService.listar_todo().size() == 0 || sucursalService.listar().size() == 0 || obtenerUsuario().getUsuarios_sucursales().size() == 0) {

			return "redirect:/";
		}
		
		model.addAttribute("usuario", obtenerUsuario());
		model.addAttribute("planes_pagos", planPagoService.listarTodo());
		model.addAttribute("clientes", clienteService.listarTodo());
		
		model.addAttribute("notificaciones", planPagoService.listarTodo());

		return "pagos/listar";
	}

	@GetMapping("/generar_pago/{id_plan_pago}/{id_cliente}")
	public String generar_pago(Model model, @PathVariable long id_plan_pago, @PathVariable long id_cliente,
			RedirectAttributes redirectAttrs) {
		
		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}
		
		if(planPagoService.buscarPorId(id_plan_pago) == null) {
			redirectAttrs.addFlashAttribute("error", "No se encontró plan de pago con este ID");
			return "redirect:/";
		}
		

		if(clienteService.buscarPorId(id_cliente) == null) {
			redirectAttrs.addFlashAttribute("error", "No se encontró cliente con este ID");
			return "redirect:/";
		}
		
		if (empresaService.listar_todo().size() == 0 || sucursalService.listar().size() == 0 || obtenerUsuario().getUsuarios_sucursales().size() == 0) {
			redirectAttrs.addFlashAttribute("error", "Para comenzar a operar en el Sistema debe 1) Tener una empresa registrada, 2) Tener una sucursal central registrada y 3) Tener su usuario asignado a una sucursal."
					+ " Consulte Manual del Usuario ubicado en la parte inferior de la página.");
			return "redirect:/";
		}
		
		if (cuotas == null) {
			redirectAttrs.addFlashAttribute("error", "No hay cuotas seleccionadas");
			return "redirect:/planes_pagos/detalles/" + id_plan_pago + '/' + id_cliente;
		}
		

		
		cliente = clienteService.buscarPorId(id_cliente);

		model.addAttribute("usuario", obtenerUsuario());
		model.addAttribute("plan_pago", planPagoService.buscarPorId(id_plan_pago));
		model.addAttribute("medios_pagos", medioPagoService.listar());
		model.addAttribute("cuotas", cuotas);
		model.addAttribute("cliente", cliente);
		
		model.addAttribute("usuario", obtenerUsuario());
		model.addAttribute("empresa", empresaService.listar_todo().get(0));

		float total = 0;

		for (Cuota cuota : cuotas) {
			total += cuota.getPendiente();
		}

		model.addAttribute("total", total);
		
		model.addAttribute("notificaciones", planPagoService.listarTodo());

		return "pagos/generar_pago";
	}

	@PostMapping("/generar_pago")
	public String btn_generar_pago(@RequestParam(name = "cuotas[]", required = false) String[] cuotas,
			@RequestParam(name = "id_plan_pago", required = false) long id_plan_pago,
		    @RequestParam(name = "id_cliente", required = false) long id_cliente,
		    RedirectAttributes redirectAttrs){
		
		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}
		
		if(planPagoService.buscarPorId(id_plan_pago) == null) {
			redirectAttrs.addFlashAttribute("error", "No se encontró plan de pago con este ID");
			return "redirect/";
		}
		

		if(clienteService.buscarPorId(id_cliente) == null) {
			redirectAttrs.addFlashAttribute("error", "No se encontró cliente con este ID");
			return "redirect/";
		}
		
		if (empresaService.listar_todo().size() == 0 || sucursalService.listar().size() == 0 || obtenerUsuario().getUsuarios_sucursales().size() == 0) {
			redirectAttrs.addFlashAttribute("error", "Para comenzar a operar en el Sistema debe 1) Tener una empresa registrada, 2) Tener una sucursal central registrada y 3) Tener su usuario asignado a una sucursal."
					+ " Consulte Manual del Usuario ubicado en la parte inferior de la página.");
			return "redirect:/";
		}
		
		if (cuotas == null) {
			redirectAttrs.addFlashAttribute("error", "No hay cuotas seleccionadas");
			return "redirect:/planes_pagos/detalles/" + id_plan_pago + '/' + id_cliente;
		}
		
		

		System.out.println("Id Plan de Pago: " + id_plan_pago);
		System.out.println("Cuotas: " + cuotas.length);

		this.cuotas = new ArrayList<>();

		for (int i = 0; i < cuotas.length; i++) {
			Cuota cuota = cuotaService.buscarPorId(Long.valueOf(cuotas[i]));
			this.cuotas.add(cuota);
		}

		return "redirect:/pagos/generar_pago/" + id_plan_pago + "/" + id_cliente;
	}

	@PostMapping("/generar_importes")
	public String generar_importes(@RequestParam(name = "plan_pago", required = true) long id_plan_pago,
			@RequestParam(name = "importes[]", required = true) float[] importes,
			@RequestParam(name = "medios_pagos[]", required = false) String[] medios_pagos,
			RedirectAttributes redirectAttrs) {

		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}
		
		if(planPagoService.buscarPorId(id_plan_pago) == null) {
			redirectAttrs.addFlashAttribute("error", "No se encontró plan de pago con este ID");
			return "redirect/";
		}
		

		if (empresaService.listar_todo().size() == 0 || sucursalService.listar().size() == 0 || obtenerUsuario().getUsuarios_sucursales().size() == 0) {
			redirectAttrs.addFlashAttribute("error", "Para comenzar a operar en el Sistema debe 1) Tener una empresa registrada, 2) Tener una sucursal central registrada y 3) Tener su usuario asignado a una sucursal."
					+ " Consulte Manual del Usuario ubicado en la parte inferior de la página.");
			return "redirect:/";
		}
		
		float total = 0;
		
		Recibo recibo = new Recibo();
		
		for (int i = 1; i < importes.length; i++) {
			total += importes[i];
		}
		

		recibo.setDescripcion(
				"RECIBIMOS (" + Convertir(String.valueOf(total), "PESOS", "PESOS", "CENTAVOS", "CENTAVOS", "CON", true)
				+ ")"
			);
		
		recibo.setConcepto(conceptoService.listar().get(0));
		
		reciboService.guardar(recibo);
		
		for (int i = 1; i < importes.length; i++) {
			System.out.println("Importes " + importes[i]); // 2000 - 2948.40
			System.out.println("Medio Pago: " + medios_pagos[i]);
			
			float importe_limite = importes[i];
			
			for(Cuota cuota : cuotas) {
				if(!cuota.isPagado()) {
					// 2948.40 > 0
					if(importe_limite > 0) {
						// 2948.40 >= 1298.8
						Importe importe = new Importe();
						importe.setMedio_pago(medioPagoService.buscarPorId(Long.valueOf(medios_pagos[i])));
						String detalles;
						if(importe_limite >= cuota.getPendiente()) {
							// 1649.4
							importe_limite -= cuota.getPendiente();
							importe.setImporte(cuota.getPendiente());
							cuota.setPendiente(0);
							cuota.setVencida(false);
						
							
							cuota.setPagado(true);
							detalles = "Cancela cuota nro: " + cuota.getNro_cuota();
						}else {
							cuota.setPendiente(cuota.getPendiente() - importe_limite);
							if(cuota.getPendiente() <= 0.50) {
								detalles = "Cancela cuota nro: " + cuota.getNro_cuota();
								cuota.setPendiente(0);
								cuota.setPagado(true);
								cuota.setVencida(false);
							}else {
								cuota.setPagado(false);
								detalles = "Abona parcialmente cuota nro: " + cuota.getNro_cuota();
							}
						
							importe.setImporte(importe_limite);
							importe_limite -= importe_limite;
						
						}
						
						importe.setFecha(new Date());
						importe.setCuota(cuota);
						importe.setDetalles(detalles);
						

						cuotaService.guardar(cuota);
						importeService.guardar(importe);
						
						Detalle_Recibo detalle_recibo = new Detalle_Recibo();
						
						
						detalle_recibo.setImporte(importe);
						/*detalle_recibo.setDescripcion(
									"RECIBIMOS (" + Convertir(String.valueOf(importe.getImporte()), "PESOS", "PESOS", "CENTAVOS", "CENTAVOS", "CON", true)
									+ ")"
								);*/
						
						detalle_recibo.setRecibo(recibo);
						detallesReciboService.guardar(detalle_recibo);
						
						
					}
				}
			}
			
		}
		
		recibo.getId_recibo();
		
		List<CtaCteCliente> ctas_ctes = ctacteclienteService.buscarPorCliente(cliente);

	    int size = ctas_ctes.size();

	    float saldo_cta_cte = ctas_ctes.get(size -
		1).getSaldo();

		CtaCteCliente ctactecliente = new CtaCteCliente();

		ctactecliente.setSaldo(saldo_cta_cte - total);
		ctactecliente.setHaber(total);
		ctactecliente.setDebe(0);
		ctactecliente.setCliente(cliente);
		
		ctacteclienteService.guardar(ctactecliente);
		
		Actividad_Usuario actividad = new Actividad_Usuario();
		actividad.setFecha(new Date());
		actividad.setHora(new Date());
		actividad.setUsuario(obtenerUsuario().getUsuarios_sucursales().get(0));
		actividad.setDescripcion("Recibo ID: " + recibo.getId_recibo() + ", generado con éxito");
		
		actividadService.guardar_actividad(actividad);
		
		Historial_Recibo historial = new Historial_Recibo();
		historial.setActividad_usuario(actividad);
		historial.setConcepto("RECIBO /" + recibo.getId_recibo());
		historial.setCtactecliente(ctactecliente);
		historial.setRecibo(recibo);
		
		historialService.guardar(historial);

		return "redirect:/planes_pagos/detalles/" + id_plan_pago + "/" + cliente.getId_cliente();
	}

	private Usuario obtenerUsuario() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		System.out.println(userDetail.getUsername());

		return usuarioRepo.findByUsername(userDetail.getUsername());
	}
	
	
	
	private final String[] UNIDADES = {"", "", "dos ", "tres ", "cuatro ", "cinco ", "seis ", "siete ", "ocho ", "nueve "};
	private final String[] DECENAS = {"diez ", "once ", "doce ", "trece ", "catorce ", "quince ", "dieciseis ",
	    "diecisiete ", "dieciocho ", "diecinueve", "veinte ", "treinta ", "cuarenta ",
	    "cincuenta ", "sesenta ", "setenta ", "ochenta ", "noventa "};
	private final String[] CENTENAS = {"", "ciento ", "doscientos ", "trecientos ", "cuatrocientos ", "quinientos ", "seiscientos ",
	    "setecientos ", "ochocientos ", "novecientos "};
	
	public String Convertir(String numero, String etiquetaEnteroSingular,String etiquetaEnteroPlural, String etiquetaFlotanteSingular,String etiquetaFlotantePlural, String etiquetaConector, boolean mayusculas) {
	    String literal = "";
	    String parte_decimal = "";
	    //si el numero utiliza (.) en lugar de (,) -> se reemplaza
	    numero = numero.replace(".", ",");
	    //si el numero no tiene parte decimal, se le agrega ,00
	    if (numero.indexOf(",") == -1) {
	        numero = numero + ",00";
	    }
	    //se valida formato de entrada -> 0,00 y 999 999 999 999,00
	    if (Pattern.matches("\\d{1,12},\\d{1,2}", numero)) {
	        //se divide el numero 0000000,00 -> entero y decimal
	        String Num[] = numero.split(",");
	        //de da formato al numero decimal
	        if (Num[1].length()==1) {
	        	Num[1] += "0";
	        }                        
	        String d = getDecenas(Num[1]);
	        if (d!="") {
	        	if (etiquetaEnteroSingular!="") parte_decimal += " ";
	        	if (Integer.parseInt(Num[1])==1) {
	        		parte_decimal += etiquetaConector + " " + d + etiquetaFlotanteSingular;
	        	} else {
	        		parte_decimal += etiquetaConector + " " + d + etiquetaFlotantePlural;
	        	}
	        }
	        
	        //se convierte el numero a literal                       
			BigInteger parteEntera = new BigInteger(Num[0]);
	       
	        if (parteEntera.compareTo(new BigInteger("0")) == 0) {//si el valor es cero
	            literal = "cero ";
	        } else if (parteEntera.compareTo(new BigInteger("999999999")) == 1 ) {//si es billon
	            literal = getBillones(Num[0]);
	        } else if (parteEntera.compareTo(new BigInteger("999999")) == 1 ) {//si es millon
	            literal = getMillones(Num[0]);
	        } else if (parteEntera.compareTo(new BigInteger("999")) == 1 ) {//si es miles
	            literal = getMiles(Num[0]);
	        } else if (parteEntera.compareTo(new BigInteger("99")) == 1 ) {//si es centena
	            literal = getCentenas(Num[0]);
	        } else if (parteEntera.compareTo(new BigInteger("9")) == 1 ) {//si es decena
	            literal = getDecenas(Num[0]);
	        } else {//sino unidades -> 9
	            literal = getUnidades(Num[0]);
	        }
	        //devuelve el resultado en mayusculas o minusculas
	        
	        if (parteEntera.compareTo(new BigInteger("1")) == 0) {
	        	literal += etiquetaEnteroSingular;            	
	        } else {
	        	literal += etiquetaEnteroPlural;
	        }            
	        
	        if (mayusculas) {
	            return (literal + parte_decimal).toUpperCase();
	        } else {
	            return (literal + parte_decimal);
	        }
	    } else {//error, no se puede convertir
	        return literal = null;
	    }
	}

	/* funciones para convertir los numeros a literales */
	private String getUnidades(String numero) {// 1 - 9
	    //si tuviera algun 0 antes se lo quita -> 09 = 9 o 009=9
	    String num = numero.substring(numero.length() - 1);
	    return UNIDADES[Integer.parseInt(num)];
	}

	private String getDecenas(String num) {// 99 
		int n = Integer.parseInt(num); 
		if (n < 10) {//para casos como -> 01 - 09 
			return getUnidades(num); 
		} else if (n > 19) {//para 20...99 
			String u = getUnidades(num); 
			if (u.equals("")) { //para 20,30,40,50,60,70,80,90 
				return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8]; 
			} else { 
				if(n == 21) {return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8].substring(0,5) + "i" + u;}
				if(n == 22) {return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8].substring(0,5) + "i" + u;}
				if(n == 23) {return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8].substring(0,5) + "i" + u;}
				if(n == 24) {return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8].substring(0,5) + "i" + u;}
				if(n == 25) {return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8].substring(0,5) + "i" + u;}
				if(n == 26) {return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8].substring(0,5) + "i" + u;}
				if(n == 27) {return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8].substring(0,5) + "i" + u;}
				if(n == 28) {return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8].substring(0,5) + "i" + u;} 
				if(n == 29) {return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8].substring(0,5) + "i" + u;} 
				return DECENAS[Integer.parseInt(num.substring(0, 1)) + 8] + "y " + u; 
			} 
		} else {//numeros entre 11 y 19 
			return DECENAS[n - 10];
		} 
	}

	private String getCentenas(String num) {// 999 o 099
	    if (Integer.parseInt(num) > 99) {//es centena
	        if (Integer.parseInt(num) == 100) {//caso especial
	            return " cien ";
	        } else {
	            return CENTENAS[Integer.parseInt(num.substring(0, 1))] + getDecenas(num.substring(1));
	        }
	    } else {//por Ej. 099 
	        //se quita el 0 antes de convertir a decenas
	        return getDecenas(Integer.parseInt(num) + "");
	    }
	}

	private String getMiles(String numero) {// 999 999
	    //obtiene las centenas
	    String c = numero.substring(numero.length() - 3);
	    //obtiene los miles
	    String m = numero.substring(0, numero.length() - 3);
	    String n = "";
	    //se comprueba que miles tenga valor entero
	    if (Integer.parseInt(m) > 0) {
	        n = getCentenas(m);
	        return n + "mil " + getCentenas(c);
	    } else {
	        return "" + getCentenas(c);
	    }

	}

	private String getMillones(String numero) { //000 000 000        
	    //se obtiene los miles
	    String miles = numero.substring(numero.length() - 6);
	    //se obtiene los millones
	    String millon = numero.substring(0, numero.length() - 6);
	    String n = "";
	    if (Integer.parseInt(millon) > 0) {
	    	if (Integer.parseInt(millon) == 1) {
	    		n = getUnidades(millon) + "millon ";
	    	} else {
	    		n = getCentenas(millon) + "millones ";
	    	}
	    }
	    
	    return n + getMiles(miles);
	}

	private String getBillones(String numero) { //000 000 000 000        
	    //se obtiene los miles
	    String miles = numero.substring(numero.length() - 9);
	    //se obtiene los millones
	    String millon = numero.substring(0, numero.length() - 9);
	    String n = "";
	    if (Integer.parseInt(millon) == 1) {
	    	n = getUnidades(millon) + "billon ";
	    } else {
	    	n = getCentenas(millon) + "billones ";
	    }
	    
	    return n + getMillones(miles);
	}


	

}
