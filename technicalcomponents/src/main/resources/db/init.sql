insert into default_storage_set values (1,'ORDERED');
insert into storage values (1,true,1,50,'c:/data/ismdb',1);

insert into component_catalog values (1,'Default','Default Global Catalog');
insert into component_category values (1,'Mainframe','Mainfraime components');
insert into component_category values (2,'Open','Open components');
insert into component_catalog_categories values (1,1);
insert into component_catalog_categories values (1,2);

insert into component_category values (3,'Open source','Open source components',2);
insert into component_category values (4,'Oracle','Oracle components',2);
insert into component_category values (5,'ESB','ESB components',2);

insert into component_category values (6,'Cobol compilers','Cobol compilers',1);