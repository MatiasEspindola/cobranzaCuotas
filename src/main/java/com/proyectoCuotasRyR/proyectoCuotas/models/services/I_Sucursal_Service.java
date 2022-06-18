package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.util.List;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Proveedor;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Sucursal;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Sucursal;

public interface I_Sucursal_Service {

	public List<Sucursal> listar();

	public Sucursal buscarPorId(long id_sucursal);

	public void guardar(Sucursal sucursal, boolean valor);

	public void deshabilitar(Sucursal sucursal, boolean valor);

	public boolean existente(Sucursal sucursal, boolean valor);

	public void borrar_historial(Historial_Sucursal historial);

	public void guardar_historial(Historial_Sucursal historial);
}
