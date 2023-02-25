package com.skypro.coursework3app.services;

import com.skypro.coursework3app.model.socks.SocksBatch;
import com.skypro.coursework3app.model.socks.SocksColor;
import com.skypro.coursework3app.model.socks.SocksSize;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;

public interface SocksService {

    void arrivalOfGoods(SocksBatch socksBatch);  // Приход
    int releaseOfGoods(SocksBatch socksBatch); // Отпуск
    int writeOffDefectiveOfGoods(SocksBatch socksBatch); // Дефект
    int getTotalOfGoods(SocksColor socksColor, SocksSize size, int cottonMin, int cottonMax); // Всего

    File ExportFile() throws IOException;
    void uploadFile(MultipartFile file) throws IOException;



}
