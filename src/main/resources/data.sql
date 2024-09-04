-- Create the users table if it does not exist
CREATE TABLE IF NOT EXISTS users (
                                     id BIGINT PRIMARY KEY,
                                     username VARCHAR(255) NOT NULL UNIQUE,
                                     password VARCHAR(255) NOT NULL
);

-- Create the user_entity_roles table if it does not exist
CREATE TABLE IF NOT EXISTS user_entity_roles (
                                                 user_entity_id BIGINT,
                                                 roles VARCHAR(255),
                                                 FOREIGN KEY (user_entity_id) REFERENCES users(id)
);

-- Insert initial data
INSERT INTO users (id, username, password) VALUES (1, 'testuser', '$2a$10$DowJonesIndexPasswordHash');
INSERT INTO user_entity_roles (user_entity_id, roles) VALUES (1, 'ROLE_USER');