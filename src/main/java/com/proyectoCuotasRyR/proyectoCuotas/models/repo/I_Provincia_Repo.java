package com.proyectoCuotasRyR.proyectoCuotas.models.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Provincia;

public interface I_Provincia_Repo extends CrudRepository<Provincia, Integer> {

	@Query("Select p from Provincia p where p.id = ?1")
	public Provincia buscarPorId(int id);

}
