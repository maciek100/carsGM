package com.cars.service;

import com.cars.repository.CarRepository;
import org.springframework.boot.context.event.ApplicationReadyEvent;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;
import java.util.concurrent.atomic.AtomicLong;

@Component
public class CarOperationCounters {

    private final AtomicLong carAddCounter = new AtomicLong(0);
    private final AtomicLong carDeleteCounter = new AtomicLong(0);
    private final AtomicLong carListCounter = new AtomicLong(0);
    private final AtomicLong carsToStartWith = new AtomicLong(0);

    private final CarRepository carRepository;

    public CarOperationCounters(CarRepository carRepository) {
        this.carRepository = carRepository;
    }

    @EventListener(ApplicationReadyEvent.class)
    public void initializeStartCount () {
        carRepository.reportSize()
                .subscribe(carsToStartWith::set);
    }

    public void incrementAdd() {
        carAddCounter.incrementAndGet();
    }

    public void incrementDelete() {
        carDeleteCounter.incrementAndGet();
    }

    public void incrementList() {
        carListCounter.incrementAndGet();
    }

    public long getCarAddCount() {
        return carAddCounter.get();
    }

    public long getCarDeleteCount() {
        return carDeleteCounter.get();
    }

    public long getCarListCount() {
        return carListCounter.get();
    }

    public long getInitialCarCount () {
        return carsToStartWith.get();
    }
}

