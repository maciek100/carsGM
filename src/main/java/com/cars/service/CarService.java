package com.cars.service;

import com.cars.model.Car;
import com.cars.repository.CarRepository;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class CarService {
    private final CarRepository carRepository;

    public CarService(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    public Mono<Car> findById(String id) {
        return carRepository.findById(id);
    }

    public Flux<Car> findAll() {
        return carRepository.findAll();
    }

    public Mono<Car> addCar (Mono<Car> carMono) {
        return carRepository.addCar(carMono);
    }

    public Flux<Car> addAllCars(Mono<List<Car>> allNewCars) {
       return carRepository.addAllCars(allNewCars);
    }

    public Mono<Integer> restore () {
        return carRepository.restore();
    }

    public Mono<Integer> reportCarCount () {
        return carRepository.reportSize();
    }

    public Mono<Car> deleteCar (String carId) {
        return carRepository.deleteById(carId);
    }

}
