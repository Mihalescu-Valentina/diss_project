package com.example.diss.service;

import org.apache.tika.exception.TikaException;
import org.apache.tika.io.TikaInputStream;
import org.apache.tika.metadata.Metadata;
import org.apache.tika.parser.AutoDetectParser;
import org.apache.tika.parser.EmptyParser;
import org.apache.tika.parser.ParseContext;
import org.apache.tika.parser.Parser;
import org.apache.tika.sax.BodyContentHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import org.xml.sax.SAXException;

import java.io.IOException;
import java.io.InputStream;

@Service
public class FileTextExtractor {

    private final Parser parser = new AutoDetectParser();
    private final Logger logger = LoggerFactory.getLogger(FileTextExtractor.class);

    public String extractText(MultipartFile file) {
        try (TikaInputStream in = TikaInputStream.get(file.getInputStream())) {
            var handler = new BodyContentHandler(-1);
            Metadata metadata = new Metadata();
            parser.parse(in, handler, metadata, new ParseContext());
            return handler.toString();
        } catch (IOException | SAXException | TikaException e) {
            logger.error(e.getMessage());
            throw new RuntimeException(e);
        }
    }
}
