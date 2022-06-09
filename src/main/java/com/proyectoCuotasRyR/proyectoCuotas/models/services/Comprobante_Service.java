package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Comprobante;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Comprobante_Repo;

@Service
public class Comprobante_Service implements I_Comprobante_Service {

	@Autowired
	private I_Comprobante_Repo repo;
	
	@Override
	public List<Comprobante> listar() {
		// TODO Auto-generated method stub
		return (List<Comprobante>) repo.findAll();
	}

	@Override
	public Comprobante buscarPorId(long id_comprobante) {
		// TODO Auto-generated method stub
		return repo.findById(id_comprobante).orElse(null);
	}

	@Override
	public void guardar(Comprobante comprobante) {
		// TODO Auto-generated method stub
		repo.save(comprobante);
	}

	@Override
	public void eliminar(Comprobante comprobante) {
		// TODO Auto-generated method stub
		repo.delete(comprobante);
	}

}
