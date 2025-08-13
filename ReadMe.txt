This is a simple "demo" showing Reactor stream operations on cars.
On the start it initializes the repository with a list of Car objects and allows
the user to list, delete, and add new cars. On the top of it, it maintains
the count of cars involved in all the operations.
call to http://localhost:8080/actuator/stats would reveal simple list:
{
  "Cars Initially": 38,
  "Cars Added": 2,
  "Cars Deleted": 1,
  "Cars Listed": 40
}

It is not 100% solid statistics, but it mainly illustrates the use of Spring Boot
actuators.

Here is a list of regular calls to be made to the /cars endpoint (using curl):
# List all the cars in the repository
curl -X GET "http://localhost:8080/cars"

# Display a car with a particular id:
curl -X GET "http://localhost:8080/cars/car4"
    # Returns: {"id":"car4","name":"Honda"}%

#Add one car
curl -X POST http://localhost:8080/cars/add \
     -H "Content-Type: application/json" \
     -d '{"id":"car999","name":"Corvette"}'
    #Returns: {"id":"car999","name":"Corvette"}%

# Add a list of cars
curl -X POST http://localhost:8080/cars/addAll \
     -H "Content-Type: application/json" \
     -d '[{"id":"car11127","name":"Scout"},{"id":"CAR753","name":"Bentley"}]'
    # Returns: [{"id":"car11127","name":"Scout"},{"id":"car753","name":"Bentley"}]%

# Delete a car with id
curl -X DELETE "http://localhost:8080/cars/remove/car22"
#Returns: {"id":"car22","name":"Dodge"}%

For any operation where requested for listing or deletion car is NOT in the repository,
the appropriate HTTP:404 will be returned.
