package com.skypro.coursework3app.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.skypro.coursework3app.exception.ValidationException;
import com.skypro.coursework3app.model.socks.Socks;
import com.skypro.coursework3app.model.socks.SocksBatch;
import com.skypro.coursework3app.model.socks.SocksColor;
import com.skypro.coursework3app.model.socks.SocksSize;
import com.skypro.coursework3app.repository.SocksRepository;
import com.skypro.coursework3app.services.FileService;
import com.skypro.coursework3app.services.SavingOperationService;
import com.skypro.coursework3app.services.SocksService;

import com.skypro.coursework3app.services.ValidationService;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.nio.file.Path;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class SocksServiceImpl implements SocksService {
    private final SocksRepository socksRepository;
    private final ValidationService validationService;
    private final FileService filesService;
    private final SavingOperationService operationService;

    @Value("${path.to.data.file}")
    public String dataFilePath;
    @Value("${name.of.data.file}")
    public String dataFileName;

    @Override
    public void arrivalOfGoods(SocksBatch socksBatch) {
        checkSocksButch(socksBatch);
        operationService.acceptance(socksBatch);
        socksRepository.save(socksBatch);
    }

    @Override
    public int releaseOfGoods(SocksBatch socksBatch) {
        checkSocksButch(socksBatch);
        operationService.issuance(socksBatch);
        return socksRepository.remove(socksBatch);
    }

    @Override
    public int writeOffDefectiveOfGoods(SocksBatch socksBatch) {
        checkSocksButch(socksBatch);
        operationService.writeOff(socksBatch);
        return socksRepository.remove(socksBatch);
    }

    @Override
    public int getTotalOfGoods(SocksColor socksColor, SocksSize size, int cottonMin, int cottonMax) {

        if (!validationService.validate(socksColor, size, cottonMin, cottonMax)) {
            throw new ValidationException();
        }
        Map<Socks, Integer> socksMap = socksRepository.getAll();

        int countSocks = 0;

        for (Map.Entry<Socks, Integer> entry : socksMap.entrySet()) {
            Socks socks = entry.getKey();

            if (socks.getColor().equals(socksColor)
                    && socks.getSize().equals(size)
                    && socks.getCottonPart() >= cottonMin
                    && socks.getCottonPart() <= cottonMax) {
                countSocks += entry.getValue();
            }
        }
        return countSocks;
    }

    @Override
    public File exportFile() throws IOException {
        return filesService.saveToFile(socksRepository.getList(), Path.of(dataFilePath, dataFileName)).toFile();
    }

    @Override
    public void uploadFile(MultipartFile file) throws IOException {
        List<SocksBatch> socksBatches = filesService.uploadFromFile(file, Path.of(dataFilePath, dataFileName),
                new TypeReference<List<SocksBatch>>() {
                });
        socksRepository.replace(socksBatches);
    }

    private void checkSocksButch(SocksBatch socksBatch) {
        if (!validationService.validate(socksBatch)) {
            throw new ValidationException();
        }
    }
}
