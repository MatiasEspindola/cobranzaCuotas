package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Cliente_Repo;

@Service
public class ClienteService implements I_Cliente_Service {

	@Autowired
	private I_Cliente_Repo repo;
	
	@Override
	public List<Cliente> listarTodo() {
		// TODO Auto-generated method stub
		return (List<Cliente>) repo.findAll();
	}

	@Override
	public Cliente buscarPorId(Long id_cliente) {
		// TODO Auto-generated method stub
		return repo.findById(id_cliente).orElse(null);
	}

	@Override
	public void guardar(Cliente cliente) {
		// TODO Auto-generated method stub
		repo.save(cliente);
	}
	
	@Override
	public void eliminar(Cliente cliente) {
		// TODO Auto-generated method stub
		repo.delete(cliente);
	}

	@Override
	public List<Cliente> buscarPorTerm(String term) {
		// TODO Auto-generated method stub
		return repo.buscarPorTerm(term);
	}

}
