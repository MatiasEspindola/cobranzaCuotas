package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Actividad_Usuario;

public interface I_Actividad_Service {
	
	public void borrar_actividad(Actividad_Usuario actividad);
	
	public void guardar_actividad(Actividad_Usuario actividad);

}
