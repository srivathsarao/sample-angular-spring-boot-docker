package com.test.transactionservice.config;

import com.test.transactionservice.model.Transaction;
import com.test.transactionservice.model.TransactionIntermediate;
import lombok.extern.slf4j.Slf4j;
import org.springframework.batch.core.Job;
import org.springframework.batch.core.Step;
import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.database.JpaItemWriter;
import org.springframework.batch.item.file.FlatFileItemReader;
import org.springframework.batch.item.file.LineMapper;
import org.springframework.batch.item.file.builder.FlatFileItemReaderBuilder;
import org.springframework.batch.item.file.mapping.BeanWrapperFieldSetMapper;
import org.springframework.batch.item.file.mapping.DefaultLineMapper;
import org.springframework.batch.item.file.mapping.FieldSetMapper;
import org.springframework.batch.item.file.transform.FixedLengthTokenizer;
import org.springframework.batch.item.file.transform.LineTokenizer;
import org.springframework.batch.item.file.transform.Range;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.ClassPathResource;

import javax.persistence.EntityManagerFactory;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

@Slf4j
@Configuration
@EnableBatchProcessing
public class BatchConfig {

    @Autowired
    public JobBuilderFactory jobBuilderFactory;

    @Autowired
    public StepBuilderFactory stepBuilderFactory;

    @Autowired
    private EntityManagerFactory entityManagerFactory;

    private DateFormat dateFormat = new SimpleDateFormat("yyyyMMdd");

    @Bean
    public FlatFileItemReader<TransactionIntermediate> reader() {
        return new FlatFileItemReaderBuilder<TransactionIntermediate>()
                .name("transactionReader")
                .resource(new ClassPathResource("Input.txt"))
                .lineMapper(createTransactionLineMapper())
                .build();
    }

    @Bean
    public ItemProcessor<TransactionIntermediate, Transaction> processor() {
        log.debug("Creating Item Processor for processing the items");
        return (item) -> {
            item.getProductInformation().setExpirationDate(dateFormat.parse(item.getExpirationDate()));
            log.debug("Processing line ", item);
            return new Transaction(null,
                    item.getClientInformation(),
                    item.getProductInformation(),
                    item.getQuantityLong().subtract(item.getQuantityShort()).abs(),
                    dateFormat.parse(item.getTransactionDate()));
        };
    }

    @Bean
    public JpaItemWriter writer() {
        JpaItemWriter writer = new JpaItemWriter();
        writer.setEntityManagerFactory(entityManagerFactory);
        return writer;
    }

    @Bean
    public Job importUserJob(Step step1) {
        return jobBuilderFactory.get("importUserJob")
                .incrementer(new RunIdIncrementer())
                .flow(step1)
                .end()
                .build();
    }

    @Bean
    public Step step1() {
        return stepBuilderFactory.get("step1")
                .<TransactionIntermediate, Transaction>chunk(10)
                .reader(reader())
                .processor(processor())
                .writer(writer())
                .build();
    }

    private LineMapper<TransactionIntermediate> createTransactionLineMapper() {
        log.debug("Creating Transaction Line Mapper");
        DefaultLineMapper<TransactionIntermediate> transactionLineMapper = new DefaultLineMapper<>();

        LineTokenizer transactionLineTokenizer = fixedLengthTokenizer();
        transactionLineMapper.setLineTokenizer(transactionLineTokenizer);

        FieldSetMapper<TransactionIntermediate> transactionInformationMapper = createTransactionInformationMapper();
        transactionLineMapper.setFieldSetMapper(transactionInformationMapper);

        log.debug("Creating Transaction Line Mapper");
        return transactionLineMapper;
    }

    private FieldSetMapper<TransactionIntermediate> createTransactionInformationMapper() {
        log.debug("Creating Transaction Information Mapper");
        BeanWrapperFieldSetMapper<TransactionIntermediate> transactionInformationMapper = new BeanWrapperFieldSetMapper<>();
        transactionInformationMapper.setTargetType(TransactionIntermediate.class);
        log.debug("Created Transaction Information Mapper");
        return transactionInformationMapper;
    }

    @Bean
    public FixedLengthTokenizer fixedLengthTokenizer() {
        log.debug("Creating Fixed length tokenizer");
        FixedLengthTokenizer tokenizer = new FixedLengthTokenizer();

        tokenizer.setStrict(false);
        tokenizer.setNames("clientInformation.clientType",
                "clientInformation.clientNumber",
                "clientInformation.accountNumber",
                "clientInformation.subAccountNumber",
                "productInformation.symbol",
                "productInformation.productGroupCode",
                "productInformation.exchangeCode",
                "expirationDate",
                "quantityLong",
                "quantityShort",
                "transactionDate"
        );
        log.debug("created tokenizer with names");
        tokenizer.setColumns(
                new Range(4, 7),
                new Range(8, 11),
                new Range(12, 15),
                new Range(16, 19),
                new Range(32, 37),
                new Range(26, 27),
                new Range(28, 31),
                new Range(38, 45),
                new Range(64, 73),
                new Range(53, 62),
                new Range(122, 129)
        );
        log.debug("Created tokenizer with ranges");
        return tokenizer;
    }
}
