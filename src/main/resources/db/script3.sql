DELETE FROM COMPROBANTES WHERE ID_COMPROBANTE > 0;
DELETE FROM TIPOS_COMPROBANTES WHERE ID_TIPO_COMPROBANTE > 0;
insert into tipos_comprobantes(tipo_comprobante)values('TRANSPORTE'),('PLAN_PAGO'),('RECIBO');

select * from ctasctescliente;