package com.zyq.parttime.util;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.OutputStream;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileItemFactory;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;

public class FileUtils {

    public static MultipartFile fileToMultipartFile(File file) {
        FileItem fileItem = createFileItem(file);
        MultipartFile multipartFile = new CommonsMultipartFile(fileItem);
        return multipartFile;
    }

    //创建一个contentType是text/plain的文件
    private static FileItem createFileItem(File file) {
        FileItemFactory factory = new DiskFileItemFactory(16, null);
        FileItem item = factory.createItem("textField", "text/plain", true, file.getName());
        int bytesRead = 0;
        byte[] buffer = new byte[8192];//存放文件比特流
        try {
            FileInputStream fis = new FileInputStream(file);
            OutputStream os = item.getOutputStream();
            while ((bytesRead = fis.read(buffer, 0, 8192)) != -1) {
                os.write(buffer, 0, bytesRead);
            }
            os.close();//关闭输出流
            fis.close();//关闭文件输入流
        } catch (IOException e) {
            e.printStackTrace();
        }
        return item;
    }
}
