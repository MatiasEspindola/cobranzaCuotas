package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario_Sucursal;

public interface I_Usuario_Sucursal_Service {
	
	public List<Usuario_Sucursal> listar();
	
	public Usuario_Sucursal buscarPorId(long id_sucursal);
	
	public void guardar(Usuario_Sucursal usuario_sucursal);
	
	public List<Usuario_Sucursal>  buscarPorUsuario(Usuario usuario);
	
	public List<Usuario_Sucursal>  buscarPorSucursal(Sucursal sucursal); 

}
