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
    private final CarOperationCounters carOperationCounters;

    public CarService(CarRepository carRepository,
                      CarOperationCounters carOperationCounters) {
        this.carRepository = carRepository;
        this.carOperationCounters = carOperationCounters;
    }

    public Mono<Car> findById(String id) {
        return carRepository.findById(id)
                .doOnNext(car -> carOperationCounters.incrementList());
    }

    public Flux<Car> findAll() {
        return carRepository.findAll()
                .doOnNext(car -> carOperationCounters.incrementList());
    }

    public Mono<Car> addCar (Mono<Car> carMono) {
        return carRepository.addCar(carMono)
                .doOnNext(car -> carOperationCounters.incrementAdd());
    }

    public Flux<Car> addAllCars(Mono<List<Car>> allNewCarsMono) {
        return carRepository.addAllCars(allNewCarsMono)
                .doOnNext(car -> carOperationCounters.incrementAdd());
    }

    public Mono<Integer> restore () {
        carOperationCounters.incrementDelete();
        return carRepository.restore();
    }

    public Mono<Integer> reportCarCount () {
        return carRepository.reportSize();
    }

    public Mono<Car> deleteCar (String carId) {
        return carRepository.deleteById(carId)
                .doOnNext(car -> carOperationCounters.incrementDelete());
    }

}
