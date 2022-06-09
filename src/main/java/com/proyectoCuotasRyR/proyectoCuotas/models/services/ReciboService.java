package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Recibo;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Recibo_Repo;

@Service
public class ReciboService implements I_Recibo_Service {

	@Autowired
	private I_Recibo_Repo repo;
	
	@Override
	public List<Recibo> listar() {
		// TODO Auto-generated method stub
		return (List<Recibo>) repo.findAll();
	}

	@Override
	public Recibo buscarPorId(long id_recibo) {
		// TODO Auto-generated method stub
		return repo.findById(id_recibo).orElse(null);
	}

	@Override
	public void borrar(Recibo recibo) {
		// TODO Auto-generated method stub
		repo.delete(recibo);
	}

	@Override
	public void guardar(Recibo recibo) {
		// TODO Auto-generated method stub
		repo.save(recibo);
	}

}
