package com.proyectoCuotasRyR.proyectoCuotas.controllers;

import java.io.IOException;
import java.net.MalformedURLException;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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
import org.springframework.web.multipart.MultipartFile;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Empresa;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Localidad;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Provincia;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Empresa_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_GeoService;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Responsable_Iva_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_UploadFile_Service;

@Controller
@SessionAttributes("empresa")
@RequestMapping("/empresas")
public class empresaController {

	@Autowired
	private I_Empresa_Service empresaService;

	@Autowired
	private I_Responsable_Iva_Service responsableIvaService;

	@Autowired
	private I_Usuario_Repo usuarioRepo;

	@Autowired
	private I_GeoService geoService;

	@Autowired
	private I_UploadFile_Service upl;

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

	@GetMapping("/registrar")
	public String formulario(Model model) {
		
		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Usuario"))) {
			return "redirect:/401";
		}

		if (empresaService.listar_todo().size() > 0) {
			return "redirect:/index";
		}

		Empresa empresa = new Empresa();

		model.addAttribute("responsables_iva", responsableIvaService.listar_todo());

		model.addAttribute("empresa", empresa);

		model.addAttribute("usuario", obtenerUsuario());

		return "empresas/registrar";
	}

	@GetMapping("/registrar/{id_empresa}")
	public String formulario(Model model, @PathVariable long id_empresa) {
		
		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Usuario"))) {
			return "redirect:/401";
		}

		Empresa empresa = empresaService.buscarPorId(id_empresa);

		model.addAttribute("responsables_iva", responsableIvaService.listar_todo());

		model.addAttribute("empresa", empresa);

		model.addAttribute("usuario", obtenerUsuario());

		return "empresas/registrar";
	}

	@PostMapping("/registrar")
	public String guardar(@Valid Empresa empresa, @RequestParam("file") MultipartFile foto) {

		if (!foto.isEmpty()) {
			if (empresa.getId_empresa() > 0 && empresa.getLogo() != null && empresa.getLogo().length() > 0) {
				upl.delete(empresa.getLogo());
			}
			String uniqueFilename = null;

			try {
				uniqueFilename = upl.copy(foto);
			} catch (IOException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}

			empresa.setLogo(uniqueFilename);
		}

		empresaService.guardar(empresa);

		return "redirect:/";
	}

	private Usuario obtenerUsuario() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		System.out.println(userDetail.getUsername());

		return usuarioRepo.findByUsername(userDetail.getUsername());
	}

}
