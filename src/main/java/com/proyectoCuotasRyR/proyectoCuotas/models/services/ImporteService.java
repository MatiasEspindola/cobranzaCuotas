package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Importe;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Importe_Repo;

@Service
public class ImporteService implements I_Importe_Service {

	@Autowired
	private I_Importe_Repo repo;
	
	@Override
	public void guardar(Importe importe) {
		// TODO Auto-generated method stub
		repo.save(importe);
	}

}
