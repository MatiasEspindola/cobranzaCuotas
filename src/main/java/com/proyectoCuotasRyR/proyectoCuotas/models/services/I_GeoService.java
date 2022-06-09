package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Localidad;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Provincia;

public interface I_GeoService {

	public List<Provincia> provincias();
	
	public List<Localidad> localidades();
	
	public List<Localidad> buscarPorProvincia(Provincia provincia);
	
	public List<Localidad> buscarPorTerm(String term);
	
	public Provincia buscarPorId(int id);
	
}
