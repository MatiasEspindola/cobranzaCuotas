package com.proyectoCuotasRyR.proyectoCuotas.models.repo;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cliente;
import org.springframework.data.repository.CrudRepository;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Plan_Pago;
import java.util.List;
import org.springframework.data.jpa.repository.Query;

public interface I_Plan_Pago_Repo extends CrudRepository<Plan_Pago, Long> {


    
}
