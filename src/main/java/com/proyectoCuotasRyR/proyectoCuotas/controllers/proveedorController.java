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

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Localidad;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Proveedor;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Provincia;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;
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
	
	@GetMapping("/listar")
	public String listar(Model model) {

		model.addAttribute("proveedores", proveedor_Service.listarTodo());

		model.addAttribute("usuario", obtenerUsuario());

		return "proveedores/listar";
	}
	
	@GetMapping("/detalles/{id}")
	public String detalles(Model model, @PathVariable(name = "id") long id_proveedor) {

		model.addAttribute("proveedor", proveedor_Service.buscarPorId(id_proveedor));
		model.addAttribute("usuario", obtenerUsuario());

		return "proveedores/detalles";
	}

	@GetMapping("/formulario/{id}")
	public String formulario(Model model, @PathVariable(name = "id") long id_proveedor) {

		model.addAttribute("proveedor", proveedor_Service.buscarPorId(id_proveedor));
		
		model.addAttribute("tipos_documentos", tipoDocumentoService.listarTodo());

		model.addAttribute("usuario", obtenerUsuario());
		
		editar = true;

		return "proveedores/formulario";
	}
	
	@GetMapping("/eliminar/{id}")
	public String eliminar(Model model, @PathVariable(name = "id") long id_proveedor) {

		proveedor_Service.eliminar(proveedor_Service.buscarPorId(id_proveedor));

		return "redirect:/proveedores/listar";
	}


	@GetMapping("/formulario")
	public String formulario(Model model) {

		model.addAttribute("proveedor", new Proveedor());
		
		model.addAttribute("tipos_documentos", tipoDocumentoService.listarTodo());

		model.addAttribute("usuario", obtenerUsuario());
		
		editar = false;

		return "proveedores/formulario";
	}

	@PostMapping("/formulario")
	public String guardar(@Valid Proveedor proveedor, RedirectAttributes redirAttrs) {

		if (proveedor_Service.listarTodo().size() > 0) {
			for (Proveedor prov : proveedor_Service.listarTodo()) {
				if(editar) {
					if(prov.getId_proveedor() != proveedor.getId_proveedor()) {
						if (prov.getRazon_social().equals(proveedor.getRazon_social())) {
							if (prov.getId_localidad4().getId() == proveedor.getId_localidad4().getId()) {
								redirAttrs.addFlashAttribute("error",
										"Error, ya se encuentra el proveedor "+proveedor.getRazon_social()+" ("+proveedor.getId_localidad4().getLocalidad()+", "+proveedor.getId_localidad4().getProvincia().getProv()+") "+" registrado en el Sistema");
								
								return "redirect:/proveedores/listar";
							}
						}
					}
				}else {
					if (prov.getRazon_social().equals(proveedor.getRazon_social())) {
						if (prov.getId_localidad4().getId() == proveedor.getId_localidad4().getId()) {
							redirAttrs.addFlashAttribute("error",
									"Error, ya se encuentra el proveedor "+proveedor.getRazon_social()+" ("+proveedor.getId_localidad4().getLocalidad()+", "+proveedor.getId_localidad4().getProvincia().getProv()+") "+" registrado en el Sistema");
							
							return "redirect:/proveedores/listar";
						}
					}
				}
			}
		}

		proveedor.setActivo(true);
		proveedor_Service.guardar(proveedor);

		return "redirect:/proveedores/listar";
	}

	private Usuario obtenerUsuario() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		System.out.println(userDetail.getUsername());

		return usuarioRepo.findByUsername(userDetail.getUsername());
	}

	
}
