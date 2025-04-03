-- PostgresSQL database migration script to insert initial data into the category table.
INSERT INTO public.category (id, name, description)
VALUES (1, 'Shoes', 'Ergonomic');
INSERT INTO public.category (id, name, description)
VALUES (2, 'Chips', 'Handcrafted');
INSERT INTO public.category (id, name, description)
VALUES (3, 'Fish', 'Handcrafted');
INSERT INTO public.category (id, name, description)
VALUES (4, 'Car', 'Handcrafted');
INSERT INTO public.category (id, name, description)
VALUES (5, 'Ball', 'Handcrafted');

-- PostgresSQL database migration script to insert initial data into the product table.
INSERT INTO public.product (id, name, description, available_quantity, price, category_id)
VALUES (1, 'Shoes', 'Ergonomic', 100, 100.00, 1);
INSERT INTO public.product (id, name, description, available_quantity, price, category_id)
VALUES (2, 'Chips', 'Handcrafted', 100, 100.00, 2);
INSERT INTO public.product (id, name, description, available_quantity, price, category_id)
VALUES (3, 'Fish', 'Handcrafted', 100, 100.00, 3);
INSERT INTO public.product (id, name, description, available_quantity, price, category_id)
VALUES (4, 'Car', 'Handcrafted', 100, 100.00, 4);
INSERT INTO public.product (id, name, description, available_quantity, price, category_id)
VALUES (5, 'Ball', 'Handcrafted', 100, 100.00, 5);
INSERT INTO public.product (id, name, description, available_quantity, price, category_id)
VALUES (6, 'Shoes', 'Ergonomic', 100, 100.00, 1);
INSERT INTO public.product (id, name, description, available_quantity, price, category_id)
VALUES (7, 'Chips', 'Handcrafted', 100, 100.00, 2);
INSERT INTO public.product (id, name, description, available_quantity, price, category_id)
VALUES (8, 'Fish', 'Handcrafted', 100, 100.00, 3);