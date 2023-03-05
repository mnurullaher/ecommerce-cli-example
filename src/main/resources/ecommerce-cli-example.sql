USE ecommerce_database;

CREATE TABLE users(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(50),
	email VARCHAR(50),
	createdAt TIMESTAMP,
	PRIMARY KEY(id)
);

CREATE TABLE products(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(50),
	price INT,
	createdAt TIMESTAMP,
	PRIMARY KEY(id)
);

CREATE TABLE categories(
	id INT NOT NULL AUTO_INCREMENT,
	name VARCHAR(50),
	createdAt TIMESTAMP,
	PRIMARY KEY (id)
);

CREATE TABLE categories_products(
	products_id INT,
	categories_id INT,
	FOREIGN KEY (products_id) REFERENCES products(id),
	FOREIGN KEY (categories_id) REFERENCES categories(id),
	CONSTRAINT pk_product_categories PRIMARY KEY(products_id, categories_id)
);

CREATE TABLE orders(
	id INT NOT NULL AUTO_INCREMENT,
	userId INT,
	createdAt TIMESTAMP,
	PRIMARY KEY(id),
	FOREIGN KEY (userId) REFERENCES users(id)
);

CREATE TABLE OrderItem(
    id INT NOT NULL AUTO_INCREMENT,
	orderId INT,
	productId INT,
	quantity INT,
	FOREIGN KEY (orderId) REFERENCES orders(id),
	FOREIGN KEY (productId) REFERENCES products(id)
	PRIMARY KEY (id)
);