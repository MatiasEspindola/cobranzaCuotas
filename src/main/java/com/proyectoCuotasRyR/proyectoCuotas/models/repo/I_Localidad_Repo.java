package com.proyectoCuotasRyR.proyectoCuotas.models.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Localidad;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Provincia;

public interface I_Localidad_Repo extends CrudRepository<Localidad, Integer> {

	@Query("Select l from Localidad l where l.provincia = ?1")
	public List<Localidad> buscarPorProvincia(Provincia provincia);
	
	@Query("Select l from Localidad l where l.localidad like %?1%")
	public List<Localidad> buscarPorTerm(String term);

}
