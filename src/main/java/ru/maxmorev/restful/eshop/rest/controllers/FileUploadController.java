package ru.maxmorev.restful.eshop.rest.controllers;


import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;
import ru.maxmorev.restful.eshop.config.FileUploadConfiguration;
import ru.maxmorev.restful.eshop.feignclient.MinioRestApi;
import ru.maxmorev.restful.eshop.feignclient.domain.FileUploadResponse;
import ru.maxmorev.restful.eshop.rest.Constants;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Slf4j
@RestController
@RequiredArgsConstructor
public class FileUploadController {
    private final MinioRestApi minioRestApi;
    private final FileUploadConfiguration fileUploadConfiguration;

    @SneakyThrows
    @PostMapping(value = Constants.REST_PRIVATE_URI + "file", produces = MediaType.APPLICATION_JSON_VALUE)
    public FileUploadResponse uploadData(@RequestParam("file") MultipartFile file) {
        if (file == null) {
            throw new IllegalArgumentException("You must select the a file for uploading");
        }
        String tempFileName = "/tmp/" + file.getOriginalFilename();
        FileOutputStream fo = new FileOutputStream(tempFileName);
        fo.write(file.getBytes());
        fo.close();
        File fileUpload = new File(tempFileName);
        return minioRestApi.uploadFile(fileUploadConfiguration.getAccessKey(), fileUpload);
    }

}
