package com.proyectoCuotasRyR.proyectoCuotas.controllers;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.net.MalformedURLException;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletResponse;

import org.apache.commons.io.IOUtils;
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

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Detalle_Recibo;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Recibo;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;

import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_ReciboPdf_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_Recibo_Service;
import com.proyectoCuotasRyR.proyectoCuotas.models.services.I_UploadFile_Service;

@Controller
@RequestMapping("recibos")
public class reciboController {

	@Autowired
	private I_Usuario_Repo usuarioRepo;

	@Autowired
	private I_Recibo_Service reciboService;

	@Autowired
	private I_ReciboPdf_Service reciboPdfService;
	

	@Autowired
    private I_UploadFile_Service upl;

	
	@GetMapping(value = "/uploads/empresas/{filename:.+}")
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

	@GetMapping("/ver/{id_recibo}")
	public String ver(@PathVariable long id_recibo, HttpServletResponse response, Model model) throws IOException {

		Usuario usuario = obtenerUsuario();

		/*Map<String, Object> data = new HashMap<>();

		data.put("usuario", usuario);
		data.put("recibo", reciboService.buscarPorId(id_recibo));
		data.put("cliente", reciboService.buscarPorId(id_recibo).getDetalles_recibos().get(0).getImporte().getCuota().getId_plan_pago().getId_cliente());
		data.put("total", reciboService.buscarPorId(id_recibo).getTotal());

	

		ByteArrayInputStream exportedData = reciboPdfService.exportReceiptPdf("recibos/ver", data);
		response.setContentType("application/octet-stream");
		response.setHeader("Content-Disposition", "attachment; filename=receipt.pdf");
		IOUtils.copy(exportedData, response.getOutputStream());*/
		
		model.addAttribute("usuario", usuario);
		model.addAttribute("recibo", reciboService.buscarPorId(id_recibo));
		model.addAttribute("cliente", reciboService.buscarPorId(id_recibo).getDetalles_recibos().get(0).getImporte().getCuota().getId_plan_pago().getId_cliente());
		model.addAttribute("total", reciboService.buscarPorId(id_recibo).getTotal());

		return "recibos/ver";
	}

	private Usuario obtenerUsuario() {

		Authentication auth = SecurityContextHolder.getContext().getAuthentication();

		UserDetails userDetail = (UserDetails) auth.getPrincipal();

		System.out.println(userDetail.getUsername());

		return usuarioRepo.findByUsername(userDetail.getUsername());
	}

}