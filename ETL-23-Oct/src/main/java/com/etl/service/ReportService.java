package com.etl.service;

import com.etl.model.ErrorEntity;
import com.etl.model.Users;
import com.etl.service.CSVService;
import com.etl.repository.ErrorRepository;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Element;
import com.lowagie.text.Paragraph;
import com.lowagie.text.pdf.PdfPTable;
import com.lowagie.text.pdf.PdfWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Service
public class ReportService {
    @Autowired
    private CSVService csvService;

    @Autowired
    private ErrorRepository errorEntityRepository;

    public ByteArrayInputStream generateErrorReport() {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();

        LocalDateTime currentTime = LocalDateTime.now(); // or use an appropriate timestamp
//        ErrorEntity errorEntity = new ErrorEntity(errorId, entityName, currentTime, errorMessage);


        try {
            PdfWriter.getInstance(document, out);
            document.open();

            // Add a title
            Paragraph title = new Paragraph("Batch Report \n\n");
            title.setAlignment(Element.ALIGN_CENTER);
            document.add(title);

            // Add the additional data outside the table
            String additionalData = "Data Count:\n" + csvService.getCombinedReport() + "\n\n";

            Paragraph additionalDataParagraph = new Paragraph(additionalData);
            document.add(additionalDataParagraph);

            // Create a table for error data
            PdfPTable table = new PdfPTable(4); // Four columns: Error ID, Error Message, Entity Name, Timestamp
            table.setWidthPercentage(100);
            Paragraph title1 = new Paragraph("Error Table \n\n");
            title1.setAlignment(Element.ALIGN_CENTER);
            document.add(title1);

            //Try fetching User
            Users user = new Users();
            System.out.println("Username Inside ReportService : "+user.getUsername());

            // Add table headers
            table.addCell("ID");
            table.addCell("Error Description");
            table.addCell("Entity Name");
            table.addCell("Date");


            // Define a width for the "Error ID" column
            float errorIdColumnWidth = 0.4f; // Adjust the width as needed

            // Set the width of the "Error ID" column
            table.setWidths(new float[]{0.4f, 2, 1, 1});


            // Fetch error data from the repository
            List<ErrorEntity> errorEntities = errorEntityRepository.findAll();

            // Create a DateTimeFormatter for formatting LocalDateTime
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm");
            // Add error data to the table
            for (ErrorEntity errorEntity : errorEntities) {
                table.addCell(errorEntity.geterrorId().toString());
                table.addCell(errorEntity.getErrorMessage());
                table.addCell(errorEntity.getEntityName());
                table.addCell(errorEntity.getTimestamp().format(formatter));
            }

            // Add the table to the document
            document.add(table);
            document.close();
        } catch (DocumentException e) {
            e.printStackTrace();
        }
        return new ByteArrayInputStream(out.toByteArray());
    }
}