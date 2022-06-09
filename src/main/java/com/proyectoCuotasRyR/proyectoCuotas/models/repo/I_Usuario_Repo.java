package com.proyectoCuotasRyR.proyectoCuotas.models.repo;

import org.springframework.data.repository.CrudRepository;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;

public interface I_Usuario_Repo extends CrudRepository<Usuario, Long> {

	public Usuario findByUsername(String username);
	
}
