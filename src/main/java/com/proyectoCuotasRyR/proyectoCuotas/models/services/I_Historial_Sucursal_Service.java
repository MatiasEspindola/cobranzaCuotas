package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Sucursal;

public interface I_Historial_Sucursal_Service {

	public void guardar(Historial_Sucursal historial_sucursal);

	public List<Historial_Sucursal> listarTodo();

	public Historial_Sucursal buscarPorSucursal(Sucursal sucursal);

}
