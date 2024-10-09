CREATE TABLE admin_entity (
                             adminId SERIAL PRIMARY KEY,
                             firstName VARCHAR(255) NOT NULL,
                             lastName VARCHAR(255) NOT NULL,
                             email VARCHAR(255) NOT NULL UNIQUE,
                             password VARCHAR(255) NOT NULL,
                             dateOfBirth DATE,
                             phoneNumber VARCHAR(20),
                             gender VARCHAR(10)
);

CREATE TABLE customer_entity (
                                customerId SERIAL PRIMARY KEY,
                                firstName VARCHAR(255) NOT NULL,
                                lastName VARCHAR(255) NOT NULL,
                                email VARCHAR(255) NOT NULL UNIQUE,
                                password VARCHAR(255) NOT NULL,
                                dateOfBirth DATE,
                                phoneNumber VARCHAR(20),
                                gender VARCHAR(10)
);

CREATE TABLE employee_entity (
                                employeeId SERIAL PRIMARY KEY,
                                firstName VARCHAR(255) NOT NULL,
                                lastName VARCHAR(255) NOT NULL,
                                email VARCHAR(255) NOT NULL UNIQUE,
                                password VARCHAR(255) NOT NULL,
                                dateOfBirth DATE,
                                phoneNumber VARCHAR(20),
                                gender VARCHAR(10),
                                qualified VARCHAR(255),
                                availability TEXT
);

CREATE TABLE order_entity (
                             orderId SERIAL PRIMARY KEY,
                             description TEXT,
                             price DECIMAL(10, 2),
                             estimatedTime INT
);

CREATE TABLE booking_entity (
                               bookingId SERIAL PRIMARY KEY,
                               bookingDate DATE,
                               startTime TIME,
                               totalDuration INT,
                               totalCost DECIMAL(10, 2),
                               status VARCHAR(20),
                               customerId INT REFERENCES customer_entity(customerId),
                               employeeId INT REFERENCES employee_entity(employeeId)
);

CREATE TABLE schedule_entity (
                                scheduleId SERIAL PRIMARY KEY,
                                employeeId INT REFERENCES employee_entity(employeeId),
                                date DATE,
                                startTime TIME,
                                endTime TIME
);

CREATE TABLE timeslot_entity (
                                timeSlotId SERIAL PRIMARY KEY,
                                scheduleId INT REFERENCES schedule_entity(scheduleId),
                                startTime TIME,
                                endTime TIME,
                                isAvailable BOOLEAN
);

-- Admin entity
INSERT INTO admin_entity (firstName, lastName, email, password, dateOfBirth, phoneNumber, gender)
VALUES ('Admin', 'Superuser', 'admin@supernova.api', 'encoded_password_here', '1970-01-01', '0612345678', 'MALE');

-- Employee entities (horror figures)
INSERT INTO employee_entity (firstName, lastName, email, password, dateOfBirth, phoneNumber, gender, qualified, availability)
VALUES
    ('Freddy', 'Krueger', 'freddy@horror.com', 'encoded_password_here', '1942-05-15', '0612345678', 'MALE', 'Haircuts, Shaving', 'Monday-Friday: 09:00-17:00'),
    ('Jason', 'Voorhees', 'jason@horror.com', 'encoded_password_here', '1946-06-13', '0623456789', 'MALE', 'Shaving, Scalp massages', 'Monday-Friday: 09:00-17:00'),
    ('Michael', 'Myers', 'michael@horror.com', 'encoded_password_here', '1957-10-19', '0634567890', 'MALE', 'Haircuts, Scalp massages', 'Monday-Friday: 09:00-17:00'),
    ('Ghostface', 'Killer', 'ghostface@horror.com', 'encoded_password_here', '1970-12-25', '0645678901', 'MALE', 'Haircuts', 'Monday-Friday: 09:00-17:00');

-- Customer entities (famous people)
INSERT INTO customer_entity (firstName, lastName, email, password, dateOfBirth, phoneNumber, gender)
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
INSERT INTO order_entity (description, price, estimatedTime)
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
INSERT INTO schedule_entity (employeeId, date, startTime, endTime)
VALUES
    (1, '2024-10-08', '09:00', '17:00'),
    (2, '2024-10-08', '09:00', '17:00'),
    (3, '2024-10-08', '09:00', '17:00'),
    (4, '2024-10-08', '09:00', '17:00');

-- Booking entities (Customer bookings)
INSERT INTO booking_entity (bookingDate, startTime, totalDuration, totalCost, status, customerId, employeeId)
VALUES
    ('2024-10-09', '10:00', 60, 30.00, 'PENDING', 1, 1),
    ('2024-10-09', '11:00', 45, 25.00, 'PENDING', 2, 2),
    ('2024-10-09', '12:00', 90, 40.00, 'PENDING', 3, 3),
    ('2024-10-09', '13:00', 30, 15.00, 'PENDING', 4, 4);

-- Timeslot entities (Schedule time slots)
INSERT INTO timeslot_entity (scheduleId, startTime, endTime, isAvailable)
VALUES
    (1, '09:00', '09:30', TRUE),
    (1, '09:30', '10:00', TRUE),
    (1, '10:00', '10:30', FALSE),
    (2, '10:30', '11:00', TRUE),
    (2, '11:00', '11:30', FALSE),
    (3, '11:30', '12:00', TRUE),
    (3, '12:00', '12:30', FALSE),
    (4, '12:30', '13:00', TRUE);
