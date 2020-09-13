package ru.maxmorev.restful.eshop.feignclient;

import feign.Param;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestPart;
import org.springframework.web.multipart.MultipartFile;
import ru.maxmorev.restful.eshop.feignclient.domain.FileUploadResponse;

@FeignClient(name = "eshop-minio-api", url = "${external.minioApi.url}")
public interface MinioRestApi {
    @PostMapping(path ="/file/eshop" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    FileUploadResponse uploadFile(@Param("key") String key,
                                  @RequestPart("file") MultipartFile file);
}
