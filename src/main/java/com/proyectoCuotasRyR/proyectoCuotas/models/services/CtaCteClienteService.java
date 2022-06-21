package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.CtaCteCliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_CtaCteCliente_Repo;

@Service
public class CtaCteClienteService implements I_CtaCteCliente_Service {

	@Autowired
	private I_CtaCteCliente_Repo repo;
	
	@Override
	public List<CtaCteCliente> listar() {
		// TODO Auto-generated method stub
		return (List<CtaCteCliente>) repo.findAll();
	}

	@Override
	public CtaCteCliente buscarPorId(long id_ctactecliente) {
		// TODO Auto-generated method stub
		return repo.findById(id_ctactecliente).orElse(null);
	}

	@Override
	public void eliminar(CtaCteCliente ctactecliente) {
		// TODO Auto-generated method stub
		repo.delete(ctactecliente);
	}

	@Override
	public void guardar(CtaCteCliente ctactecliente) {
		// TODO Auto-generated method stub
		repo.save(ctactecliente);
	}

	@Override
	public List<CtaCteCliente> buscarPorCliente(Cliente cliente) {
		// TODO Auto-generated method stub
		return repo.buscarPorCliente(cliente);
	}

}
