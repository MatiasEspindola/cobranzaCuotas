create table historial_pagos(
	id_historial_pago int not null primary key,
    fecha date not null
);

alter table historial_pagos add column importe float null;

create table detalles_recibos(
	id_detalle_recibo int not null primary key auto_increment,
    fecha date not null
);

alter table detalles_recibos add column hora time not null;
alter table detalles_recibos add column importe float not null;

create table conceptos(
	id_concepto int not null primary key auto_increment,
    concepto varchar(255) not null
);

alter table detalles_recibos add column id_concepto int null;
alter table detalles_recibos add constraint id_concepto foreign key (id_concepto)
references conceptos(id_concepto);

alter table detalles_recibos add column observaciones varchar(255) null;

alter table recibos add column id_detalle_recibo int null;
alter table recibos add constraint id_detalle_recibo foreign key (id_detalle_recibo)
references detalles_recibos(id_detalle_recibo);

create table medios_pagos(
	id_medio_pago int not null primary key auto_increment,
    alta date not null,
    medio_pago varchar(255) not null,
    activo boolean
);

alter table detalles_recibos add column id_medio_pago int null;
alter table detalles_recibos add constraint id_medio_pago foreign key (id_medio_pago)
references medios_pagos(id_medio_pago);


select * from recibos;
select * from detalles_recibos;
