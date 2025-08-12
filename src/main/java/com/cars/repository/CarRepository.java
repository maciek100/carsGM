package com.cars.repository;

import com.cars.model.Car;
import org.springframework.stereotype.Repository;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Repository
public class CarRepository {
    private static final List<Car> allCars = List.of(
            new Car("car1", "Chevrolet"),
            new Car("car2", "Subaru"),
            new Car("car3", "Toyota"),
            new Car("car4", "Honda"),
            new Car("car5", "Ford"),
            new Car("car6", "Hyundai"),
            new Car("car7", "Lincoln"),
            new Car("car8", "GMC"),
            new Car("car9", "Kia"),
            new Car("car10", "Tesla"),
            new Car("car11", "Fiat"),
            new Car("car12", "Citroen"),
            new Car("car13", "Suzuki"),
            new Car("car14", "Cadillac"),
            new Car("car15", "Mercedes"),
            new Car("car16", "BMW"),
            new Car("car17", "Lexus"),
            new Car("car18", "Lucid"),
            new Car("car19", "Chrysler"),
            new Car("car20", "Bugatti"),
            new Car("car21", "Buick"),
            new Car("car22", "Dodge"),
            new Car("car23", "Rivian"),
            new Car("car24", "Mazda"),
            new Car("car25", "Nissan"),
            new Car("car26", "Acura"),
            new Car("car27", "Mitsubishi"),
            new Car("car28", "Jaguar"),
            new Car("car29", "Maserati"),
            new Car("car30", "Lotus"),
            new Car("car31", "Mini"),
            new Car("car32", "Abarth"),
            new Car("car33", "Genesis"),
            new Car("car34", "Jeep"),
            new Car("car35", "Audi"),
            new Car("car36", "Jaguar"),
            new Car("car37", "Infinity"),
            new Car("car38", "Land Rover")
    );
    private static List<Car> activeCars = new ArrayList<>();
    //static {
    //    allCars.stream()
    //            .forEach(car -> activeCars.add(new Car(car.id(), car.name())));
    //}

    public CarRepository () {
        restore();
    }
    public Flux<Car> findAll() {
        return Flux.fromIterable(activeCars);
    }

    public Mono<Car> findById(String targetId) {
       Optional<Car> carOpt = activeCars.stream()
                .filter(car -> car.id().equals(targetId))
                .findFirst();
        return carOpt.map(Mono::just).orElseGet(Mono::empty);
    }

    public Mono<Car> addCar(Mono<Car> carMono) {
        return carMono.flatMap(car -> {
            if (!activeCars.contains(car)) {
                activeCars.add(car);
                return Mono.just(car);
            } else {
                return Mono.empty();
            }
        });
    }
    public Mono<Car> deleteById(String targetId) {
        //activeCars.removeIf(car -> car.id().equals(targetId));
        Optional<Car> carToRemove = activeCars.stream()
                .filter(car -> car.id().equals(targetId))
                .findFirst();
        carToRemove.ifPresent(activeCars::remove);
        return Mono.justOrEmpty(carToRemove);
    }

    public Flux<Car> addAllCars(Mono<List<Car>> allNewCars) {
        return allNewCars
                .flatMapMany(Flux::fromIterable)
                .flatMap(car -> addCar(Mono.just(car)))
                .onErrorContinue((e,o) -> {
                    Car car = (Car)o;
                    System.err.println("On car " + car + " encountered an error " + e.getMessage());
                });
    }

    public Mono<Integer> restore () {
        activeCars.clear();
        allCars.forEach(car -> activeCars.add(new Car(car.id(), car.name())));
        return Mono.just(activeCars.size());
    }

    public Mono<Integer> reportSize () {
        return Mono.just(activeCars.size());
    }
}
