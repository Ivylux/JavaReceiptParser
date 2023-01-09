package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.ExecutionException;
import java.util.regex.*;

class Parser {
    public void receiptOut(String text) throws Exception {
        Pattern receiptStart = Pattern.compile("Salgskvittering \\d{5} \\d{2}.\\d{2}.\\d{4} \\d{2}:\\d{2}", Pattern.CASE_INSENSITIVE);
        Matcher matcher = receiptStart.matcher(text);

        if (!matcher.find()) {
            throw new Exception("Unreadable receipt");
        }
        int start = matcher.end();

        String items = text.substring(start);
        String[] lines = items.split("\n");

        for (String line : lines) {
            System.out.println(line);

            if (line.contains("Totalt")) {
                break;
            }
        }
    }
}

public class Main {
    public static void main(String[] args) throws Exception {
        File file = new File("pdf/Coop-20230105.pdf");
        PDDocument document = PDDocument.load(file);
        PDFTextStripper pdfStripper = new PDFTextStripper();
        String text = pdfStripper.getText(document);
        document.close();

        Parser parser = new Parser();
        parser.receiptOut(text);
    }
}