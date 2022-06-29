package com.proyectoCuotasRyR.proyectoCuotas.controllers;

import java.net.MalformedURLException;
import java.util.Date;

import javax.validation.Valid;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.SessionAttributes;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Empresa;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Pregunta;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario_Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Empresa_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Sucursal_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_UploadFile_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Usuario_Sucursal_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.PreguntaService;

@Controller
@RequestMapping("/configuraciones")
@SessionAttributes("usuario_sucursal")
public class configuracionController {
	
	@Autowired
	private I_Empresa_Service empresaService;
	
	@Autowired
	private I_Usuario_Repo usuarioRepo;
	
	@Autowired
    private I_UploadFile_Service upl;
	
	@Autowired
	private BCryptPasswordEncoder passwordEncoder;
	
	@Autowired
	private I_Usuario_Sucursal_Service usuarioSucursalService;
	
	@Autowired
	private I_Sucursal_Service sucursalService;
	
	@Autowired
	private PreguntaService preguntaService;
	
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
	
	@GetMapping("/configuracion")
	public String configuracion(Model model) {
	
		Empresa empresa = null;
		
		if(empresaService.listar_todo().size() > 0) {
			 empresa = empresaService.listar_todo().get(0);
			 
				Usuario_Sucursal usuario_sucursal = new Usuario_Sucursal();
				model.addAttribute("usuario_sucursal", usuario_sucursal);
				model.addAttribute("sucursales", sucursalService.listar());
		}
		
		model.addAttribute("empresa", empresa);
		
		Usuario usuario = obtenerUsuario();
		
		model.addAttribute("usuario", usuario);
		
		model.addAttribute("preguntas", preguntaService.listar());
		
	
	
	
		
		return "configuraciones/configuracion";
	}
	
	@PostMapping("/configuracion")
	public String guardar(@RequestParam(name="pass_nue") String pass_nue, @RequestParam(name="pass_ant") String pass_ant,
			@RequestParam(name="pregunta") Pregunta pregunta,
			@RequestParam(name="pass_ant") String respuesta,
			RedirectAttributes redirectAttrs) {
		
		
		Usuario usuario = obtenerUsuario();
		
		if(!usuario.getPass2().equals(pass_ant)) {
			redirectAttrs.addFlashAttribute("error", "La antigüa contraseña ingresada es incorrecta");
			return "redirect:/configuraciones/configuracion";
			
		}else {
			redirectAttrs.addFlashAttribute("success", "Los datos de su usuario han sido modificado con éxito");
			usuario.setPass2(pass_nue);
			
			String bcryptPassword = passwordEncoder.encode(pass_nue);
			System.out.println(bcryptPassword);
			usuario.setPassword(bcryptPassword);
			usuario.setId_pregunta(pregunta);
			usuario.setRespuesta(respuesta);
			usuarioRepo.save(usuario);
		}
		
		return "redirect:/";
	}
	
	
	
	@PostMapping("/asignar_sucursal")
	public String guardar(@Valid Usuario_Sucursal usuario_sucursal) {
		
		
	
		usuario_sucursal.setUsuario(obtenerUsuario());
		
		usuarioSucursalService.guardar(usuario_sucursal);
		
		return "redirect:/";
		
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
