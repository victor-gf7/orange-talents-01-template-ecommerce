package br.com.zup.freemarket.uploadimage;

import com.amazonaws.SdkClientException;
import com.amazonaws.auth.AWSStaticCredentialsProvider;
import com.amazonaws.auth.BasicAWSCredentials;
import com.amazonaws.services.s3.AmazonS3;
import com.amazonaws.services.s3.AmazonS3ClientBuilder;
import com.amazonaws.services.s3.model.ObjectMetadata;
import com.amazonaws.services.s3.model.PutObjectRequest;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.util.*;
import java.util.stream.Collectors;

@Service
public class UploadImageService {

    @Value("${app.aws.s3.bucketname}")
    private String bucketName;

    @Value("${app.aws.iam.accesskey}")
    private String accessKey;

    @Value("${app.aws.iam.secretkey}")
    private String secretKey;

    @Value("${app.aws.s3.endpointurl}")
    private String endpointUrl;

    @Value("${app.aws.s3.clientregion}")
    private String clientRegion;

    public Set<String> uploadImage(List<MultipartFile> files) throws SdkClientException {

        return files.stream().map(file -> {
            byte[] bytes = new byte[0];
            try {
                bytes = file.getBytes();
            } catch (IOException e) {
                e.printStackTrace();
            }
            String fileObjKeyName = file.getOriginalFilename();

            BasicAWSCredentials awsCredentials = new BasicAWSCredentials(accessKey, secretKey);
            AmazonS3 s3Client = AmazonS3ClientBuilder.standard().withRegion(clientRegion).withCredentials(new AWSStaticCredentialsProvider(awsCredentials)).build();

            ObjectMetadata metadata = new ObjectMetadata();
            metadata.setContentType(file.getContentType());
            metadata.setContentLength(file.getSize());

            InputStream inputStream = new ByteArrayInputStream(bytes);
            PutObjectRequest request = new PutObjectRequest(bucketName, fileObjKeyName, inputStream, metadata);

            s3Client.putObject(request);

            return "https://s3."+clientRegion+".amazonaws.com/"+bucketName+"/"+fileObjKeyName;
        }).collect(Collectors.toSet());

    }


    public Set<String> uploadImageFake(List<MultipartFile> files) {

        return files.stream().map(file -> "http://s3.amazonaws.com/"+ file.getOriginalFilename().replace(" ", "_")).collect(Collectors.toSet());
    }
}
