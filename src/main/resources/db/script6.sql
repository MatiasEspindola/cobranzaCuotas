alter table historial_pagos add column id_importe int null;
alter table historial_pagos add constraint id_importe foreign key (id_importe)
references importes(id_importe);

--                 Historial Pagos
-- id_historial   id_cuota    importe    id_importe
-- 		1			  1	      1649.40        1
--		2			  2		  1000			 1

--                   Importes
-- id_importe   importe      fecha     id_medio_pago
--      1       2649.40    13-05-2022        1

select * from cuotas;

update cuotas
set pagado = true
where pendiente = 0;