package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Actividad_Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.CtaCteCliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Alta_Cliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Actividad_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Cliente_Repo;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Historial_Alta_Cliente_Repo;

@Service
public class ClienteService implements I_Cliente_Service {

	@Autowired
	private I_Cliente_Repo repo;

	@Autowired
	private I_Historial_Alta_Cliente_Repo historial_repo;



	@Override
	public List<Cliente> listarTodo() {
		// TODO Auto-generated method stub
		return (List<Cliente>) repo.findAll();
	}

	@Override
	public Cliente buscarPorId(Long id_cliente) {
		// TODO Auto-generated method stub
		return repo.findById(id_cliente).orElse(null);
	}

	@Override
	public void guardar(Cliente cliente, boolean activo) {
		// TODO Auto-generated method stub
		cliente.setActivo(activo);
		repo.save(cliente);
	}

	@Override
	public List<Cliente> buscarPorTerm(String term) {
		// TODO Auto-generated method stub
		return repo.buscarPorTerm(term);
	}

	@Override
	public void deshabilitar(Cliente cliente, boolean valor) {
		// TODO Auto-generated method stub
		cliente.setActivo(valor);
		;
		repo.save(cliente);
	}

	@Override
	public boolean existente(Cliente cliente, boolean editar) {
		if (editar) {
			for (Cliente cliente_pos : listarTodo()) {
				if (cliente_pos.getId_cliente() != cliente.getId_cliente()) {
					if (cliente_pos.getNro_documento().equals(cliente.getNro_documento())) {

						return true;

					}
				}
			}
		} else {
			for (Cliente cliente_pos : listarTodo()) {
				if (cliente_pos.getNro_documento().equals(cliente.getNro_documento())) {

					return true;

				}
			}
		}

		return false;
	}

	@Override
	public void borrar_historial(Historial_Alta_Cliente historial) {
		// TODO Auto-generated method stub
		historial_repo.delete(historial);
	}

	@Override
	public void guardar_historial(Historial_Alta_Cliente historial) {
		// TODO Auto-generated method stub
		historial_repo.save(historial);
	}

	@Override
	public Historial_Alta_Cliente buscarPorCtaCte(CtaCteCliente ctactecliente) {
		// TODO Auto-generated method stub
		return historial_repo.buscarPorCtaCte(ctactecliente);
	}

	


}
