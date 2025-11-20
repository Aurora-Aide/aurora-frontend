package com.example.aurora.domain.usecase

import com.example.aurora.data.repository.AuthRepository

class CarUseCase(private val repository: AuthRepository) {
    suspend operator fun invoke(brand: String, model: String, licensePlate: String) =
        repository.addCar(brand, model, licensePlate)
}
