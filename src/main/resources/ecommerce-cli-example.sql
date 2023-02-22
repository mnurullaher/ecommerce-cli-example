USE ecommerce_database;

CREATE TABLE users(
	user_id INT NOT NULL AUTO_INCREMENT,
	user_name VARCHAR(50),
	user_email VARCHAR(50),
	createdAt TIMESTAMP,
	PRIMARY KEY(user_id)
);

CREATE TABLE products(
	product_id INT NOT NULL AUTO_INCREMENT,
	product_name VARCHAR(50),
	product_price DOUBLE,
	createdAt TIMESTAMP,
	PRIMARY KEY(product_id)
);

CREATE TABLE categories(
	category_id INT NOT NULL AUTO_INCREMENT,
	category_name VARCHAR(50),
	createdAt TIMESTAMP,
	PRIMARY KEY (category_id)
);

CREATE TABLE product_categories(
	product_id INT,
	category_id INT,
	FOREIGN KEY (product_id) REFERENCES products(product_id),
	FOREIGN KEY (category_id) REFERENCES categories(category_id)
);

CREATE TABLE orders(
	order_id INT NOT NULL AUTO_INCREMENT,
	user_id INT,
	status VARCHAR(50),
	createdAt TIMESTAMP,
	PRIMARY KEY(order_id),
	FOREIGN KEY (user_id) REFERENCES users(user_id)
);

CREATE TABLE order_items(
	order_id INT,
	product_id INT,
	quantity INT,
	FOREIGN KEY (order_id) REFERENCES orders(order_id),
	FOREIGN KEY (product_id) REFERENCES products(product_id)
);