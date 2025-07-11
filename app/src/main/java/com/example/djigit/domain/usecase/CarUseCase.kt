package com.example.djigit.domain.usecase

import com.example.djigit.data.repository.AuthRepository

class CarUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(brand: String, model: String, licensePlate: String) =
        repository.addCar(brand, model, licensePlate)
}
