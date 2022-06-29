package com.proyectoCuotasRyR.proyectoCuotas.auth.handler;

import java.io.IOException;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.SimpleUrlAuthenticationSuccessHandler;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.FlashMap;
import org.springframework.web.servlet.support.SessionFlashMapManager;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;

@Component
public class LoginSuccessHandler extends SimpleUrlAuthenticationSuccessHandler{




	@Override
	public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
			Authentication authentication) throws IOException, ServletException {

		SessionFlashMapManager flashMapManager = new SessionFlashMapManager();
		
		FlashMap flashMap = new FlashMap();
		
		
		
		flashMap.put("success", "Hola " +authentication.getName()+ ", iniciaste sesión con éxito!");
		
		flashMapManager.saveOutputFlashMap(flashMap, request, response);
		
		if(authentication != null) {
			
		
			
			logger.info("El usuario '"+authentication.getName()+"' ha iniciado sesión con éxito");
		}
		
		super.onAuthenticationSuccess(request, response, authentication);
	}
	

	
}
