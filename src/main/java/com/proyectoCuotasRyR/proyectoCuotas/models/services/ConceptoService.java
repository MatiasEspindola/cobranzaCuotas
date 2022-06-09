package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Concepto;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Concepto_Repo;

@Service
public class ConceptoService implements I_Concepto_Service {

	@Autowired
	private I_Concepto_Repo repo;
	
	@Override
	public List<Concepto> listar() {
		// TODO Auto-generated method stub
		return (List<Concepto>) repo.findAll();
	}

}
