# Spring warehouse manager

Данный проект демонстрирует прототип сервера для управления складами и находящимся на них товарами.

Сервер работает посредством REST API.

## API endpoints

### GET /api/getDoc
Показывает логированный документ по номеру. Для документа выводятся:
* Номер
* Дата
* Тип
* Содержание

**Обязательный параметр:**
docNumber - номер документа

### GET /api/getProduct
Выводит список всех товаров, зарегистрированных на всех складах. Для товаров показывается следующая информация:
* Артикул
* Наименование
* Цена последней закупки
* Цена последней продажи

**Дополнительные параметры:**
* name - для выдачи информации по конкретному товару

### GET /api/getStock
Выводит остаток товаров на всех складах. Для остатка товаров показывается следующая информация:
* Артикул
* Наименование
* Остаток

**Дополнительные параметры:**
* warehouse - для выдачи информации по конкретному складу


### POST /api/postAdmissionDoc
Принимает документ о поступлении товаров на один конкретный склад. Документ должен быть в формате JSON и содержать следующие поля (все строкового типа):
* number - номер документа, должен быть уникальным.
* warehouse - идентификатор склада.
* products - массив, содержащий данные о товарах. Может содержать данные о нескольких товарах одного артикула.

Товары для массива product должны содержать следующие поля:
* vendorCode - уникальный для данного типа товара артикул, целое положительное число со значением не больше 2^63 - 1.
* name - наименование товара. *Важно! Если товар с данным артикулом уже существует, то его имя будет перезаписано.*
* quantity - количество товара, только целое число больше нуля.
* price - стоимость покупки товара, не отрицательное целое или дробное число чез точку.

### POST /api/postSellingDoc
Принимает документ о продаже товаров с конкретного склада. Документ должен быть в формате JSON и содержать следующие поля (все строкового типа):
* number - номер документа, должен быть уникальным.
* warehouse - идентификатор склада.
* products - массив, содержащий данные о товарах. Может содержать данные о нескольких товарах одного артикула.

Товары для массива product должны содержать следующие поля:
* vendorCode - уникальный для данного типа товара артикул, целое положительное число со значением не больше 2^63 - 1.
* quantity - количество товара, только целое число больше нуля.
* price - стоимость продажи товара, не отрицательное целое или дробное число чез точку.

### POST /api/postMovingDoc
Принимает документ о перемещении товаров с одного склада на другой. Документ должен быть в формате JSON и содержать следующие поля (все строкового типа):
* number - номер документа, должен быть уникальным.
* fromWarehouse - идентификатор склада, ИЗ которого перемещается товар.
* toWarehouse - идентификатор склада, НА который перемещается товар.
* products - массив, содержащий данные о товарах. Может содержать данные о нескольких товарах одного артикула.

Товары для массива product должны содержать следующие поля:
* vendorCode - уникальный для данного типа товара артикул, целое положительное число со значением не больше 2^63 - 1.
* quantity - количество товара, только целое положительное число.

### POST /api/postNewWarehouse
Добавляет новый склад в базу данных. Запрос должен иметь "тело" в формате JSON со следующими полями (все строкового типа):
* warehouse - код нового склада.

