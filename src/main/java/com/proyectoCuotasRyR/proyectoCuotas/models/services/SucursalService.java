package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Historial_Sucursal_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Sucursal_Repo;

@Service
public class SucursalService implements I_Sucursal_Service {

	@Autowired
	private I_Sucursal_Repo repo;
	
	@Autowired
	private I_Historial_Sucursal_Repo historial_repo;
	
	@Override
	public List<Sucursal> listar() {
		// TODO Auto-generated method stub
		return (List<Sucursal>) repo.findAll();
	}

	@Override
	public Sucursal buscarPorId(long id_sucursal) {
		// TODO Auto-generated method stub
		return repo.findById(id_sucursal).orElse(null);
	}

	@Override
	public void guardar(Sucursal sucursal, boolean valor) {
		// TODO Auto-generated method stub
		sucursal.setActivo(valor);
		repo.save(sucursal);
	}

	@Override
	public void deshabilitar(Sucursal sucursal, boolean valor) {
		// TODO Auto-generated method stub
		sucursal.setActivo(valor);
		repo.save(sucursal);
	}
	
	@Override
	public boolean existente(Sucursal sucursal, boolean editar) {
		
		if(editar) {
			for(Sucursal sucursal_pos : listar()) {
				if(sucursal_pos.getId_sucursal() != sucursal.getId_sucursal()) {
					if(sucursal_pos.getDireccion().equals(sucursal.getDireccion())) {
						if(sucursal_pos.getId_localidad2().getLocalidad() == sucursal.getId_localidad2().getLocalidad()) {
							System.out.print("REPETIDO");
							return true;
						}
					}
				}
			}
		}else {
			for(Sucursal sucursal_pos : listar()) {
				if(sucursal_pos.getDireccion().equals(sucursal.getDireccion())) {
					if(sucursal_pos.getId_localidad2().getLocalidad() == sucursal.getId_localidad2().getLocalidad()) {
						return true;
					}
				}
			}
		}
		
		return false;
	}

	@Override
	public void borrar_historial(Historial_Sucursal historial) {
		// TODO Auto-generated method stub
		historial_repo.delete(historial);
	}

	@Override
	public void guardar_historial(Historial_Sucursal historial) {
		// TODO Auto-generated method stub
		historial_repo.save(historial);
	}

}
