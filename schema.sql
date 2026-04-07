-- 1. Database Creation
CREATE DATABASE IF NOT EXISTS shopping_cart_localization
CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE shopping_cart_localization;

-- 2. Table for UI Localization Strings
-- Stores the 50 mandatory translations for English, Finnish, Swedish, Japanese, and Arabic
DROP TABLE IF EXISTS `localization_strings`;
CREATE TABLE `localization_strings` (
                                        `id` INT NOT NULL AUTO_INCREMENT,
                                        `key` VARCHAR(100) NOT NULL,
                                        `value` VARCHAR(255) NOT NULL,
                                        `language` VARCHAR(10) NOT NULL,
                                        PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 3. Table for Main Cart Records
-- Stores the summary of each checkout session
DROP TABLE IF EXISTS `cart_records`;
CREATE TABLE `cart_records` (
                                `id` INT NOT NULL AUTO_INCREMENT,
                                `total_items` INT NOT NULL,
                                `total_cost` DOUBLE NOT NULL,
                                `language` VARCHAR(10) DEFAULT NULL,
                                `created_at` TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
                                PRIMARY KEY (`id`)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 4. Table for Individual Items
-- Relational table linked to cart_records via Foreign Key
DROP TABLE IF EXISTS `cart_items`;
CREATE TABLE `cart_items` (
                              `id` INT NOT NULL AUTO_INCREMENT,
                              `cart_record_id` INT DEFAULT NULL,
                              `item_number` INT NOT NULL,
                              `price` DOUBLE NOT NULL,
                              `quantity` INT NOT NULL,
                              `subtotal` DOUBLE NOT NULL,
                              PRIMARY KEY (`id`),
                              KEY `cart_record_id` (`cart_record_id`),
                              CONSTRAINT `fk_cart_record` FOREIGN KEY (`cart_record_id`)
                                  REFERENCES `cart_records` (`id`) ON DELETE CASCADE
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- 5. Insert Localization Data
INSERT INTO `localization_strings` (`key`, `value`, `language`) VALUES
                                                                    ('select_language','Select Language','en'),
                                                                    ('confirm_language','Confirm Language','en'),
                                                                    ('enter_num_items','Enter number of items','en'),
                                                                    ('start','Start','en'),
                                                                    ('next_item','Next Item','en'),
                                                                    ('calculate','Calculate','en'),
                                                                    ('total_cost','Total Cost','en'),
                                                                    ('reset','Reset','en'),
                                                                    ('price','Enter price','en'),
                                                                    ('quantity','Enter quantity','en'),

                                                                    ('select_language','Valitse kieli','fi'),
                                                                    ('confirm_language','Vahvista kieli','fi'),
                                                                    ('enter_num_items','Anna tuotteiden määrä','fi'),
                                                                    ('start','Aloita','fi'),
                                                                    ('next_item','Seuraava tuote','fi'),
                                                                    ('calculate','Laske','fi'),
                                                                    ('total_cost','Kokonaishinta','fi'),
                                                                    ('reset','Tyhjennä','fi'),
                                                                    ('price','Anna hinta','fi'),
                                                                    ('quantity','Anna määrä','fi'),

                                                                    ('select_language','Välj språk','sv'),
                                                                    ('confirm_language','Bekräfta språk','sv'),
                                                                    ('enter_num_items','Ange antal artiklar','sv'),
                                                                    ('start','Starta','sv'),
                                                                    ('next_item','Nästa objekt','sv'),
                                                                    ('calculate','Beräkna','sv'),
                                                                    ('total_cost','Total kostnad','sv'),
                                                                    ('reset','Återställ','sv'),
                                                                    ('price','Ange pris','sv'),
                                                                    ('quantity','Ange kvantitet','sv'),

                                                                    ('select_language','言語を選択','ja'),
                                                                    ('confirm_language','言語を確認','ja'),
                                                                    ('enter_num_items','アイテム数を入力','ja'),
                                                                    ('start','開始','ja'),
                                                                    ('next_item','次のアイテム','ja'),
                                                                    ('calculate','計算する','ja'),
                                                                    ('total_cost','合計金額','ja'),
                                                                    ('reset','リセット','ja'),
                                                                    ('price','価格を入力','ja'),
                                                                    ('quantity','数量を入力','ja'),

                                                                    ('select_language','اختر اللغة','ar'),
                                                                    ('confirm_language','تأكيد اللغة','ar'),
                                                                    ('enter_num_items','أدخل عدد العناصر','ar'),
                                                                    ('start','ابدأ','ar'),
                                                                    ('next_item','العنصر التالي','ar'),
                                                                    ('calculate','احسب','ar'),
                                                                    ('total_cost','التكلفة الإجمالية','ar'),
                                                                    ('reset','إعادة ضبط','ar'),
                                                                    ('price','أدخل السعر','ar'),
                                                                    ('quantity','أدخل الكمية','ar');