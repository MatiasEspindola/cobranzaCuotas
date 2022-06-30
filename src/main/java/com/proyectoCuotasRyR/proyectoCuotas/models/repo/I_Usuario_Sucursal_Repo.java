package com.proyectoCuotasRyR.proyectoCuotas.models.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario_Sucursal;

public interface I_Usuario_Sucursal_Repo extends CrudRepository<Usuario_Sucursal, Long> {

	@Query("Select u From Usuario_Sucursal u where u.usuario = ?1")
	public List<Usuario_Sucursal> buscarPorUsuario(Usuario usuario); 
	
	@Query("Select u From Usuario_Sucursal u where u.sucursal = ?1")
	public List<Usuario_Sucursal>  buscarPorSucursal(Sucursal sucursal); 
	
}
