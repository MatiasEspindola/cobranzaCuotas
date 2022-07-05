package com.proyectoCuotasRyR.proyectoCuotas.controllers;

import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

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
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Actividad_Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Localidad;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Provincia;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario_Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Actividad_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Empresa_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_GeoService;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Plan_Pago_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Sucursal_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_UploadFile_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Usuario_Sucursal_Service;

@Controller
@SessionAttributes("sucursal")
@RequestMapping("/sucursales")
public class sucursalController {

	@Autowired
	private I_Empresa_Service empresaService;

	@Autowired
	private I_Usuario_Repo usuarioRepo;

	@Autowired
	private I_GeoService geoService;

	@Autowired
	private I_UploadFile_Service upl;

	@Autowired
	private I_Sucursal_Service sucursalService;

	@Autowired
	private I_Actividad_Service actividadService;

	@Autowired
	private I_Usuario_Sucursal_Service usuarioSucursalService;
	
	@Autowired
	private I_Plan_Pago_Service planPagoService;

	private boolean editar;

	@GetMapping(value = "/provincias", produces = { "application/json" })
	public @ResponseBody List<Provincia> provincias() {
		return geoService.provincias();
	}

	@GetMapping(value = "/localidades", produces = { "application/json" })
	public @ResponseBody List<Localidad> localidades(@RequestParam String id_provincia) {
		return geoService.buscarPorId(Integer.valueOf(id_provincia)).getLocalidades();
	}

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

	@PreAuthorize("hasAuthority('Admin')")
	@GetMapping("/registrar")
	public String registrar(Model model, RedirectAttributes redirectAttrs) {

		if (!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}

		if (empresaService.listar_todo().size() == 0) {

			redirectAttrs.addFlashAttribute("error", "Para registrar una sucursal debe registrar una empresa");

			return "redirect:/";
		}

		model.addAttribute("empresa", empresaService.listar_todo().get(0));
		model.addAttribute("usuario", obtenerUsuario());
		model.addAttribute("sucursal", new Sucursal());

		model.addAttribute("notificaciones", planPagoService.listarTodo());
		
		editar = false;

		return "sucursales/registrar";
	}
	
	@PreAuthorize("hasAuthority('Admin')")
	@GetMapping("/detalles/{id_sucursal}")
	public String detalles(Model model, @PathVariable long id_sucursal, RedirectAttributes redirectAttrs) {

		if (!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}

		if (sucursalService.buscarPorId(id_sucursal) == null) {
			redirectAttrs.addFlashAttribute("error", "Sucursal con ese ID no existe");

			return "redirect:/";
		}

		if (empresaService.listar_todo().size() == 0 || sucursalService.listar().size() == 0) {

			redirectAttrs.addFlashAttribute("error", "No hay empresa o sucursales registradas");

			return "redirect:/";
		}
		
		Sucursal sucursal = sucursalService.buscarPorId(id_sucursal);

		model.addAttribute("empresa", empresaService.listar_todo().get(0));
		model.addAttribute("usuario", obtenerUsuario());
		model.addAttribute("sucursal", sucursal);
		
		model.addAttribute("notificaciones", planPagoService.listarTodo());
		
		
		List<Usuario_Sucursal> usuario_sucursal = usuarioSucursalService.buscarPorSucursal(sucursal);
		
		model.addAttribute("us", usuario_sucursal.get(0));
		
		System.out.println(usuario_sucursal.size());
		
		//for(Actividad_Usuario)



		return "sucursales/detalles";
	}

	@PreAuthorize("hasAuthority('Admin')")
	@GetMapping("/registrar/{id_sucursal}")
	public String registrar(Model model, @PathVariable long id_sucursal, RedirectAttributes redirectAttrs) {

		if (!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}

		if (sucursalService.buscarPorId(id_sucursal) == null) {
			redirectAttrs.addFlashAttribute("error", "Sucursal con ese ID no existe");

			return "redirect:/";
		}

		if (empresaService.listar_todo().size() == 0 || sucursalService.listar().size() == 0) {

			redirectAttrs.addFlashAttribute("error", "No hay empresa o sucursales registradas");

			return "redirect:/";
		}

		model.addAttribute("empresa", empresaService.listar_todo().get(0));
		model.addAttribute("usuario", obtenerUsuario());
		model.addAttribute("sucursal", sucursalService.buscarPorId(id_sucursal));
		
		model.addAttribute("notificaciones", planPagoService.listarTodo());

		editar = true;

		return "sucursales/registrar";
	}

	@GetMapping("/listar")
	public String listar(Model model, RedirectAttributes redirectAttrs) {

		if (!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}

		if (empresaService.listar_todo().size() == 0) {

			redirectAttrs.addFlashAttribute("error", "No hay ninguna empresa registrada");

			return "redirect:/";
		}

		model.addAttribute("empresa", empresaService.listar_todo().get(0));
		model.addAttribute("usuario", obtenerUsuario());
		model.addAttribute("sucursales", sucursalService.listar());
		
		model.addAttribute("notificaciones", planPagoService.listarTodo());

		return "sucursales/listar";
	}

	@PreAuthorize("hasAuthority('Admin')")
	@PostMapping("/registrar")
	public String guardar(@Valid Sucursal sucursal, RedirectAttributes redirectAttrs) {

		if (empresaService.listar_todo().size() == 0) {

			redirectAttrs.addFlashAttribute("error", "No hay ninguna empresa registrada");

			return "redirect:/";
		}

		if (!sucursalService.existente(sucursal, editar)) {

			if (!editar) {

				boolean existe_casa_central = false;

				for (Sucursal suc : sucursalService.listar()) {
					if (suc.isEs_casa_central()) {
						existe_casa_central = true;
						break;
					}
				}

				sucursal.setEmpresa(empresaService.listar_todo().get(0));
				
				if(existe_casa_central) {
					sucursal.setEs_casa_central(false);
				}else {
					sucursal.setEs_casa_central(true);
				}
				
			
				sucursalService.guardar(sucursal, true);

				Actividad_Usuario actividad = new Actividad_Usuario();
				
				if(sucursal.isEs_casa_central()) {
					Usuario_Sucursal usuario_sucursal = new Usuario_Sucursal();
					usuario_sucursal.setSucursal(sucursal);
					usuario_sucursal.setUsuario(obtenerUsuario());
					usuarioSucursalService.guardar(usuario_sucursal);
					
					actividad.setUsuario(usuario_sucursal);
				} else {
					actividad.setUsuario(obtenerUsuario().getUsuarios_sucursales().get(0));
				}

			
				
				actividad.setFecha(new Date());
				actividad.setHora(new Date());
				actividad.setDescripcion("Alta Sucursal " + sucursal.getId_sucursal() + " por usuario: "
						+ obtenerUsuario().getUsername());

				actividadService.guardar_actividad(actividad);

				Historial_Sucursal historial = new Historial_Sucursal();
				historial.setActividad_usuario(actividad);
				historial.setSucursal(sucursal);

				sucursalService.guardar_historial(historial);

			} else {
				sucursalService.guardar(sucursal, sucursal.isActivo());

			}

		} else {
			redirectAttrs.addFlashAttribute("error",
					"Se están repitiendo los datos con una sucursal que ya existe en el Sistema");
		}

		return "redirect:/sucursales/listar";
	}

	@PreAuthorize("hasAuthority('Admin')")
	@GetMapping("/deshabilitar/{id_sucursal}")
	public String deshabilitar(@PathVariable long id_sucursal, RedirectAttributes redirectAttrs, Model model) {

		if (sucursalService.buscarPorId(id_sucursal) == null) {
			redirectAttrs.addFlashAttribute("error", "Sucursal con ese ID no existe");

			return "redirect:/";
		}

		if (!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}

		if (empresaService.listar_todo().size() == 0 || sucursalService.listar().size() == 0) {

			redirectAttrs.addFlashAttribute("error", "No hay empresa o sucursales registradas");

			return "redirect:/";
		}

		Sucursal sucursal = sucursalService.buscarPorId(id_sucursal);

		Usuario_Sucursal usuario_sucursal = new Usuario_Sucursal();

		usuario_sucursal.setUsuario(obtenerUsuario());
		usuario_sucursal.setSucursal(sucursal);

		usuarioSucursalService.guardar(usuario_sucursal);

		Actividad_Usuario actividad = new Actividad_Usuario();

		actividad.setFecha(new Date());
		actividad.setHora(new Date());
		actividad.setUsuario(usuario_sucursal);

		if (sucursal.isActivo()) {
			sucursalService.deshabilitar(sucursal, false);
			actividad.setDescripcion("Sucursal " + sucursal.getId_sucursal() + " dado de baja por usuario: "
					+ obtenerUsuario().getUsername());
		} else {
			sucursalService.deshabilitar(sucursal, true);
			actividad.setDescripcion("Sucursal " + sucursal.getId_sucursal() + " dado de alta por usuario: "
					+ obtenerUsuario().getUsername());
		}

		actividadService.guardar_actividad(actividad);
		
		model.addAttribute("notificaciones", planPagoService.listarTodo());

		return "redirect:/sucursales/listar";
	}

	private Usuario obtenerUsuario() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		System.out.println(userDetail.getUsername());

		return usuarioRepo.findByUsername(userDetail.getUsername());
	}

}
