insert into field (id, name, personal_data, special_category_personal_data, contractual_regulation, legal_requirement, obligation_to_provide)
values (1,'User First Name', true, false, false, false, false);
insert into field (id, name, personal_data, special_category_personal_data, contractual_regulation, legal_requirement, obligation_to_provide)
values (2,'User Last Name', true, false, false, false, false);
insert into field (id, name, personal_data, special_category_personal_data, contractual_regulation, legal_requirement, obligation_to_provide)
values (3,'User Username', true, false, false, false, false);
insert into field (id, name, personal_data, special_category_personal_data, contractual_regulation, legal_requirement, obligation_to_provide)
values (4,'User Address Post Code', true, false, false, false, false);
insert into field (id, name, personal_data, special_category_personal_data, contractual_regulation, legal_requirement, obligation_to_provide)
values (5,'User Address Street', true, false, false, false, false);
insert into field (id, name, personal_data, special_category_personal_data, contractual_regulation, legal_requirement, obligation_to_provide)
values (6,'User Address House Number', true, false, false, false, false);
insert into field (id, name, personal_data, special_category_personal_data, contractual_regulation, legal_requirement, obligation_to_provide)
values (7,'User Address City', true, false, false, false, false);
insert into field (id, name, personal_data, special_category_personal_data, contractual_regulation, legal_requirement, obligation_to_provide)
values (8,'User Address Country', true, false, false, false, false);
insert into field (id, name, personal_data, special_category_personal_data, contractual_regulation, legal_requirement, obligation_to_provide)
values (9,'Creditcard Number', true, false, false, false, false);
insert into field (id, name, personal_data, special_category_personal_data, contractual_regulation, legal_requirement, obligation_to_provide)
values (10,'Creditcard Expiry', true, false, false, false, false);
insert into field (id, name, personal_data, special_category_personal_data, contractual_regulation, legal_requirement, obligation_to_provide)
values (11,'Creditcard CCV', true, false, false, false, false);

-- mapping_field
-- create sequence seq_mapping_field;
-- select nextval('seq_mapping_field')
INSERT INTO mapping_field (id,phase,path,field_id, format,namespace,endpoint_id)
values
    (nextval('seq_mapping_field'),'REQUEST', '$.card.longNum', 9, 'json', 'body', 'http:POST:payment:/paymentAuth'),
    (nextval('seq_mapping_field'),'REQUEST', '$.card.expires', 10, 'json', 'body', 'http:POST:payment:/paymentAuth'),
    (nextval('seq_mapping_field'),'REQUEST', '$.customer.lastName', 2, 'json', 'body', 'http:POST:payment:/paymentAuth'),
    (nextval('seq_mapping_field'),'REQUEST', '$.address.number', 6, 'json', 'body', 'http:POST:payment:/paymentAuth'),
    (nextval('seq_mapping_field'),'REQUEST', '$.address.country', 8, 'json', 'body', 'http:POST:payment:/paymentAuth'),
    (nextval('seq_mapping_field'),'REQUEST', '$.customer.username', 3, 'json', 'body', 'http:POST:payment:/paymentAuth'),
    (nextval('seq_mapping_field'),'REQUEST', '$.card.ccv', 11, 'json', 'body', 'http:POST:payment:/paymentAuth'),
    (nextval('seq_mapping_field'),'REQUEST', '$.address.city', 7, 'json', 'body', 'http:POST:payment:/paymentAuth'),
    (nextval('seq_mapping_field'),'REQUEST', '$.address.street', 5, 'json', 'body', 'http:POST:payment:/paymentAuth'),
    (nextval('seq_mapping_field'),'REQUEST', '$.customer.firstName', 1, 'json', 'body', 'http:POST:payment:/paymentAuth'),
    (nextval('seq_mapping_field'),'REQUEST', '$.address.postcode', 4, 'json', 'body', 'http:POST:payment:/paymentAuth');

INSERT INTO mapping_field (id,phase,path,field_id, format,namespace,endpoint_id)
values
    (nextval('seq_mapping_field'),'RESPONSE','$.customer.firstName', 1 ,'json','body','http:POST:orders:/orders'),
    (nextval('seq_mapping_field'),'RESPONSE','$.customer.lastName', 2 ,'json','body','http:POST:orders:/orders'),
    (nextval('seq_mapping_field'),'RESPONSE','$.address.number', 6 ,'json','body','http:POST:orders:/orders'),
    (nextval('seq_mapping_field'),'RESPONSE','$.card.ccv', 11 ,'json','body','http:POST:orders:/orders'),
    (nextval('seq_mapping_field'),'REQUEST','$.customer', 1 ,'json','body','http:POST:orders:/orders'),
    (nextval('seq_mapping_field'),'RESPONSE','$.customer.username', 3 ,'json','body','http:POST:orders:/orders'),
    (nextval('seq_mapping_field'),'RESPONSE','$.card.expires', 10 ,'json','body','http:POST:orders:/orders'),
    (nextval('seq_mapping_field'),'RESPONSE','$.address.street', 5 ,'json','body','http:POST:orders:/orders'),
    (nextval('seq_mapping_field'),'REQUEST','$.card', 9 ,'json','body','http:POST:orders:/orders'),
    (nextval('seq_mapping_field'),'RESPONSE','$.address.postcode', 4 ,'json','body','http:POST:orders:/orders'),
    (nextval('seq_mapping_field'),'RESPONSE','$.address.city', 7 ,'json','body','http:POST:orders:/orders'),
    (nextval('seq_mapping_field'),'RESPONSE','$.card.longNum', 9 ,'json','body','http:POST:orders:/orders'),
    (nextval('seq_mapping_field'),'REQUEST','$.address', 5 ,'json','body','http:POST:orders:/orders'),
    (nextval('seq_mapping_field'),'RESPONSE','$.address.country',  8,'json','body','http:POST:orders:/orders');

INSERT INTO mapping_field (id,phase,path,field_id, format,namespace,endpoint_id)
values
    (nextval('seq_mapping_field'), 'RESPONSE', '$.customer.firstName', 1, 'json', 'body', 'http:POST:front-end.sock-shop.svc.cluster.local:/orders'),
    (nextval('seq_mapping_field'), 'RESPONSE', '$.customer.lastName', 2, 'json', 'body', 'http:POST:front-end.sock-shop.svc.cluster.local:/orders'),
    (nextval('seq_mapping_field'), 'RESPONSE', '$.address.number', 6, 'json', 'body', 'http:POST:front-end.sock-shop.svc.cluster.local:/orders'),
    (nextval('seq_mapping_field'), 'RESPONSE', '$.card.ccv', 11, 'json', 'body', 'http:POST:front-end.sock-shop.svc.cluster.local:/orders'),
    (nextval('seq_mapping_field'), 'RESPONSE', '$.customer.username', 3, 'json', 'body', 'http:POST:front-end.sock-shop.svc.cluster.local:/orders'),
    (nextval('seq_mapping_field'), 'RESPONSE', '$.card.expires', 10, 'json', 'body', 'http:POST:front-end.sock-shop.svc.cluster.local:/orders'),
    (nextval('seq_mapping_field'), 'RESPONSE', '$.address.street', 5, 'json', 'body', 'http:POST:front-end.sock-shop.svc.cluster.local:/orders'),
    (nextval('seq_mapping_field'), 'RESPONSE', '$.address.postcode', 4, 'json', 'body', 'http:POST:front-end.sock-shop.svc.cluster.local:/orders'),
    (nextval('seq_mapping_field'), 'RESPONSE', '$.address.city', 7, 'json', 'body', 'http:POST:front-end.sock-shop.svc.cluster.local:/orders'),
    (nextval('seq_mapping_field'), 'RESPONSE', '$.card.longNum', 9, 'json', 'body', 'http:POST:front-end.sock-shop.svc.cluster.local:/orders'),
    (nextval('seq_mapping_field'), 'RESPONSE', '$.address.country', 8, 'json', 'body', 'http:POST:front-end.sock-shop.svc.cluster.local:/orders');