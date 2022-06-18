package com.proyectoCuotasRyR.proyectoCuotas.models.repo;

import org.springframework.data.repository.CrudRepository;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Historial_Proveedor;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Proveedor;

public interface I_Historial_Proveedor_Repo extends CrudRepository<Historial_Proveedor, Long> {

}
