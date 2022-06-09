package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Empresa;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Empresa_Repo;

@Service
public class EmpresaService implements I_Empresa_Service {

	@Autowired
	private I_Empresa_Repo repo;
	
	@Override
	public List<Empresa> listar_todo() {
		// TODO Auto-generated method stub
		return (List<Empresa>) repo.findAll();
	}

	@Override
	public Empresa buscarPorId(long id_empresa) {
		// TODO Auto-generated method stub
		return repo.findById(id_empresa).orElse(null);
	}

	@Override
	public void guardar(Empresa empresa) {
		// TODO Auto-generated method stub
		repo.save(empresa);
	}

}
