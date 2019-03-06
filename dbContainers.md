```
sudo docker run -d \
    --name mongodb-spring-test \
    -e MONGO_INITDB_ROOT_USERNAME=admin \
    -e MONGO_INITDB_ROOT_PASSWORD=senhaadmin \
    -p 6000:27017 \
    mongo:4.0.5
```
```
sudo docker exec -ti mongodb-express-test \
    mongo --host localhost -u admin -p senhaadmin --authenticationDatabase admin \
    --eval "db.getSiblingDB('peoples').createUser({user: 'user', pwd: 'userpasswd', roles: [{role: 'readWrite', db: 'peoples'}]})"
```
mongodb://user:userpasswd@localhost:6000/peoples


```
sudo docker run -d \
    --name postgres-spring-test \
    -e POSTGRES_USER=user \
    -e POSTGRES_PASSWORD=userpasswd \
    -e POSTGRES_DB=users \
    -p 6001:5432 \
    postgres:11.1
```
postgres://user:userpasswd@localhost:6001/users
