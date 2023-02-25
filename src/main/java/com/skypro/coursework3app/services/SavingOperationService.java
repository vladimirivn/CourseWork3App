package com.skypro.coursework3app.services;

import com.skypro.coursework3app.model.socks.SocksBatch;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface SavingOperationService {

    void acceptance(SocksBatch socksBatch);
    void issuance(SocksBatch socksBatch);
    void writeOff(SocksBatch socksBatch);
    File ExportFile() throws IOException;
    void uploadFile(MultipartFile file) throws IOException;
}
