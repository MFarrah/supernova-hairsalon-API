-- Insert Employees (Horror Figures) with bcrypt encoded passwords and unique qualified order ids
INSERT INTO employee_entity (email, password, first_name, last_name, date_of_birth, phone_number, gender)
VALUES
    ('freddy@krueger.com', '$2a$10$7QJ8K1Q1Q1Q1Q1Q1Q1Q1QO', 'Freddy', 'Krueger', '1942-05-01', '1234567890', 'MALE'),
    ('jason@voorhees.com', '$2a$10$7QJ8K1Q1Q1Q1Q1Q1Q1Q1QO', 'Jason', 'Voorhees', '1946-06-13', '1234567891', 'MALE'),
    ('michael@myers.com', '$2a$10$7QJ8K1Q1Q1Q1Q1Q1Q1Q1QO', 'Michael', 'Myers', '1957-10-19', '1234567892', 'MALE'),
    ('chucky@doll.com', '$2a$10$7QJ8K1Q1Q1Q1Q1Q1Q1Q1QO', 'Chucky', 'Doll', '1988-11-09', '1234567893', 'MALE');

-- Insert Customers (Famous People) with bcrypt encoded passwords
INSERT INTO customer_entity (first_name, last_name, date_of_birth, email, phone_number, password, gender)
VALUES
    ('Albert', 'Einstein', '1879-03-14', 'albert@einstein.com', '1234567894', '$2a$10$7QJ8K1Q1Q1Q1Q1Q1Q1Q1QO', 'MALE'),
    ('Marie', 'Curie', '1867-11-07', 'marie@curie.com', '1234567895', '$2a$10$7QJ8K1Q1Q1Q1Q1Q1Q1Q1QO', 'FEMALE'),
    ('Isaac', 'Newton', '1643-01-04', 'isaac@newton.com', '1234567896', '$2a$10$7QJ8K1Q1Q1Q1Q1Q1Q1Q1QO', 'MALE'),
    ('Nikola', 'Tesla', '1856-07-10', 'nikola@tesla.com', '1234567897', '$2a$10$7QJ8K1Q1Q1Q1Q1Q1Q1Q1QO', 'MALE');

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

-- Insert Schedules for Employees
-- Freddy Krueger's Schedule
INSERT INTO schedule_entity (employee_id, day_of_week, start_time, end_time)
VALUES
    (1, 'MONDAY', '09:00:00', '17:00:00'),
    (1, 'TUESDAY', '09:00:00', '17:00:00');

-- Jason Voorhees' Schedule
INSERT INTO schedule_entity (employee_id, day_of_week, start_time, end_time)
VALUES
    (2, 'WEDNESDAY', '09:00:00', '17:00:00'),
    (2, 'THURSDAY', '09:00:00', '17:00:00');

-- Michael Myers' Schedule
INSERT INTO schedule_entity (employee_id, day_of_week, start_time, end_time)
VALUES
    (3, 'FRIDAY', '09:00:00', '17:00:00'),
    (3, 'SATURDAY', '09:00:00', '17:00:00');

-- Chucky's Schedule
INSERT INTO schedule_entity (employee_id, day_of_week, start_time, end_time)
VALUES
    (4, 'SUNDAY', '09:00:00', '17:00:00'),
    (4, 'MONDAY', '09:00:00', '17:00:00');

-- Insert Qualified Order IDs for Employees
-- Freddy Krueger's Qualified Orders
INSERT INTO employee_qualified_orders (employee_id, qualified_order_id)
VALUES
    (1, 1),
    (1, 2);

-- Jason Voorhees' Qualified Orders
INSERT INTO employee_qualified_orders (employee_id, qualified_order_id)
VALUES
    (2, 3),
    (2, 4);

-- Michael Myers' Qualified Orders
INSERT INTO employee_qualified_orders (employee_id, qualified_order_id)
VALUES
    (3, 5),
    (3, 6);

-- Chucky's Qualified Orders
INSERT INTO employee_qualified_orders (employee_id, qualified_order_id)
VALUES
    (4, 7),
    (4, 8);
