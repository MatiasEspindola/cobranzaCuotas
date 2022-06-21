package com.proyectoCuotasRyR.proyectoCuotas.models.repo;

import java.util.List;

import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.CtaCteCliente;

public interface I_CtaCteCliente_Repo extends CrudRepository<CtaCteCliente, Long> {

	@Query("Select c From CtaCteCliente c where id_cliente = ?1")
	public List<CtaCteCliente> buscarPorCliente(Cliente cliente);
	
}
