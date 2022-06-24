package com.proyectoCuotasRyR.proyectoCuotas.controllers;

import java.util.Date;
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

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Actividad_Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Proveedor;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Localidad;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Proveedor;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Provincia;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Actividad_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Empresa_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_GeoService;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Proveedor_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Tipo_Documento_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_UploadFile_Service;

@Controller
@SessionAttributes("proveedor")
@RequestMapping("/proveedores")
public class proveedorController {

	@Autowired
	private I_Proveedor_Service proveedor_Service;

	@Autowired
	private I_Tipo_Documento_Service tipoDocumentoService;

	@Autowired
	private I_Usuario_Repo usuarioRepo;

	private boolean editar;

	@Autowired
	private I_GeoService geoService;

	@Autowired
	private I_Empresa_Service empresaService;

	@Autowired
	private I_Actividad_Service actividadService;

	@GetMapping(value = "/cargar_proveedor/{term}", produces = { "application/json" })
	public @ResponseBody List<Proveedor> autocompleteProveedor(String term) {
		return proveedor_Service.buscarPorTerm(term);
	}

	@GetMapping(value = "/provincias", produces = { "application/json" })
	public @ResponseBody List<Provincia> provincias() {
		return geoService.provincias();
	}

	@GetMapping(value = "/localidades", produces = { "application/json" })
	public @ResponseBody List<Localidad> localidades(@RequestParam String id_provincia) {
		return geoService.buscarPorId(Integer.valueOf(id_provincia)).getLocalidades();
	}

	@GetMapping("/registrar/{id}")
	public String formulario(Model model, @PathVariable(name = "id") long id_proveedor) {
		
		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}

		if (empresaService.listar_todo().size() == 0) {

			return "redirect:/empresas/registrar";
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Usuario"))) {
			return "redirect:/401";
		}

		model.addAttribute("proveedor", proveedor_Service.buscarPorId(id_proveedor));

		model.addAttribute("tipos_documentos", tipoDocumentoService.listarTodo());

		model.addAttribute("usuario", obtenerUsuario());

		model.addAttribute("empresa", empresaService.listar_todo().get(0));

		editar = true;

		return "proveedores/registrar";
	}

	@GetMapping("/deshabilitar/{id_proveedor}")
	public String deshabilitar(Model model, @PathVariable long id_proveedor) {
		
		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}

		if (empresaService.listar_todo().size() == 0) {

			return "redirect:/empresas/registrar";
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Usuario"))) {
			return "redirect:/401";
		}

		Proveedor proveedor = proveedor_Service.buscarPorId(id_proveedor);

		Actividad_Usuario actividad = new Actividad_Usuario();

		actividad.setFecha(new Date());
		actividad.setHora(new Date());
		actividad.setUsuario(obtenerUsuario());

		if (proveedor.isActivo()) {
			proveedor_Service.deshabilitar(proveedor, false);
			actividad.setDescripcion("Proveedor " + proveedor.getId_proveedor() + " dado de baja por usuario: "
					+ obtenerUsuario().getUsername());
		} else {
			proveedor_Service.deshabilitar(proveedor, true);
			actividad.setDescripcion("Proveedor " + proveedor.getId_proveedor() + " dado de alta por usuario: "
					+ obtenerUsuario().getUsername());
		}

		actividadService.guardar_actividad(actividad);

		return "redirect:/proveedores/listar";
	}

	@GetMapping("/listar")
	public String listar(Model model) {
		
		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}

		if (empresaService.listar_todo().size() == 0) {

			return "redirect:/empresas/registrar";
		}

		model.addAttribute("proveedores", proveedor_Service.listarTodo());

		model.addAttribute("usuario", obtenerUsuario());

		model.addAttribute("empresa", empresaService.listar_todo().get(0));

		return "proveedores/listar";
	}

	@GetMapping("/registrar")
	public String formulario(Model model) {
		
		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}

		if (empresaService.listar_todo().size() == 0) {

			return "redirect:/empresas/registrar";
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Usuario"))) {
			return "redirect:/401";
		}

		model.addAttribute("proveedor", new Proveedor());

		model.addAttribute("tipos_documentos", tipoDocumentoService.listarTodo());

		model.addAttribute("usuario", obtenerUsuario());

		model.addAttribute("empresa", empresaService.listar_todo().get(0));

		editar = false;

		return "proveedores/registrar";
	}

	@PostMapping("/registrar")
	public String guardar(@Valid Proveedor proveedor, RedirectAttributes redirAttrs) {

		if (!proveedor_Service.existente(proveedor, editar)) {
			if (!editar) {
				proveedor_Service.guardar(proveedor, true);

				Actividad_Usuario actividad = new Actividad_Usuario();
				actividad.setFecha(new Date());
				actividad.setHora(new Date());
				actividad.setUsuario(obtenerUsuario());
				actividad.setDescripcion("Alta proveedor " + proveedor.getId_proveedor() + " por usuario: "
						+ obtenerUsuario().getUsername());

				actividadService.guardar_actividad(actividad);

				Historial_Proveedor historial = new Historial_Proveedor();
				historial.setActividad_usuario(actividad);
				historial.setProveedor(proveedor);

				proveedor_Service.guardar_historial(historial);

			} else {
				proveedor_Service.guardar(proveedor, proveedor.isActivo());
			}
		}

		return "redirect:/proveedores/listar";
	}

	private Usuario obtenerUsuario() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		System.out.println(userDetail.getUsername());

		return usuarioRepo.findByUsername(userDetail.getUsername());
	}

}
