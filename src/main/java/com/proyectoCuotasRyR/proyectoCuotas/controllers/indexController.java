package com.proyectoCuotasRyR.proyectoCuotas.controllers;

import java.net.MalformedURLException;
import java.util.Date;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

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
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cuota;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Empresa;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario_Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Sucursal_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Cuota_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Empresa_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Sucursal_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_UploadFile_Service;

@Controller
public class indexController {

	@Autowired
	private I_Usuario_Repo usuarioRepo;

	@Autowired
	private I_Empresa_Service empresaService;

	@Autowired
	private I_UploadFile_Service upl;

	@Autowired
	private I_Sucursal_Service sucursalService;
	
	@Autowired
	private I_Cuota_Service cuotaService;

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

	@GetMapping("/inactivo")
	public String inactivo() {

		return "inactivo";
	}

	@GetMapping({ "/", "/index" })
	public String index(Model model, @RequestParam(value = "logout", required = false) String logout,
			RedirectAttributes redirectAttrs) {

		if(!obtenerUsuario().isActivo()) {
			return "redirect:/inactivo";
		}
		
		
		Empresa empresa = null;
		if(empresaService.listar_todo().size() > 0) {
			empresa	= empresaService.listar_todo().get(0);
		}

		model.addAttribute("empresa", empresa);
		model.addAttribute("usuario", obtenerUsuario());
		
		if(cuotaService.listarTodo().size() > 0) {
			
			for(Cuota cuota : cuotaService.listarTodo()) {
				if(!cuota.isPagado()) {
					if(!cuota.isVencida()) {
						Date hoy = new Date();
						Date vencimiento_cuota = cuota.getFecha();
						
						if(hoy.after(vencimiento_cuota)) {
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
			
		}

		return "index";
	}

	

	private Usuario obtenerUsuario() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		System.out.println(userDetail.getUsername());

		return usuarioRepo.findByUsername(userDetail.getUsername());
	}
	
	

}
