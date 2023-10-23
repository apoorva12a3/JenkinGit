package com.etl;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.doNothing;
import static org.mockito.Mockito.doThrow;
import static org.mockito.Mockito.times;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.verifyNoInteractions;
import static org.mockito.Mockito.verifyNoMoreInteractions;
import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.mock.web.MockMultipartFile;

import com.etl.controller.CSVController;
import com.etl.helper.CSVHelper;
import com.etl.message.ResponseMessage;
import com.etl.service.CSVService;

@SpringBootTest(classes = CSVControllerTest.TestConfig.class)
public class CSVControllerTest {

    @Mock
    CSVService fileService;

    @Mock
    CSVHelper csvHelper;

    @InjectMocks
    CSVController csvController = new CSVController();
    //	@Mock
//
    @Test
    public void testUploadFile_Success() throws Exception {
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "CSV content".getBytes());

        // Mock behavior
        //when(csvHelper.hasCSVFormat(file)).thenReturn(true);
        doNothing().when(fileService).saveInDB(file);


        // Perform the test
        ResponseEntity<ResponseMessage> response = csvController.uploadFile(file);

        // Verify the result
        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals("Uploaded the file successfully: test.csv", response.getBody().getMessage());

        // Verify interactions with mocks
        verify(fileService, times(1)).saveInDB(file);
        verifyNoMoreInteractions(fileService);
    }

    @Test
    void testUploadFile_InvalidFileFormat() {
        // Prepare test data
        MockMultipartFile file = new MockMultipartFile("file", "test.txt", "text/plain", "Text content".getBytes());

        // Mock behavior
        //when(CSVHelper.hasCSVFormat(file)).thenReturn(false);

        // Perform the test
        ResponseEntity<ResponseMessage> response = csvController.uploadFile(file);

        // Verify the result
        assertEquals(HttpStatus.BAD_REQUEST, response.getStatusCode());
        assertEquals("Please upload a csv file!", response.getBody().getMessage());

        // Verify interactions with mocks
        verifyNoInteractions(fileService);
    }

    @Test
    void testUploadFile_Exception() {
        // Prepare test data
        MockMultipartFile file = new MockMultipartFile("file", "test.csv", "text/csv", "CSV content".getBytes());

        // Mock behavior
        //when(CSVHelper.hasCSVFormat(file)).thenReturn(true);
        doThrow(new RuntimeException("Test exception")).when(fileService).saveInDB(file);

        // Perform the test
        ResponseEntity<ResponseMessage> response = csvController.uploadFile(file);

        // Verify the result
        assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
        assertEquals("Could not upload the file: test.csv!Test exception", response.getBody().getMessage());

        // Verify interactions with mocks
        verify(fileService, times(1)).saveInDB(file);
        verifyNoMoreInteractions(fileService);
    }

    @Test
    public void testGetCombinedReportEndpoint_Success() {
        String combinedReport = "";

        when(fileService.getCombinedReport()).thenReturn(combinedReport);
        ResponseEntity<?> response = csvController.getCombinedReportEndpoint();

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(combinedReport,response.getBody());
    }

    @Test
    public void testGetCombinedReportEndpoint_Exception() {
        String combinedReport = "";

        doThrow(new RuntimeException()).when(fileService).getCombinedReport();
        //when(fileService.getCombinedReport()).thenReturn(combinedReport);
        ResponseEntity<?> response = csvController.getCombinedReportEndpoint();

        //assertEquals(HttpStatus.EXPECTATION_FAILED, response.getStatusCode());
        assertEquals(HttpStatus.INTERNAL_SERVER_ERROR, response.getStatusCode());
        //assertEquals(combinedReport,response.getBody());
    }

    @SpringBootTest
    public static class TestConfig {
    }
}

