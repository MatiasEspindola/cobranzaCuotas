package com.proyectoCuotasRyR.proyectoCuotas.models.repo;

import org.springframework.data.repository.CrudRepository;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cuota;

public interface I_Cuota_Repo extends CrudRepository<Cuota, Long> {

}
