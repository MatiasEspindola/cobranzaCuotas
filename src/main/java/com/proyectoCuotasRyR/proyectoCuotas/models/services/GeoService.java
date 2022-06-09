package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Localidad;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Provincia;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Localidad_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Provincia_Repo;

@Service
public class GeoService implements I_GeoService {
	
	@Autowired
	private I_Provincia_Repo provinciaRepo;
	
	@Autowired
	private I_Localidad_Repo localidadRepo;

	@Override
	public List<Provincia> provincias() {
		// TODO Auto-generated method stub
		return (List<Provincia>) provinciaRepo.findAll();
	}

	@Override
	public List<Localidad> localidades() {
		// TODO Auto-generated method stub
		return (List<Localidad>) localidadRepo.findAll();
	}

	@Override
	public List<Localidad> buscarPorProvincia(Provincia provincia) {
		// TODO Auto-generated method stub
		return localidadRepo.buscarPorProvincia(provincia);
	}

	@Override
	public Provincia buscarPorId(int id) {
		// TODO Auto-generated method stub
		return provinciaRepo.buscarPorId(id);
	}

	@Override
	public List<Localidad> buscarPorTerm(String term) {
		// TODO Auto-generated method stub
		return localidadRepo.buscarPorTerm(term);
	}

}
