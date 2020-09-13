package ru.maxmorev.restful.eshop.feignclient;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.multipart.MultipartFile;
import ru.maxmorev.restful.eshop.feignclient.domain.FileUploadResponse;

@FeignClient(name = "eshop-minio-api", url = "${external.minioApi.url}")
public interface MinioRestApi {
    @PostMapping(path ="/file/eshop" , consumes = MediaType.MULTIPART_FORM_DATA_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    FileUploadResponse uploadFile(@RequestParam("key") String key,
                                  @RequestParam("file") MultipartFile file);
}
