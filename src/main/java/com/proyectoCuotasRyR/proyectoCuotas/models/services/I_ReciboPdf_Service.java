package com.proyectoCuotasRyR.proyectoCuotas.models.services;

import java.io.ByteArrayInputStream;
import java.util.Map;

import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Cliente;
import com.proyectoCuotasRyR.proyectoCuotas.models.entities.Recibo;

public interface I_ReciboPdf_Service {
	  ByteArrayInputStream exportReceiptPdf(String templateName, Map<String, Object> data);
}
