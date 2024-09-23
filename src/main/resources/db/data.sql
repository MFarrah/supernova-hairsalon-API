

-- Insert Employees
INSERT INTO employee_entity (employee_id, first_name, last_name, email, phone_number, password, gender, roles)
VALUES
    (1, 'John', 'Doe', 'john.doe@supernova.api', '0612345678', '$2b$12$S5AMlO3lgPPaT85H3JObouOLCqi2mPFFXiztVDh36Xw3fXWSEQxpu', 'MALE', 'EMPLOYEE'),
    (2, 'Jane', 'Smith', 'jane.smith@supernova.api', '0612345679', '$2b$12$BWayQCG9aFw7F/OHn05IZ.w35DIKJAo/ZRYUlCfo3eLvauU66J0gS', 'FEMALE', 'EMPLOYEE');


-- Insert Customers
INSERT INTO customer_entity (customer_id, first_name, last_name, email, phone_number, password, gender, roles)
VALUES
    (1, 'Chris', 'Evans', 'chris.evans@example.com', '0612345683', '$2b$12$UV0cQYO2rGmyqb8Hq7Y13eJD1UFK4gwIvggY1ff2C6GiVN16sHJl2', 'MALE', 'CUSTOMER'),
    (2, 'Anna', 'Taylor', 'anna.taylor@example.com', '0612345684', '$2b$12$6G9lBv.nXhOWg/UbcNSOJOVebjx1lEtGgtdPwqVHpZDA5Qx1Ds1ha', 'FEMALE', 'CUSTOMER');


-- Insert Orders
INSERT INTO order_entity (order_id, description, price, duration)
VALUES
    (1, 'Haircut', 25.00, 30),
    (2, 'Hair Coloring', 50.00, 60),
    (3, 'Shampoo & Styling', 30.00, 45);


-- Insert TimeSlots
INSERT INTO time_slot_entity (time_slot_id, employee_id, date, start_time, end_time)
VALUES
    (1, 1, '2024-09-25', '10:00', '10:30'),
    (2, 2, '2024-09-25', '11:00', '11:30');


-- Insert Schedules
INSERT INTO schedule_entity (schedule_id, employee_id, day_of_week, start_time, end_time)
VALUES
    (1, 1, 'MONDAY', '09:00', '17:00'),
    (2, 2, 'TUESDAY', '10:00', '18:00');


-- Insert Bookings
INSERT INTO booking_entity (booking_id, customer_id, employee_id, booking_date, start_time, total_cost, total_duration, status)
VALUES
    (1, 1, 1, '2024-09-25', '10:00', 25.00, 30, 'PENDING'),
    (2, 2, 2, '2024-09-25', '11:00', 50.00, 60, 'PENDING');

-- Insert relations between bookings and orders
INSERT INTO booking_orders (booking_id, order_id)
VALUES
    (1, 1),
    (2, 2);
