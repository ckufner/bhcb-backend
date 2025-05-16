package com.example.bhcbbackend.services.util;

import lombok.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionDefinition;
import org.springframework.transaction.support.TransactionTemplate;

import java.util.function.Supplier;

@Service
class TransactionServiceImpl implements TransactionService
{
    private final TransactionTemplate transactionTemplate;
    private final TransactionTemplate readonlyTransactionTemplate;

    TransactionServiceImpl(
            @NonNull final PlatformTransactionManager platformTransactionManager
    )
    {
        this.transactionTemplate = new TransactionTemplate(platformTransactionManager);
        this.transactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);

        this.readonlyTransactionTemplate = new TransactionTemplate(platformTransactionManager);
        this.readonlyTransactionTemplate.setPropagationBehavior(TransactionDefinition.PROPAGATION_REQUIRES_NEW);
        this.readonlyTransactionTemplate.setReadOnly(true);
    }

    @Override
    public <E> E read(
            @NonNull final Supplier<E> func
    )
    {
        return this.readonlyTransactionTemplate.execute(status -> func.get());
    }

    @Override
    public <E> E execute(
            @NonNull final Supplier<E> func
    )
    {
        return this.transactionTemplate.execute(status -> func.get());
    }

    @Override
    public void executeWithoutResult(
            @NonNull final Runnable runnable
    )
    {
        this.transactionTemplate.executeWithoutResult(status -> runnable.run());
    }
}
