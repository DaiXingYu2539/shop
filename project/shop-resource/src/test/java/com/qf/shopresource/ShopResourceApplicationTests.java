package com.qf.shopresource;

import com.github.tobato.fastdfs.domain.StorePath;
import com.github.tobato.fastdfs.service.FastFileStorageClient;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;

@SpringBootTest
class ShopResourceApplicationTests  {

    @Autowired
    private FastFileStorageClient client;

    @Test
    void contextLoads() throws FileNotFoundException {
        File file = new File("C:/Users/11446/Desktop/Screenshot_20210425-161941.jpg");
        FileInputStream fis = new FileInputStream(file);
        StorePath jpg = client.uploadImageAndCrtThumbImage(fis, file.length(), "jpg", null);
        System.out.println(jpg.getFullPath());
    }

}
