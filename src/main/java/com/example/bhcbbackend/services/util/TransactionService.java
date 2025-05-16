package com.example.bhcbbackend.services.util;

import lombok.NonNull;

import java.util.function.Supplier;

public interface TransactionService
{
    <E> E read(@NonNull Supplier<E> func);

    <E> E execute(@NonNull Supplier<E> func);

    void executeWithoutResult(@NonNull Runnable runnable);
}

