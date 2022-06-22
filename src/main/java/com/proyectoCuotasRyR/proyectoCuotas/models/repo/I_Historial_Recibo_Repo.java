package com.proyectoCuotasRyR.proyectoCuotas.models.repo;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Recibo;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Recibo;

public interface I_Historial_Recibo_Repo extends CrudRepository<Historial_Recibo, Long> {
	
	@Query("Select historial From Historial_Recibo historial where historial.recibo = ?1")
	public Historial_Recibo buscarPorRecibo(Recibo recibo);

}
