-- Insert roles
INSERT INTO ROLES (name) VALUES ('ROLE_ADMIN');
INSERT INTO ROLES (name) VALUES ('ROLE_USER');

-- INSERT users with BCrypt hashed passwords where password = username

INSERT INTO USERS (username, password, first_name, last_name)
VALUES
    ('admin', '$2a$10$dlcBdJF.Nszt1ChilpUfwOxzLPTUqCYC.k7miHNRjiAbPDETMaUNW', 'Admin', 'Administrator'),
    ('user',  '$2a$10$dlcBdJF.Nszt1ChilpUfwOxzLPTUqCYC.k7miHNRjiAbPDETMaUNW', 'John', 'Doe');

-- Assign roles to users
-- Assuming 'admin' has ID 1 and 'user' has ID 2
-- And ROLE_ADMIN is ID 1 and ROLE_USER is ID 2
INSERT INTO ROLE_USER (application_user_id, role_id) VALUES (1, 1); -- admin -> ROLE_ADMIN
INSERT INTO ROLE_USER (application_user_id, role_id) VALUES (2, 2); -- user  -> ROLE_USER

INSERT INTO WORKING_HOURS (open_time, close_time)
VALUES
    ('08:00:00', '22:00:00'),
    ('10:00:00', '23:00:00');


INSERT INTO RESTAURANT (name, address_city, address_street, address_house_number, address_postal_code,
                        contact_phone_number, contact_email, is_open, average_delivery_time_seconds, average_customer_rating,
                        max_orders, michelin_star, kratak_opis, working_hours_id, created_by)
VALUES
    ('Restoran Dobar Tek', 'Zagreb', 'Ilica', '15', '10000', '0911234567', 'kontakt@restoran.hr', TRUE, 1800, 4.5, 100, 1, 'Izvrstan restoran sa svježim namirnicama.', 1, 1),
    ('Pizzeria Napoli', 'Split', 'Riva', '20', '21000', '0927654321', 'napoli@pizzeria.hr', TRUE, 1200, 4.7, 80, 0, 'Najbolja pizza na Jadranu.', 2, 1),
    ('Bistro Zelena Dolina', 'Rijeka', 'Korzo', '3', '51000', '0959876543', 'zelena@bistro.hr', FALSE, 2400, 4.3, 50, 0, 'Domaća hrana i ugodan ambijent.', 1, 1);

INSERT INTO REVIEW (title, text, rating, restaurant_id)
VALUES
    ( 'Super iskustvo!', 'Hrana je bila izvrsna, a dostava brza.', 5, 3),
    ( 'Najbolja pizza!', 'Pizzeria Napoli nikad ne razočara.', 5, 1),
    ( 'Dobra atmosfera', 'Fino smo jeli i uživali u miru.', 4, 2);

