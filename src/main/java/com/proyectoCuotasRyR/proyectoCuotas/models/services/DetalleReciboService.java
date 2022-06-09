package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Detalle_Recibo;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_DetalleRecibo_Repo;

@Service
public class DetalleReciboService implements I_DetalleRecibo_Service {

	@Autowired
	private I_DetalleRecibo_Repo repo;
	
	@Override
	public List<Detalle_Recibo> listar() {
		// TODO Auto-generated method stub
		return (List<Detalle_Recibo>) repo.findAll();
	}

	@Override
	public Detalle_Recibo buscarPorId(long id_detalle_recibo) {
		// TODO Auto-generated method stub
		return repo.findById(id_detalle_recibo).orElse(null);
	}

	@Override
	public void guardar(Detalle_Recibo detalle_recibo) {
		// TODO Auto-generated method stub
		repo.save(detalle_recibo);
	}



}
