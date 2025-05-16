package com.example.bhcbbackend.services;

import jakarta.persistence.EntityManager;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.hibernate.search.mapper.orm.Search;
import org.hibernate.search.mapper.orm.session.SearchSession;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextRefreshedEvent;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
@Slf4j
class IndexingService implements ApplicationListener<ContextRefreshedEvent>
{
    private final EntityManager entityManager;

    @Override
    @Transactional
    public void onApplicationEvent(final ContextRefreshedEvent event)
    {
        SearchSession searchSession = Search.session(this.entityManager);

        try
        {
            searchSession.massIndexer().idFetchSize(100).batchSizeToLoadObjects(50).threadsToLoadObjects(8).startAndWait();
        }
        catch (InterruptedException ex)
        {
            log.error("{}", ex.getMessage(), ex);
            Thread.currentThread().interrupt();
        }
    }
}
