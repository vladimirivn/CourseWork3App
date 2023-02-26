package com.skypro.coursework3app.services;

import com.fasterxml.jackson.core.type.TypeReference;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public interface FileService {


    <T> Path saveToFile(T content, Path path) throws IOException;

    <T> List<T> uploadFromFile(MultipartFile file, Path path, TypeReference<List<T>> typeReference) throws IOException;

    <T> List<T> readFromFile(Path path, TypeReference<List<T>> typeReference);

    void uploadFile(MultipartFile file, Path path) throws IOException;

    void createNewFile(Path path) throws IOException;
}
