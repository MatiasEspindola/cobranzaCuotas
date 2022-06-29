package com.proyectoCuotasRyR.proyectoCuotas.auth.handler;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;

public class PagePopulationInterceptor extends HandlerInterceptorAdapter{
	
	@Autowired
	private I_Usuario_Repo usuarioRepo;
	
	 @Override
	  public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
	        if(modelAndView != null) {
	        	 modelAndView.addObject("usuario", obtenerUsuario());
	        }
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
