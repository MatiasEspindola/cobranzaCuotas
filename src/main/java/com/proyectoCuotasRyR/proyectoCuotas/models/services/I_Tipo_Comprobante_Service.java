package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Tipo_Comprobante;

public interface I_Tipo_Comprobante_Service {

	public List<Tipo_Comprobante> listar();
	
	public Tipo_Comprobante buscarPorId(long id_tipo_comprobante);
	
}
