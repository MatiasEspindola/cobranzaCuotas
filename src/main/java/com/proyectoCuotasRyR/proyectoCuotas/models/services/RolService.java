package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Rol;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Rol_Repo;

@Service
public class RolService implements I_Rol_Service {

	@Autowired
	private I_Rol_Repo repo;
	
	@Override
	public Rol buscarPorId(long id_rol) {
		// TODO Auto-generated method stub
		return repo.findById(id_rol).orElse(null);
	}

	@Override
	public List<Rol> listarTodo() {
		// TODO Auto-generated method stub
		return (List<Rol>) repo.findAll();
	}

}
