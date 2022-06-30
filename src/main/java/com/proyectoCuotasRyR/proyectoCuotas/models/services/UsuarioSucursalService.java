package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario_Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.repo.I_Usuario_Sucursal_Repo;

@Service
public class UsuarioSucursalService implements I_Usuario_Sucursal_Service {

	@Autowired
	private I_Usuario_Sucursal_Repo repo;
	
	@Override
	public List<Usuario_Sucursal> listar() {
		// TODO Auto-generated method stub
		return (List<Usuario_Sucursal>) repo.findAll();
	}

	@Override
	public Usuario_Sucursal buscarPorId(long id_sucursal) {
		// TODO Auto-generated method stub
		return repo.findById(id_sucursal).orElse(null);
	}

	@Override
	public void guardar(Usuario_Sucursal usuario_sucursal) {
		// TODO Auto-generated method stub
		repo.save(usuario_sucursal);
	}

	@Override
	public List<Usuario_Sucursal>  buscarPorUsuario(Usuario usuario) {
		// TODO Auto-generated method stub
		return repo.buscarPorUsuario(usuario);
	}

	@Override
	public List<Usuario_Sucursal>  buscarPorSucursal(Sucursal sucursal) {
		// TODO Auto-generated method stub
		return repo.buscarPorSucursal(sucursal);
	}

}
