package com.proyectoCuotasRyR.proyectoCuotas.models.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Authority;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;

public interface I_Authority_Repo extends CrudRepository<Authority, Long> {
	
	@Query("Select a from Authority a where a.id_usuario_auth = ?1")
	public Authority buscarPorUsuario(Usuario usuario);

}
