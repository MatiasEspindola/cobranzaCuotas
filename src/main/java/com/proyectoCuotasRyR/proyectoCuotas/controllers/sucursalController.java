package com.proyectoCuotasRyR.proyectoCuotas.controllers;
import java.net.MalformedURLException;
import java.util.Date;
import java.util.List;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
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

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Actividad_Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Localidad;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Provincia;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Actividad_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Empresa_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_GeoService;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Sucursal_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_UploadFile_Service;

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
	
	@GetMapping("/registrar")
	public String registrar(Model model) {
		
		if(empresaService.listar_todo().size() == 0) {
			return "redirect:/empresas/registrar"; 
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Usuario"))) {
			return "redirect:/401";
		}
		
		model.addAttribute("empresa", empresaService.listar_todo().get(0));
		model.addAttribute("usuario", obtenerUsuario());
		model.addAttribute("sucursal", new Sucursal());
		
		editar = false;
		
		return "sucursales/registrar";
	}
	
	@GetMapping("/registrar/{id_sucursal}")
	public String registrar(Model model, @PathVariable long id_sucursal) {
		
		if(empresaService.listar_todo().size() == 0) {
			return "redirect:/empresas/registrar"; 
		}
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Usuario"))) {
			return "redirect:/401";
		}
		
		model.addAttribute("empresa", empresaService.listar_todo().get(0));
		model.addAttribute("usuario", obtenerUsuario());
		model.addAttribute("sucursal", sucursalService.buscarPorId(id_sucursal));
		
		editar = true;
		
		return "sucursales/registrar";
	}
	
	@GetMapping("/listar")
	public String listar(Model model) {
		
		if(empresaService.listar_todo().size() == 0) {
			return "redirect:/empresas/registrar"; 
		}
		
		model.addAttribute("empresa", empresaService.listar_todo().get(0));
		model.addAttribute("usuario", obtenerUsuario());
		model.addAttribute("sucursales", sucursalService.listar());
		
		return "sucursales/listar";
	}
	
	@PostMapping("/registrar")
	public String guardar(@Valid Sucursal sucursal) {
		
		if(!sucursalService.existente(sucursal, editar)) {
			
			if(!editar) {
				sucursal.setEmpresa(empresaService.listar_todo().get(0));
				sucursalService.guardar(sucursal, true);
				
				Actividad_Usuario actividad = new Actividad_Usuario();
				actividad.setFecha(new Date());
				actividad.setHora(new Date());
				actividad.setDescripcion("Alta Sucursal " + sucursal.getId_sucursal() + " por usuario: " + obtenerUsuario().getUsername());
				actividad.setUsuario(obtenerUsuario());
				
				actividadService.guardar_actividad(actividad);
				
				Historial_Sucursal historial = new Historial_Sucursal();
				historial.setActividad_usuario(actividad);
				historial.setSucursal(sucursal);
				
				sucursalService.guardar_historial(historial);
				
			}else {
				sucursalService.guardar(sucursal, sucursal.isActivo());
				
			}
			
		}
		
		return "redirect:/sucursales/listar";
	}
	
	@GetMapping("/deshabilitar/{id_sucursal}")
	public String deshabilitar(@PathVariable long id_sucursal){
		
		Authentication auth = SecurityContextHolder.getContext().getAuthentication();
		if (auth != null && auth.getAuthorities().stream().anyMatch(a -> a.getAuthority().equals("Usuario"))) {
			return "redirect:/401";
		}
		
		Sucursal sucursal = sucursalService.buscarPorId(id_sucursal);
		
		Actividad_Usuario actividad = new Actividad_Usuario();
		
		actividad.setFecha(new Date());
		actividad.setHora(new Date());
		actividad.setUsuario(obtenerUsuario());
		
		
		if(sucursal.isActivo()) {
			sucursalService.deshabilitar(sucursal, false);
			actividad.setDescripcion("Sucursal " + sucursal.getId_sucursal() + " dado de baja por usuario: " + obtenerUsuario().getUsername());
		}else {
			sucursalService.deshabilitar(sucursal, true);
			actividad.setDescripcion("Sucursal " + sucursal.getId_sucursal() + " dado de alta por usuario: " + obtenerUsuario().getUsername());
		}
		
		actividadService.guardar_actividad(actividad);
		
		return "redirect:/sucursales/listar";
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
