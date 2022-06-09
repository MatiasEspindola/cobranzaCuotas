select * from clientes; 

select * from importes;
select * from historial_pagos;

alter table detalles_recibos add column id_recibo int null;
alter table detalles_recibos add constraint id_recibo foreign key (id_recibo)
references recibos(id_recibo);

alter table recibos add column descripcion varchar(255);
alter table recibos add column fecha date;


select * from conceptos;
select * from tipos_comprobantes;
insert into conceptos (concepto) values ("COBRANZA");


select * from detalles_recibos;