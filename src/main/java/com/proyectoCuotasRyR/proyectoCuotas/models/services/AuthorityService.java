package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Authority;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Authority_Repo;

@Service
public class AuthorityService implements I_Authority_Service {

	@Autowired
	private I_Authority_Repo repo;
	
	@Override
	@Transactional
	public void guardar(Authority authority) {
		// TODO Auto-generated method stub
		repo.save(authority);
	}

	@Override
	public Authority buscarPorUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return repo.buscarPorUsuario(usuario);
	}

}
