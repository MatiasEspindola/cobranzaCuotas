create table historiales_recibos(
	id_historial_recibo int not null primary key auto_increment
);

create table historiales_altas_planes_pagos(
	id_historial_alta_plan_pago int not null primary key auto_increment
);

create table historiales_altas_clientes(
	id_historial_cliente int not null primary key auto_increment
);

alter table historiales_recibos add column id_ctactecliente_recibo int null;
alter table historiales_recibos add constraint id_ctactecliente_recibo
foreign key (id_ctactecliente_recibo) references ctasctescliente(id_ctactecliente);

alter table historiales_recibos add column fk_recibo int null;
alter table historiales_recibos add constraint fk_recibo
foreign key (fk_recibo) references recibos(id_recibo);

alter table historiales_recibos add column descripcion varchar(255) null;
alter table historiales_recibos add column fecha date null;
alter table historiales_recibos add column hora datetime null;


alter table historiales_altas_planes_pagos add column id_ctactecliente_plan_pago int null;
alter table historiales_altas_planes_pagos add constraint id_ctactecliente_plan_pago
foreign key (id_ctactecliente_plan_pago) references ctasctescliente(id_ctactecliente);

alter table historiales_altas_planes_pagos add column fk_plan_pago int null;
alter table historiales_altas_planes_pagos add constraint fk_plan_pago
foreign key (fk_plan_pago) references planes_pagos(id_plan_pago);

alter table historiales_altas_planes_pagos add column descripcion varchar(255) null;


alter table historiales_altas_clientes add column id_ctactecliente_cliente int null;
alter table historiales_altas_clientes add constraint id_ctactecliente_cliente
foreign key (id_ctactecliente_cliente) references ctasctescliente(id_ctactecliente);

alter table historiales_altas_clientes add column fk_cliente int null;
alter table historiales_altas_clientes add constraint fk_cliente
foreign key (fk_cliente) references clientes(id_cliente);

alter table historiales_altas_clientes add column descripcion varchar(255) null;
alter table historiales_altas_clientes add column alta date null;

alter table sucursales add column razon_social varchar(255) null;
alter table empresas add column inicio_actividades date null;