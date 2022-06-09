package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Tipo_Documento;

public interface I_Tipo_Documento_Service {

	public List<Tipo_Documento> listarTodo();
	
	public Tipo_Documento buscarPorId(Long id_tipo_documento);
	
	public void guardar(Tipo_Documento tipo_documento);
	
}
