-- DROP TABLE IF EXISTS REVIEW;
-- DROP TABLE IF EXISTS RESTAURANT;
-- DROP TABLE IF EXISTS WORKING_HOURS;

CREATE TABLE WORKING_HOURS (
                               id BIGINT PRIMARY KEY AUTO_INCREMENT,
                               open_time TIME,
                               close_time TIME
);

CREATE TABLE USERS (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       username VARCHAR(255) NOT NULL UNIQUE,
                       password VARCHAR(255) NOT NULL,
                       first_name VARCHAR(255),
                       last_name VARCHAR(255)
);

CREATE TABLE ROLES (
                       id BIGINT AUTO_INCREMENT PRIMARY KEY,
                       name VARCHAR(255) NOT NULL UNIQUE
);

CREATE TABLE ROLE_USER (
                           application_user_id BIGINT NOT NULL,
                           role_id BIGINT NOT NULL,
                           PRIMARY KEY (application_user_id, role_id),
                           FOREIGN KEY (application_user_id) REFERENCES USERS(id),
                           FOREIGN KEY (role_id) REFERENCES ROLES(id)
);

CREATE TABLE RESTAURANT (
                            id BIGINT PRIMARY KEY AUTO_INCREMENT,
                            name VARCHAR(255) NOT NULL,

                            address_city VARCHAR(255),
                            address_street VARCHAR(255),
                            address_house_number VARCHAR(10),
                            address_postal_code VARCHAR(10),

                            contact_phone_number VARCHAR(20),
                            contact_email VARCHAR(255),

                            is_open BOOLEAN,
                            average_delivery_time_seconds BIGINT,
                            average_customer_rating DOUBLE,
                            max_orders INT,
                            michelin_star INT,
                            kratak_opis TEXT,

                            working_hours_id BIGINT,
                            created_by BIGINT,

                            FOREIGN KEY (working_hours_id) REFERENCES WORKING_HOURS(id),
                            FOREIGN KEY (created_by) REFERENCES USERS(id)
);

CREATE TABLE REVIEW (
                        id BIGINT PRIMARY KEY AUTO_INCREMENT,
                        title VARCHAR(255) NOT NULL,
                        text TEXT,
                        rating INT CHECK (rating BETWEEN 1 AND 5) NOT NULL,

                        restaurant_id BIGINT NOT NULL,
                        FOREIGN KEY (restaurant_id) REFERENCES restaurant(id) ON DELETE CASCADE
);


