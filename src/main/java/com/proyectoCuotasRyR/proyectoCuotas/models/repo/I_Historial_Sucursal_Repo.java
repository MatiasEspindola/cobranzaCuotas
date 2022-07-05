package com.proyectoCuotasRyR.proyectoCuotas.models.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Sucursal;

public interface I_Historial_Sucursal_Repo extends CrudRepository<Historial_Sucursal, Long>{

	@Query("Select h From Historial_Sucursal h where h.sucursal = ?1")
	public Historial_Sucursal buscarPorSucursal(Sucursal sucursal);
	
	
}
