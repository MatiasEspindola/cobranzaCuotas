select * from tipos_comprobantes;

alter table historial_pagos add column detalles varchar(255);
alter table detalles_recibos add column id_historial int null;
alter table detalles_recibos add constraint id_historial
foreign key (id_historial) references historial_pagos(id_historial_pago);

alter table historial_pagos add constraint id_cuota foreign key (id_cuota)
references cuotas(id_cuota); 

update cuotas set pendiente = 1649.4 where id_cuota > 0;