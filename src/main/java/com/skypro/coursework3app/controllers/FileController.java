package com.skypro.coursework3app.controllers;


import com.skypro.coursework3app.services.SavingOperationService;
import com.skypro.coursework3app.services.SocksService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

@RestController
@RequestMapping("/api/socks/files")
@Tag(name = "API для работы с файлами", description = "Импорт/экспорт файла товаров")
@RequiredArgsConstructor

public class FileController {
    private final SocksService socksService;
    private final SavingOperationService savingOperationService;

    @GetMapping("/download")
    @Operation(summary = "Выгрузка файла товаров")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная выгрузка файла с товаром"),
            @ApiResponse(responseCode = "400", description = "Ошибка выгрузки файла с товаром")
    })
    public ResponseEntity<InputStreamResource> downloadDataFile() {
        try {
            File file = socksService.ExportFile();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = " + file.getName())
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }

    @PostMapping(value = "/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка файла товаров")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Файл с товарами загружен"),
            @ApiResponse(responseCode = "400", description = "Ошибка загрузки фала товаров"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка на сервере")
    })
    public ResponseEntity<String> uploadDataFile(@RequestParam MultipartFile file) {
        try {
            socksService.uploadFile(file);
            return ResponseEntity.ok("Файл успешно загружен");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Ошибка при загрузке файла.");
        }
    }
    @GetMapping("/operation/download")
    @Operation(summary = "Выгрузка json-файла операций с товаром")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Успешная выгрузка json-файла операций с товаром"),
            @ApiResponse(responseCode = "400", description = "Ошибка выгрузки json-файла операций с товаром")
    })
    public ResponseEntity<InputStreamResource> downloadSavingOperationJsonFile() {
        try {
            File file = savingOperationService.ExportFile();
            InputStreamResource resource = new InputStreamResource(new FileInputStream(file));
            return ResponseEntity.ok()
                    .contentType(MediaType.APPLICATION_JSON)
                    .contentLength(file.length())
                    .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename = " + file.getName())
                    .body(resource);
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.noContent().build();
        }
    }
    @PostMapping(value = "/operation/upload", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    @Operation(summary = "Загрузка json-файла операций с товаром")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Json-файл операций с товаром загружен"),
            @ApiResponse(responseCode = "400", description = "Ошибка загрузки Json-файла операций с товаром"),
            @ApiResponse(responseCode = "500", description = "Внутренняя ошибка на сервере")
    })
    public ResponseEntity<String> uploadSavingOperationJsonFile(@RequestParam MultipartFile file) {
        try {
            savingOperationService.uploadFile(file);
            return ResponseEntity.ok("Файл успешно загружен");
        } catch (IOException e) {
            e.printStackTrace();
            return ResponseEntity.badRequest().body("Ошибка при загрузке файла.");
        }
    }

}
