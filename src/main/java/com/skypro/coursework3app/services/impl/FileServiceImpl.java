package com.skypro.coursework3app.services.impl;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skypro.coursework3app.services.FileService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.NoSuchFileException;
import java.nio.file.Path;
import java.util.ArrayList;
import java.util.List;

import static java.nio.file.StandardOpenOption.CREATE_NEW;

@Service
@RequiredArgsConstructor

public class FileServiceImpl implements FileService {
    private final ObjectMapper objectMapper;

    @Override
    public <T> Path saveToFile(T content, Path path) throws IOException {
        String json = objectMapper.writeValueAsString(content);
        createNewFile(path);
        return Files.writeString(path, json);
    }

    @Override
    public <T> List<T> uploadFromFile(MultipartFile file, Path path, TypeReference<List<T>> typeReference) throws IOException {
        uploadFile(file, path);
        return readFromFile(path, typeReference);
    }
    @Override
    public <T> List<T> readFromFile(Path path, TypeReference<List<T>> typeReference) {
        try {
            String json = Files.readString(path);
            if (json.isEmpty()) {
                return new ArrayList<>();
            }
            return objectMapper.readValue(json, typeReference);
        } catch (NoSuchFileException e) {
            return new ArrayList<>();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    public void uploadFile(MultipartFile file, Path path) throws IOException {
        Files.createDirectories(path.getParent());
        Files.deleteIfExists(path);
        try (InputStream is = file.getInputStream();
             OutputStream os = Files.newOutputStream(path, CREATE_NEW);
             BufferedInputStream bis = new BufferedInputStream(is, 1024);
             BufferedOutputStream bos = new BufferedOutputStream(os, 1024)) {
            bis.transferTo(bos);
        }
    }

    @Override
    public void createNewFile(Path path) throws IOException {
        try {
            Files.deleteIfExists(path);
            Files.createFile(path);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
