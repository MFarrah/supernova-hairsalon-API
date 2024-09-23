-- Insert Admins
INSERT INTO admin_entity (admin_id, first_name, last_name, email, password)
VALUES (1, 'Super', 'Admin', 'admin@supernova.api', '$2b$12$SkEhsz6J85d2gKxDnkXcJO4BVnmrp8rpMa0DSzTIjIuMJ1f7grKJy');

INSERT INTO admin_roles (admin_id, role)
VALUES (1, 'ADMIN');


-- Insert Employees (voor PostgreSQL array types)
INSERT INTO employee_entity (employee_id, first_name, last_name, email, phone_number, password, gender, roles, qualified)
VALUES
    (1, 'John', 'Doe', 'john.doe@supernova.api', '0612345678', '$2b$12$S5AMlO3lgPPaT85H3JObouOLCqi2mPFFXiztVDh36Xw3fXWSEQxpu', 'MALE', 'EMPLOYEE', '{1,2}'),
    (2, 'Jane', 'Smith', 'jane.smith@supernova.api', '0612345679', '$2b$12$BWayQCG9aFw7F/OHn05IZ.w35DIKJAo/ZRYUlCfo3eLvauU66J0gS', 'FEMALE', 'EMPLOYEE', '{2,3}');,
    (3, 'Emma', 'Jones', 'emma.jones@supernova.api', '0612345680', '$2b$12$1ukB85x8KpaMGsubQFChDenoimKLPeyejj9m5a8i6gaZ4ZcQH4jk.', 'FEMALE', 'EMPLOYEE', '{1,3}'),
    (4, 'Mike', 'Johnson', 'mike.johnson@supernova.api', '0612345681', '$2b$12$rFh4vgmHZGmJFySLQFz6S.dR8w.IddoCfCEiVkFDgbVPY4fNS.Bby', 'MALE', 'EMPLOYEE', '{1,2,3}'),
    (5, 'Lucy', 'Brown', 'lucy.brown@supernova.api', '0612345682', '$2b$12$V3qG0kFqe3UqCGFKdRZmAuK58iQxV/ns8p6EwK/MdMcjnZn.SlQh2', 'FEMALE', 'EMPLOYEE,ADMIN', '{1}');

-- Insert Customers
INSERT INTO customer_entity (customer_id, first_name, last_name, email, phone_number, password, gender, roles)
VALUES
    (1, 'Chris', 'Evans', 'chris.evans@example.com', '0612345683', '$2b$12$UV0cQYO2rGmyqb8Hq7Y13eJD1UFK4gwIvggY1ff2C6GiVN16sHJl2', 'MALE', 'CUSTOMER'),
    (2, 'Anna', 'Taylor', 'anna.taylor@example.com', '0612345684', '$2b$12$6G9lBv.nXhOWg/UbcNSOJOVebjx1lEtGgtdPwqVHpZDA5Qx1Ds1ha', 'FEMALE', 'CUSTOMER'),
    (3, 'Mark', 'Wilson', 'mark.wilson@example.com', '0612345685', '$2b$12$YCBt5tW24YU7FHgQ65ZE6ucGouVsD/rOpi4aymmN2BDlExJiIWWMm', 'MALE', 'CUSTOMER'),
    (4, 'Sophie', 'Miller', 'sophie.miller@example.com', '0612345686', '$2b$12$t7I37YBhjGbuPoA5IsiDLOsC155JjZ/bP9EVh4koFmHnUQZSyNsQK', 'FEMALE', 'CUSTOMER'),
    (5, 'Tom', 'Hanks', 'tom.hanks@example.com', '0612345687', '$2b$12$D6nlVHMXAY0.jIDAMssyH.AeaiX8b4NqrqoCivaPClnIFKqxDiDke', 'MALE', 'CUSTOMER');
