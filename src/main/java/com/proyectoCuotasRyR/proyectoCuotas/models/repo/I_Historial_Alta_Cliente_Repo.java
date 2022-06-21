package com.proyectoCuotasRyR.proyectoCuotas.models.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.CtaCteCliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Alta_Cliente;

public interface I_Historial_Alta_Cliente_Repo extends CrudRepository<Historial_Alta_Cliente, Long> {

	@Query("Select historial From Historial_Alta_Cliente historial where historial.ctactecliente = ?1")
	public Historial_Alta_Cliente buscarPorCtaCte(CtaCteCliente ctactecliente);
	
}
