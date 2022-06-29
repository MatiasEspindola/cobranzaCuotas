package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Authority;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Repo;


@Service("jpaUserDetailsService")
public class UsuarioService implements UserDetailsService{

	@Autowired
	private I_Usuario_Repo usuarioDao;
	
	private Logger logger = LoggerFactory.getLogger(UsuarioService.class);
	
	@Transactional(readOnly=true)
	public void guardar(Usuario usuario) {
		usuarioDao.save(usuario);
	}
	
	@Transactional(readOnly=true)
	public Usuario buscarPorId(long id_usuario) {
		return usuarioDao.findById(id_usuario).orElse(null);
	}
	
	@Override
	@Transactional(readOnly=true)
	public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
		
        Usuario usuario = usuarioDao.findByUsername(username);
        
       
        if(usuario == null) {
        	logger.error("Error en el Login: no existe el usuario '" + username + "' en el sistema!");
        	throw new UsernameNotFoundException("Username: " + username + " no existe en el sistema!");
        }
        
        List<GrantedAuthority> authorities
        = new ArrayList<>();
        
      for (Authority role: usuario.getAuthorities()) {
          authorities.add(new SimpleGrantedAuthority(role.getId_rol_auth().getRol()));
      }
        
        
        if(authorities.isEmpty()) {
        	logger.error("Error en el Login: Usuario '" + username + "' no tiene roles asignados!");
        	throw new UsernameNotFoundException("Error en el Login: usuario '" + username + "' no tiene roles asignados!");
        }
        
		return new User(usuario.getUsername(), usuario.getPassword(), true, true, true, true, authorities);
	}
	
	String inactivo() {
		return "redirect:/login?logout";
	}
	
}
