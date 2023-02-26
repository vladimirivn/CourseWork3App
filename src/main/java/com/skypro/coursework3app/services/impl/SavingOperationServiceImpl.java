package com.skypro.coursework3app.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.skypro.coursework3app.model.operation.OperationType;
import com.skypro.coursework3app.model.operation.SavingOperation;
import com.skypro.coursework3app.model.socks.SocksBatch;
import com.skypro.coursework3app.services.FileService;
import com.skypro.coursework3app.services.SavingOperationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

@Service
@RequiredArgsConstructor
public class SavingOperationServiceImpl implements SavingOperationService {

    private List<SavingOperation> savingOperationList = new ArrayList<>();
    private final FileService filesService;
    @Value("${path.to.data.file}")
    public String dataFilePath;
    @Value("${name.of.data.operation.file}")
    public String dataFileName;


    @Override
    public void acceptance(SocksBatch socksBatch) {
        savingOperationList.add(new SavingOperation(OperationType.ACCEPTANCE, socksBatch));
    }

    @Override
    public void issuance(SocksBatch socksBatch) {
        savingOperationList.add(new SavingOperation(OperationType.ISSUANCE, socksBatch));
    }

    @Override
    public void writeOff(SocksBatch socksBatch) {
        savingOperationList.add(new SavingOperation(OperationType.WRITE_OFF, socksBatch));
    }

    @Override
    public File exportFile() throws IOException {
        return filesService.saveToFile(savingOperationList, Path.of(dataFilePath, dataFileName)).toFile();
    }


    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        savingOperationList = filesService.uploadFromFile(file, Path.of(dataFilePath, dataFileName),
                new TypeReference<List<SavingOperation>>() {
                });
    }

}
