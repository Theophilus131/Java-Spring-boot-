CREATE TABLE orders (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        user_id BIGINT,
                        product_name VARCHAR(200),
                        amount DECIMAL(10,2),
                        created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                        CONSTRAINT fk_user FOREIGN KEY (user_id) REFERENCES users(id)
);
