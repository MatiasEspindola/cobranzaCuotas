package com.proyectoCuotasRyR.proyectoCuotas.models.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cliente;

public interface I_Cliente_Repo extends CrudRepository<Cliente, Long> {

	@Query("Select c From Cliente c where c.cliente like %?1% or c.nro_documento like %?1%")
	public List<Cliente> buscarPorTerm(String term);
	
}
