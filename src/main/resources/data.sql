INSERT INTO warehouses (warehouse_code) VALUES ('MSK-S');
INSERT INTO warehouses (warehouse_code) VALUES ('MSK-N');
INSERT INTO warehouses (warehouse_code) VALUES ('SPB');
INSERT INTO warehouses (warehouse_code) VALUES ('KZN');

INSERT INTO products (vendor_code, name, purchase_price, selling_price) VALUES (1, 'WATER', 100.00, 150.00);
INSERT INTO products (vendor_code, name, purchase_price, selling_price) VALUES (2, 'BEER', 200.00, 299.99);
INSERT INTO products (vendor_code, name, purchase_price, selling_price) VALUES (3, 'BREAD', 10.50, 29.99);
INSERT INTO products (vendor_code, name, purchase_price, selling_price) VALUES (4, 'CHICKEN', 120.00, 300.00);
INSERT INTO products (vendor_code, name, purchase_price, selling_price) VALUES (5, 'CHEESE', 300.00, 499.99);
INSERT INTO products (vendor_code, name, purchase_price, selling_price) VALUES (6, 'CAKE', 99.99, 210.45);
INSERT INTO products (vendor_code, name, purchase_price, selling_price) VALUES (7, 'MANGO', 70.00, 140.50);
INSERT INTO products (vendor_code, name, purchase_price, selling_price) VALUES (8, 'POTATO', 8.80, 16.70);
INSERT INTO products (vendor_code, name, purchase_price, selling_price) VALUES (9, 'MAYO', 29.30, 77.99);

INSERT INTO documents_log (number, doc_type, date) VALUES (1, 1, '2022-06-04 18:13:56');
INSERT INTO documents_log (number, doc_type, date) VALUES (2, 2, '2022-06-03 11:10:00');

INSERT INTO storage (storage_id, vendor_code, warehouse, stock) VALUES (1, 1, 'MSK-S', 401123);
INSERT INTO storage (storage_id, vendor_code, warehouse, stock) VALUES (2, 2, 'SPB', 9995435);
INSERT INTO storage (storage_id, vendor_code, warehouse, stock) VALUES (3, 3, 'MSK-S', 1);
INSERT INTO storage (storage_id, vendor_code, warehouse, stock) VALUES (4, 4, 'SPB', 10000);
INSERT INTO storage (storage_id, vendor_code, warehouse, stock) VALUES (5, 5, 'SPB', 999);
INSERT INTO storage (storage_id, vendor_code, warehouse, stock) VALUES (6, 6, 'MSK-S', 0);
INSERT INTO storage (storage_id, vendor_code, warehouse, stock) VALUES (7, 7, 'KZN', 564561);
INSERT INTO storage (storage_id, vendor_code, warehouse, stock) VALUES (8, 8, 'MSK-N', 897912);
INSERT INTO storage (storage_id, vendor_code, warehouse, stock) VALUES (9, 9, 'KZN', 231354);
INSERT INTO storage (storage_id, vendor_code, warehouse, stock) VALUES (10, 2, 'MSK-S', 54784);
INSERT INTO storage (storage_id, vendor_code, warehouse, stock) VALUES (11, 1, 'MSK-N', 789);
INSERT INTO storage (storage_id, vendor_code, warehouse, stock) VALUES (12, 9, 'MSK-S', 777);
INSERT INTO storage (storage_id, vendor_code, warehouse, stock) VALUES (13, 2, 'MSK-N', 89798);