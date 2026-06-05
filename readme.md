Postgres creating on docker:

docker run -d --name my-postgres-server -p 5432:5432 -e POSTGRES_PASSWORD=mysecretpassword1881*! postgres

Creating db on container:

docker exec -it my-postgres-server psql -U postgres
CREATE DATABASE "my-test-database";
\l
\q