

CREATE TABLE customer_entity (
                                customer_id SERIAL PRIMARY KEY,
                                first_name VARCHAR(255),
                                last_name VARCHAR(255),
                                email VARCHAR(255) NOT NULL UNIQUE,
                                password VARCHAR(255) NOT NULL,
                                date_of_birth DATE,
                                phone_number VARCHAR(20),
                                gender VARCHAR(10)
);

CREATE TABLE employee_entity (
                                employee_id SERIAL PRIMARY KEY,
                                first_name VARCHAR(255),
                                last_name VARCHAR(255),
                                email VARCHAR(255) NOT NULL UNIQUE,
                                password VARCHAR(255) NOT NULL,
                                date_of_birth DATE,
                                phone_number VARCHAR(20),
                                gender VARCHAR(10),
                                qualified VARCHAR(255),
                                availability TEXT
);

CREATE TABLE order_entity (
                             order_id SERIAL PRIMARY KEY,
                             description TEXT,
                             price DECIMAL(10, 2),
                             estimated_time INT
);

CREATE TABLE booking_entity (
                               booking_id SERIAL PRIMARY KEY,
                               booking_date DATE,
                               start_time TIME,
                               total_duration INT,
                               total_cost DECIMAL(10, 2),
                               status VARCHAR(20),
                               customer_id INT REFERENCES customer_entity(customer_id),
                               employee_id INT REFERENCES employee_entity(employee_id)
);

CREATE TABLE schedule_entity (
                                schedule_id SERIAL PRIMARY KEY,
                                employee_id INT REFERENCES employee_entity(employee_id),
                                date DATE,
                                start_time TIME,
                                end_time TIME
);

CREATE TABLE timeslot_entity (
                                timeSlot_id SERIAL PRIMARY KEY,
                                schedule_id INT REFERENCES schedule_entity(schedule_id),
                                start_time TIME,
                                end_time TIME,
                                is_available BOOLEAN
);


-- Employee entities (horror figures)
INSERT INTO employee_entity (first_name, last_name, email, password, date_of_birth, phone_number, gender, qualified, availability)
VALUES
    ('Freddy', 'Krueger', 'freddy@horror.com', 'encoded_password_here', '1942-05-15', '0612345678', 'MALE', 'Haircuts, Shaving', 'Monday-Friday: 09:00-17:00'),
    ('Jason', 'Voorhees', 'jason@horror.com', 'encoded_password_here', '1946-06-13', '0623456789', 'MALE', 'Shaving, Scalp massages', 'Monday-Friday: 09:00-17:00'),
    ('Michael', 'Myers', 'michael@horror.com', 'encoded_password_here', '1957-10-19', '0634567890', 'MALE', 'Haircuts, Scalp massages', 'Monday-Friday: 09:00-17:00'),
    ('Ghostface', 'Killer', 'ghostface@horror.com', 'encoded_password_here', '1970-12-25', '0645678901', 'MALE', 'Haircuts', 'Monday-Friday: 09:00-17:00');

-- Customer entities (famous people)
INSERT INTO customer_entity (first_name, last_name, email, password, date_of_birth, phone_number, gender)
VALUES
    ('Albert', 'Einstein', 'einstein@physics.com', 'encoded_password_here', '1879-03-14', '0612341234', 'MALE'),
    ('Marie', 'Curie', 'curie@science.com', 'encoded_password_here', '1867-11-07', '0623452345', 'FEMALE'),
    ('Leonardo', 'da Vinci', 'davinci@art.com', 'encoded_password_here', '1452-04-15', '0634563456', 'MALE'),
    ('Cleopatra', 'VII', 'cleo@egypt.com', 'encoded_password_here', '1111-01-01', '0645674567', 'FEMALE'),
    ('Nikola', 'Tesla', 'tesla@electricity.com', 'encoded_password_here', '1856-07-10', '0656785678', 'MALE'),
    ('Isaac', 'Newton', 'newton@gravity.com', 'encoded_password_here', '1643-01-04', '0667896789', 'MALE'),
    ('Galileo', 'Galilei', 'galileo@astronomy.com', 'encoded_password_here', '1564-02-15', '0678907890', 'MALE'),
    ('Ada', 'Lovelace', 'ada@computing.com', 'encoded_password_here', '1815-12-10', '0689018901', 'FEMALE'),
    ('Joan', 'of Arc', 'joan@france.com', 'encoded_password_here', '1412-01-06', '0690129012', 'FEMALE'),
    ('Napoleon', 'Bonaparte', 'napoleon@france.com', 'encoded_password_here', '1769-08-15', '0612345678', 'MALE');

-- Order entities (hair salon services)
INSERT INTO order_entity (description, price, estimated_time)
VALUES
    ('Basic Haircut', 15.00, 30),
    ('Beard Trim', 10.00, 15),
    ('Hair Coloring', 40.00, 90),
    ('Scalp Massage', 25.00, 45),
    ('Hair Styling', 30.00, 60),
    ('Shaving', 12.00, 20),
    ('Perm', 50.00, 120),
    ('Straightening', 45.00, 90),
    ('Kids Haircut', 12.00, 30),
    ('Luxury Haircut', 60.00, 120);

-- Schedule entities (Employee schedules)
INSERT INTO schedule_entity (employee_id, date, start_time, end_time)
VALUES
    (1, '2024-10-08', '09:00', '17:00'),
    (2, '2024-10-08', '09:00', '17:00'),
    (3, '2024-10-08', '09:00', '17:00'),
    (4, '2024-10-08', '09:00', '17:00');

-- Booking entities (Customer bookings)
INSERT INTO booking_entity (booking_date, start_time, total_duration, total_cost, status, customer_id, employee_id)
VALUES
    ('2024-10-09', '10:00', 60, 30.00, 'PENDING', 1, 1),
    ('2024-10-09', '11:00', 45, 25.00, 'PENDING', 2, 2),
    ('2024-10-09', '12:00', 90, 40.00, 'PENDING', 3, 3),
    ('2024-10-09', '13:00', 30, 15.00, 'PENDING', 4, 4);

-- Timeslot entities (Schedule time slots)
INSERT INTO timeslot_entity (schedule_id, start_time, end_time, is_available)
VALUES
    (1, '09:00', '09:30', TRUE),
    (1, '09:30', '10:00', TRUE),
    (1, '10:00', '10:30', TRUE),
    (2, '10:30', '11:00', TRUE),
    (2, '11:00', '11:30', TRUE),
    (3, '11:30', '12:00', TRUE),
    (3, '12:00', '12:30', TRUE),
    (4, '12:30', '13:00', TRUE);
