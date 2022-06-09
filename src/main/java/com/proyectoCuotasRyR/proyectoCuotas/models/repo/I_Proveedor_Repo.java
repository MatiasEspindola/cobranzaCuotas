package com.proyectoCuotasRyR.proyectoCuotas.models.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Proveedor;

public interface I_Proveedor_Repo extends CrudRepository<Proveedor, Long> {

	@Query("Select p From Proveedor p where p.razon_social like %?1%")
	public List<Proveedor> buscarPorTerm(String term);
	
}
