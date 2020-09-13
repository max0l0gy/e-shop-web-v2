package ru.maxmorev.restful.eshop.feignclient;

import feign.Headers;
import feign.Param;
import feign.RequestLine;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import ru.maxmorev.restful.eshop.feignclient.domain.FileUploadResponse;

import java.io.File;

@FeignClient(name = "eshop-minio-api", url = "${external.minioApi.url}")
public interface MinioRestApi {
    @RequestLine("POST /file/eshop")
    @Headers("Content-Type: multipart/form-data")
    FileUploadResponse uploadFile(@Param("key") String key,
                                  @Param("file") File file);
}
