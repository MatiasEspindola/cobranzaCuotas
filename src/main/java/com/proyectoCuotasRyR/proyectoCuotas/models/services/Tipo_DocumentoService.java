package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Tipo_Documento;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Tipo_Documento_Repo;

@Service
public class Tipo_DocumentoService implements I_Tipo_Documento_Service {

	@Autowired
	private I_Tipo_Documento_Repo repo;
	
	@Override
	public List<Tipo_Documento> listarTodo() {
		// TODO Auto-generated method stub
		return (List<Tipo_Documento>)repo.findAll();
	}

	@Override
	public Tipo_Documento buscarPorId(Long id_tipo_documento) {
		// TODO Auto-generated method stub
		return repo.findById(id_tipo_documento).orElse(null);
	}

	@Override
	public void guardar(Tipo_Documento tipo_documento) {
		// TODO Auto-generated method stub
		repo.save(tipo_documento);
	}

}
