package com.etl;


import com.etl.model.ErrorEntity;
import com.etl.repository.ErrorRepository;
import com.etl.service.CSVService;
import com.etl.service.ReportService;
import com.lowagie.text.DocumentException;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import static org.junit.jupiter.api.Assertions.assertNotNull;

import java.io.ByteArrayInputStream;
import java.util.ArrayList;
import java.util.List;
import java.time.LocalDateTime;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.times;

@SpringBootTest
public class ReportServiceTest {

    @Mock
    private CSVService csvService;

    @Mock
    private ErrorRepository errorEntityRepository;

    @InjectMocks
    private ReportService reportService;

    @Test
    public void testGenerateErrorReport() throws DocumentException {
        // Mock CSVService and ErrorRepository
        Mockito.when(csvService.getCombinedReport()).thenReturn("123"); // Example data count
        List<ErrorEntity> errorEntities = new ArrayList<>();
        errorEntities.add(createErrorEntity(1L, "Error 1", "Entity 1"));
        errorEntities.add(createErrorEntity(2L, "Error 2", "Entity 2"));
        Mockito.when(errorEntityRepository.findAll()).thenReturn(errorEntities);

        // Generate the error report
        ByteArrayInputStream report = reportService.generateErrorReport();

        // You can add assertions based on your specific PDF content
        // For example, you can use a PDF parsing library to check the content.
        // However, here we're just checking that the report is not null.
        assertNotNull(report);
    }

    private ErrorEntity createErrorEntity(Long i, String errorMessage, String entityName) {
        ErrorEntity errorEntity = new ErrorEntity();
        errorEntity.seterrorId(i);
        errorEntity.setErrorMessage(errorMessage);
        errorEntity.setEntityName(entityName);
        errorEntity.setTimestamp(LocalDateTime.now());
        return errorEntity;
    }
}