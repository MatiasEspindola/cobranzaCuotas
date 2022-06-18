package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Proveedor;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Proveedor;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Historial_Proveedor_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Proveedor_Repo;

@Service
public class ProveedorService implements I_Proveedor_Service {

	@Autowired
	private I_Proveedor_Repo repo;
	
	@Autowired
	private I_Historial_Proveedor_Repo repo_historial;
	
	@Override
	public List<Proveedor> listarTodo() {
		// TODO Auto-generated method stub
		return (List<Proveedor>) repo.findAll();
	}

	@Override
	public Proveedor buscarPorId(Long id_proveedor) {
		// TODO Auto-generated method stub
		return repo.findById(id_proveedor).orElse(null);
	}

	@Override
	public void guardar(Proveedor proveedor, boolean valor) {
		// TODO Auto-generated method stub
		proveedor.setActivo(true);
		repo.save(proveedor);
	}
	
	@Override
	public void deshabilitar(Proveedor proveedor, boolean valor) {
		// TODO Auto-generated method stub
		proveedor.setActivo(valor);
		repo.save(proveedor);
	}

	@Override
	public List<Proveedor> buscarPorTerm(String term) {
		// TODO Auto-generated method stub
		return repo.buscarPorTerm(term);
	}
	
	@Override
	public boolean existente(Proveedor proveedor, boolean editar) {
		
		if(editar) {
			for(Proveedor proveedor_pos : listarTodo()) {
				if(proveedor_pos.getId_proveedor() != proveedor.getId_proveedor()) {
					if(proveedor_pos.getRazon_social().equals(proveedor.getRazon_social())) {
						if(proveedor_pos.getDireccion().equals(proveedor.getDireccion())) {
							if(proveedor_pos.getId_localidad4().getLocalidad() == proveedor.getId_localidad4().getLocalidad()) {
								System.out.print("REPETIDO");
								return true;
							}
						}
					}
				}
			}
		}else {
			for(Proveedor proveedor_pos : listarTodo()) {
				if(proveedor_pos.getRazon_social().equals(proveedor.getRazon_social())) {
					if(proveedor_pos.getDireccion().equals(proveedor.getDireccion())) {
						if(proveedor_pos.getId_localidad4().getLocalidad() == proveedor.getId_localidad4().getLocalidad()) {
							System.out.print("REPETIDO");
							return true;
						}
					}
				}
			}
		}
		
		return false;
	}

	@Override
	public void borrar_historial(Historial_Proveedor historial) {
		// TODO Auto-generated method stub
		repo_historial.delete(historial);
	}

	@Override
	public void guardar_historial(Historial_Proveedor historial) {
		// TODO Auto-generated method stub
		repo_historial.save(historial);
	}


}
