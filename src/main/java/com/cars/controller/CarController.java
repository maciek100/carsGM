package com.cars.controller;


import com.cars.model.Car;
import com.cars.service.CarService;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.time.Duration;
import java.util.List;

@RestController
@RequestMapping("/cars")
public class CarController {
    private final CarService carService;

    public CarController(CarService carService) {
        this.carService = carService;
    }

    @GetMapping("/restore")
    public Mono<Integer> restoreData () {
        return carService.restore();
    }

    @GetMapping("/count")
    public Mono<Integer> reportCarCount () {
        return carService.reportCarCount();
    }

    @GetMapping("/{id}")
    public Mono<Car> getCarById(@PathVariable String id) {
        return carService.findById(id);
    }

    @GetMapping(value = "", produces = MediaType.TEXT_EVENT_STREAM_VALUE)
    public Flux<Car> getAllCars() {
        return carService.findAll().delayElements(Duration.ofMillis(250));
    }

    @PostMapping("/add")
    public Mono<Car> addCar(@RequestBody Mono<Car> carMono) {
        return carService.addCar(carMono
                .map(car -> new Car(car.id().toLowerCase(), car.name())));
    }

    @PostMapping("/addAll")
    public Flux<Car> addAllCars(@RequestBody Mono<List<Car>> allNewCarsMono) {
        Mono<List<Car>> sanitized = allNewCarsMono.map(list ->
                list.stream()
                        .map(car -> new Car(car.id().toLowerCase(), car.name()))
                        .toList()
        );
        return carService.addAllCars(sanitized);
    }

    @DeleteMapping("/remove/{id}")
    public Mono<Car> deleteCar(@PathVariable("id") String id) {
        return carService.deleteCar(id);
    }


}
