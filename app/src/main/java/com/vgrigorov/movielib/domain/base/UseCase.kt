package com.vgrigorov.movielib.domain.base

interface UseCase<I, R> {
    suspend fun execute(param: I): R
}

interface UseCaseNoSuspend<I, R> {
    fun execute(param: I): R
}