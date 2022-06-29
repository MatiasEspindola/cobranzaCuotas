package com.proyectoCuotasRyR.proyectoCuotas.models.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario_Sucursal;

public interface I_Usuario_Sucursal_Repo extends CrudRepository<Usuario_Sucursal, Long> {

	@Query("Select u From Usuario_Sucursal u where u.usuario = ?1")
	public Usuario_Sucursal buscarPorUsuario(Usuario usuario); 
	
}
