package sesac.mockInvestment.minio;

import io.minio.*;
import io.minio.errors.*;
import io.minio.messages.Item;
import org.springframework.util.StreamUtils;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Iterator;

// https://min.io/docs/minio/linux/developers/java/API.html 도큐먼트 문서
public class MinioTest {

    public static void main(String[] args) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {

        // 1. 접속
        MinioClient minioClient = MinioClient.builder().endpoint("http:infra.shucloud.site:33338")
                .credentials("DUqZH7GmcQ9rll9bYBCY", "4DiuznrM4BhTpQbPWOJZFjnmnBkhMunadbjpmbaS")
                .build();

        // 2. 버킷 유무 판단
        boolean found = minioClient.bucketExists(BucketExistsArgs.builder().bucket("test").build());

        if (found) {
            System.out.println("존재");
        } else {
            System.out.println("미존재");
        }

        // 3. 버킷 내 저장된 Item 목록 조회
        Iterable<Result<Item>> itemList = minioClient.listObjects(ListObjectsArgs.builder().bucket("test").build());

        Iterator<Result<Item>> it = itemList.iterator();

        while (it.hasNext()) {
            Result<Item> next = it.next();
            System.out.println(next.get().size());
        }

        // 4-1. 파일 업로드 (파일)
        minioClient.uploadObject(
                UploadObjectArgs.builder()
                        .bucket("test")
                        .object("test.txt")
                        .filename("C:\\Users\\sHu\\Desktop\\mockInvestment\\src\\main\\java\\sesac\\mockInvestment\\test.txt")
                        .build()
        );

        // 4-2. 파일 업로드 (바이너리)
        InputStream is = new FileInputStream("C:\\Users\\sHu\\Desktop\\mockInvestment\\src\\main\\java\\sesac\\mockInvestment\\test.txt");
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket("test")
                        .object("test2.txt")
                        .stream(is, is.available(), 0)
                        .build()
        );

        // 5-1. 파일 다운로드 (파일)
        minioClient.downloadObject(
                DownloadObjectArgs.builder()
                        .bucket("test")
                        .object("test2.txt")
                        .filename("C:\\Users\\sHu\\Desktop\\mockInvestment\\src\\main\\java\\sesac\\mockInvestment\\test3.txt")
                        .build()
        );

        // 5-2. 파일 다운로드 (바이너리)
        InputStream response = minioClient.getObject(
                GetObjectArgs.builder()
                        .bucket("test")
                        .object("test.txt")
                        .build()
        );
        System.out.println(StreamUtils.copyToString(response, StandardCharsets.UTF_8));

        response.close();

        // 6. 파일 삭제
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket("test")
                        .object("test2.txt")
                        .build()
        );
    }
}