package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Tipo_Comprobante;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Tipo_Comprobante_Repo;

@Service
public class Tipo_Comprobante_Service implements I_Tipo_Comprobante_Service {

	@Autowired
	private I_Tipo_Comprobante_Repo repo;
	
	@Override
	public List<Tipo_Comprobante> listar() {
		// TODO Auto-generated method stub
		return (List<Tipo_Comprobante>) repo.findAll();
	}

	@Override
	public Tipo_Comprobante buscarPorId(long id_tipo_comprobante) {
		// TODO Auto-generated method stub
		return repo.findById(id_tipo_comprobante).orElse(null);
	}

}
