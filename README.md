Checklist for correct working the application:

1. Ensure that server port 8080 is available
2. Ensure that PostgreSQL is installed
3. Execute init_script.sql into your database
4. create file application.properties in the project root and fill it with properties from the file
   application.properties.original
5. use this endpoints for CRUD operations with Category entity: http://localhost:8080/api/v1/category
    * create:   POST http://localhost:8080/api/v1/category body should contain data in json format, for example:
      {
      "name": "IT",
      "request": "cat"
      }
    * retrieve: GET http://localhost:8080/api/v1/category/{id} , for example http://localhost:8080/api/v1/category/1
    * update:   PUT http://localhost:8080/api/v1/category body should contain data in json format, for example:
      {
      "id": 1,
      "name": "OOO Category",
      "request": "param"
      }
    * delete:   DELETE http://localhost:8080/api/v1/category/{id} , for example http://localhost:8080/api/v1/category/1
6. use this endpoints for CRUD operations with Banner entity: http://localhost:8080/api/v1/banner
    * create:   POST http://localhost:8080/api/v1/banner body should contain data in json format, for example:
      {
      "name": "Banner 1",
      "body": "body 1",
      "price": 9.99,
      "categories": [
      {
      "id": 1,
      "name": "OOO Category",
      "request": "param"
      },
      {
      "id": 2,
      "name": "IT",
      "request": "cat"
      }
      ]
      }
    * retrieve: GET http://localhost:8080/api/v1/banner/{id} , for example http://localhost:8080/api/v1/banner/1
    * update:   PUT http://localhost:8080/api/v1/banner body should contain data in json format, for example:
      {
      "id": 1,
      "name": "Banner 1",
      "body": "body 1",
      "price": 9.99,
      "categories": [
      {
      "id": 1,
      "name": "OOO Category",
      "request": "param"
      },
      {
      "id": 2,
      "name": "IT",
      "request": "cat"
      }
      ]
      }
    * delete:   DELETE http://localhost:8080/api/v1/banner/{id} , for example http://localhost:8080/api/v1/banner/1
7. use this endpoint for searching banners GET http://localhost:8080/api/v1/search/banner/bid?...
   for example GET http://localhost:8080/api/v1/search/banner/bid?cat=IT&param=ooo&cat=mem
8. user credentials which you can use for log in:
    * username: user   password: password -  has no access to CRUD operations with Banner and Category entities
    * username: admin   password: password - has full access