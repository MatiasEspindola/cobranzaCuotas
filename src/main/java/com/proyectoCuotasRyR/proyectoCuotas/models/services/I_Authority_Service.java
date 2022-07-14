package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Authority;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Usuario;

public interface I_Authority_Service {

	public void guardar(Authority authority);
	
	public Authority buscarPorUsuario(Usuario usuario);
	
}
