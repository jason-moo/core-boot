package com.xzn.uploader.file;

import com.aliyun.oss.OSSClient;
import com.aliyun.oss.model.OSSObject;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.*;

/**
 * @author ruancl@xkeshi.com
 * @date 2017/6/5
 * <p>
 * 根据运维规定需要按一定规则来存放到固定目录下
 * <p>
 * /projectName/文件名
 */
public class OssUploaderImpl implements FileUploader {

    private final String endpoint;

    private final String accessKeyId;

    private final String accessKeySecret;

    private OSSClient client;

    private String bucketName;

    private String projectName;


    public OssUploaderImpl(String endpoint, String accessKeyId, String accessKeySecret, String bucketName, String projectName) {
        this.endpoint = endpoint;
        this.accessKeyId = accessKeyId;
        this.accessKeySecret = accessKeySecret;
        this.client = new OSSClient(endpoint, accessKeyId, accessKeySecret);
        this.bucketName = bucketName;
        this.projectName = projectName;
    }

    public void shutdown() {
        this.client.shutdown();
    }

    private String formatName(String name) {
        String today = LocalDate.now().format(DateTimeFormatter.BASIC_ISO_DATE);
        return String.format("%s/%s/%s.%s", projectName, today, UUID.randomUUID().toString(), name);
    }


    @Override
    public String upload(InputStream in, String ext) {
        String fullName = formatName(ext);
        this.client.putObject(bucketName, fullName, in);
        return fullName;
    }

    @Override
    public List<String> upload(List<FileEntry> pathFiles) {
        List<String> names = new ArrayList<>();
        pathFiles.forEach(o ->
        {
            String fullName = formatName(o.getExt());
            this.client.putObject(bucketName, fullName, o.getInputStream());
            names.add(fullName);
        });
        return names;
    }

    @Override
    public InputStream downLoad(String name) {
        if (!this.client.doesObjectExist(bucketName, name)) {
            return null;
        }
        OSSObject ossObject = this.client.getObject(bucketName, name);
        if (ossObject == null) {
            return null;
        }
        return ossObject.getObjectContent();
    }

    @Override
    public Map<String, InputStream> downLoad(HashSet<String> names) {
        Map<String, InputStream> map = new HashMap<>(names.size());
        names.forEach(o -> {
            if (this.client.doesObjectExist(bucketName, o)) {
                OSSObject ossObject = this.client.getObject(bucketName, o);
                if (ossObject != null) {
                    map.put(o, ossObject.getObjectContent());
                }
            }

        });
        return map;
    }


    public void test() {
        this.client.listBuckets().forEach(o -> System.out.println(o.getName()));
    }


    public static void main(String[] args) throws IOException {
        OssUploaderImpl ossClient = new OssUploaderImpl("oss-cn-hangzhou.aliyuncs.com",
                "LTAI63GulDwTQzTh", "qdy7RSGc6NWWtFnt2ECabZutwIXxSQ", "test-xkeshi-oss", "file-exchange");


        // upload
        //OOSUploaderImpl uploader = new OOSUploaderImpl("http://test-xkeshi-oss.oss-cn-hangzhou.aliyuncs.com","LTAI63GulDwTQzTh","qdy7RSGc6NWWtFnt2ECabZutwIXxSQ");
        InputStream inputStream = new ByteArrayInputStream("hhh".getBytes(StandardCharsets.UTF_8));
        String path = ossClient.upload(inputStream, "txt");

        System.out.println(path);
        //download
        inputStream = ossClient.downLoad(path);
        byte[] bytes = new byte[1024];
        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        int i;
        while ((i = inputStream.read(bytes)) != -1) {
            outputStream.write(bytes, 0, i);
        }
        System.out.println(new String(outputStream.toByteArray()));
    }
}
