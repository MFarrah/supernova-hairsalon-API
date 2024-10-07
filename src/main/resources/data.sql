-- Insert Employees (Example Data) with bcrypt encoded passwords and unique qualified order ids
INSERT INTO employee_entity (email, password, first_name, last_name, date_of_birth, phone_number, gender)
VALUES
    ('freddy@krueger.com', '$2a$10$7QJ8K1Q1Q1Q1Q1Q1Q1Q1QO', 'Freddy', 'Krueger', '1942-05-01', '1234567890', 'MALE'),
    ('jason@voorhees.com', '$2a$10$7QJ8K1Q1Q1Q1Q1Q1Q1Q1QO', 'Jason', 'Voorhees', '1946-06-13', '1234567891', 'MALE'),
    ('michael@myers.com', '$2a$10$7QJ8K1Q1Q1Q1Q1Q1Q1QO', 'Michael', 'Myers', '1957-10-19', '1234567892', 'MALE'),
    ('chucky@doll.com', '$2a$10$7QJ8K1Q1Q1Q1Q1Q1Q1QO', 'Chucky', 'Doll', '1988-11-09', '1234567893', 'MALE');

-- Insert Employee Roles (Mapping)
INSERT INTO employee_roles (employee_id, roles)
VALUES
    (1, 'ADMIN'),
    (2, 'EMPLOYEE'),
    (3, 'EMPLOYEE'),
    (4, 'EMPLOYEE');

-- Insert Customers (Famous People) with bcrypt encoded passwords
INSERT INTO customer_entity (first_name, last_name, date_of_birth, email, phone_number, password, gender)
VALUES
    ('Albert', 'Einstein', '1879-03-14', 'albert@einstein.com', '1234567894', '$2a$10$7QJ8K1Q1Q1Q1Q1Q1Q1QO', 'MALE'),
    ('Marie', 'Curie', '1867-11-07', 'marie@curie.com', '1234567895', '$2a$10$7QJ8K1Q1Q1Q1Q1Q1Q1QO', 'FEMALE'),
    ('Isaac', 'Newton', '1643-01-04', 'isaac@newton.com', '1234567896', '$2a$10$7QJ8K1Q1Q1Q1Q1Q1Q1QO', 'MALE'),
    ('Nikola', 'Tesla', '1856-07-10', 'nikola@tesla.com', '1234567897', '$2a$10$7QJ8K1Q1Q1Q1Q1Q1Q1QO', 'MALE');

-- Insert Orders (Barbershop Related)
INSERT INTO order_entity (description, price, duration)
VALUES
    ('Haircut', 15.00, 30),
    ('Shave', 10.00, 20),
    ('Beard Trim', 12.00, 25),
    ('Hair Coloring', 50.00, 90),
    ('Hair Styling', 30.00, 45),
    ('Facial', 25.00, 40),
    ('Manicure', 20.00, 35),
    ('Pedicure', 25.00, 40),
    ('Massage', 60.00, 60),
    ('Scalp Treatment', 40.00, 50);

-- Insert Schedules for Employees (32-hour contracts with 4 x 8-hour shifts)
-- Freddy Krueger's Schedule
INSERT INTO schedule_entity (employee_id, day_of_week, start_time, end_time)
VALUES
    (1, 'MONDAY', '09:00:00', '17:00:00'),
    (1, 'WEDNESDAY', '09:00:00', '17:00:00'),
    (1, 'THURSDAY', '09:00:00', '17:00:00'),
    (1, 'FRIDAY', '09:00:00', '17:00:00');

-- Jason Voorhees' Schedule
INSERT INTO schedule_entity (employee_id, day_of_week, start_time, end_time)
VALUES
    (2, 'TUESDAY', '10:00:00', '18:00:00'),
    (2, 'WEDNESDAY', '10:00:00', '18:00:00'),
    (2, 'THURSDAY', '10:00:00', '18:00:00'),
    (2, 'FRIDAY', '10:00:00', '18:00:00');

-- Michael Myers' Schedule
INSERT INTO schedule_entity (employee_id, day_of_week, start_time, end_time)
VALUES
    (3, 'MONDAY', '11:00:00', '19:00:00'),
    (3, 'TUESDAY', '11:00:00', '19:00:00'),
    (3, 'THURSDAY', '11:00:00', '19:00:00'),
    (3, 'FRIDAY', '11:00:00', '19:00:00');

-- Chucky Doll's Schedule
INSERT INTO schedule_entity (employee_id, day_of_week, start_time, end_time)
VALUES
    (4, 'MONDAY', '12:00:00', '20:00:00'),
    (4, 'WEDNESDAY', '12:00:00', '20:00:00'),
    (4, 'THURSDAY', '12:00:00', '20:00:00'),
    (4, 'FRIDAY', '12:00:00', '20:00:00');


-- Insert Qualified Orders for Employees
INSERT INTO employee_qualified_orders (employee_id, qualified_order_id)
VALUES
    (1, 1),
    (1, 2),
    (2, 3),
    (3, 4),
    (4, 5);
