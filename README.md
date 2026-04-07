# Shopping Cart Application with Database Localization

This project is a JavaFX-based Shopping Cart application developed as part of the OTP2 Database Localization Assignment. The application allows users to input items, prices, and quantities, calculates the total cost, and persists all data to a MariaDB/MySQL database.

## Key Features
**Database-Driven Localization**: UI strings are fetched dynamically from a database table (`localization_strings`) instead of standard properties files.

**Multi-Language Support**: Supports English, Finnish, Swedish, Japanese, and Arabic.

**RTL Support**: Automatic Right-to-Left (RTL) node orientation for Arabic.

**Data Persistence**: Saves detailed cart records and individual item breakdowns using a relational database schema with foreign keys.

## Prerequisites
* Java 17 or higher
* JavaFX SDK 21
* MariaDB or MySQL Server
* Maven

## Database Setup
To set up the required database environment, execute the following SQL commands in your MySQL/MariaDB terminal:

```sql
CREATE DATABASE IF NOT EXISTS shopping_cart_localization 
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;

USE shopping_cart_localization;

-- Main records table
CREATE TABLE IF NOT EXISTS cart_records (
    id INT AUTO_INCREMENT PRIMARY KEY,
    total_items INT NOT NULL,
    total_cost DOUBLE NOT NULL,
    language VARCHAR(10),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP
);

-- Individual items table
CREATE TABLE IF NOT EXISTS cart_items (
    id INT AUTO_INCREMENT PRIMARY KEY,
    cart_record_id INT,
    item_number INT NOT NULL,
    price DOUBLE NOT NULL,
    quantity INT NOT NULL,
    subtotal DOUBLE NOT NULL,
    FOREIGN KEY (cart_record_id) REFERENCES cart_records(id) ON DELETE CASCADE
);

-- UI localization table
CREATE TABLE IF NOT EXISTS localization_strings (
    id INT AUTO_INCREMENT PRIMARY KEY,
    `key` VARCHAR(100) NOT NULL,
    value VARCHAR(255) NOT NULL,
    language VARCHAR(10) NOT NULL
);
```
*(Note: Ensure you run the provided INSERT statements to populate the `localization_strings` table for all supported languages.)*

## Configuration
Update the `DatabaseConnection.java` file with your local database credentials:
* **URL**: `jdbc:mysql://localhost:3306/shopping_cart_localization`
* **User**: `root`
* **Password**: Your database password

## How to Run
1.  Clone the repository.
2.  Ensure your MariaDB/MySQL server is running and the database is populated.
3.  Run the application using Maven:
    ```bash
    mvn javafx:run
    ```

## DevOps
The repository includes the following files for automated deployment and containerization:
* **Dockerfile**: For containerizing the JavaFX application environment.
* **Jenkinsfile**: For CI/CD pipeline automation.