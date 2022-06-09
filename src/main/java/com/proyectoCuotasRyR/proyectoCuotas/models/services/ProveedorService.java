package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Proveedor;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Proveedor_Repo;

@Service
public class ProveedorService implements I_Proveedor_Service {

	@Autowired
	private I_Proveedor_Repo repo;
	
	@Override
	public List<Proveedor> listarTodo() {
		// TODO Auto-generated method stub
		return (List<Proveedor>) repo.findAll();
	}

	@Override
	public Proveedor buscarPorId(Long id_proveedor) {
		// TODO Auto-generated method stub
		return repo.findById(id_proveedor).orElse(null);
	}

	@Override
	public void guardar(Proveedor proveedor) {
		// TODO Auto-generated method stub
		repo.save(proveedor);
	}
	
	@Override
	public void eliminar(Proveedor proveedor) {
		// TODO Auto-generated method stub
		repo.delete(proveedor);
	}

	@Override
	public List<Proveedor> buscarPorTerm(String term) {
		// TODO Auto-generated method stub
		return repo.buscarPorTerm(term);
	}

}
