package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Empresa;

public interface I_Empresa_Service {
	
public List<Empresa> listar_todo();
	
	public Empresa buscarPorId(long id_empresa);
	
	public void guardar(Empresa empresa);

}
