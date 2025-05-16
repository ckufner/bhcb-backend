package com.example.bhcbbackend.configurations.lucene;

import org.apache.lucene.analysis.core.LowerCaseFilterFactory;
import org.apache.lucene.analysis.ngram.EdgeNGramFilterFactory;
import org.apache.lucene.analysis.snowball.SnowballPorterFilterFactory;
import org.apache.lucene.analysis.standard.StandardTokenizerFactory;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurationContext;
import org.hibernate.search.backend.lucene.analysis.LuceneAnalysisConfigurer;

public class AnalysisConfigurer implements LuceneAnalysisConfigurer
{
    @Override
    public void configure(final LuceneAnalysisConfigurationContext luceneAnalysisConfigurationContext)
    {
        luceneAnalysisConfigurationContext.analyzer("german").custom()
                .tokenizer(StandardTokenizerFactory.class)
                .tokenFilter(LowerCaseFilterFactory.class)
                .tokenFilter(SnowballPorterFilterFactory.class)
                .param("language", "German")
                .tokenFilter(EdgeNGramFilterFactory.class)
                .param("minGramSize", "2")
                .param("maxGramSize", "15");
    }
}
