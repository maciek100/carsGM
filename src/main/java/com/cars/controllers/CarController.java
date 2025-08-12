package com.cars.controllers;


import com.cars.model.Car;
import com.cars.services.CarService;
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
        Mono<Car> uppercaseIdCar =  carMono
                .map(car -> {
                    String lowercaseId = car.id().toLowerCase();
                    return new Car(lowercaseId, car.name());
                });
        return carService.addCar(uppercaseIdCar);
    }

    @PostMapping("/addAll")
    public Flux<Car> addAllCars(@RequestBody Mono<List<Car>> allNewCarsMono) {
        return carService.addAllCars(allNewCarsMono);
    }


}
