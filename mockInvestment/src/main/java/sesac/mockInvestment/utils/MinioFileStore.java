package sesac.mockInvestment.utils;

import io.minio.*;
import io.minio.errors.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import sesac.mockInvestment.domain.BoardDto;
import java.io.IOException;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.UUID;

@Component
public class MinioFileStore {

    @Value("spring.minio.endpoint.url")
    private String url;

    private final MinioClient minioClient = MinioClient.builder().endpoint(url)
            .credentials("DUqZH7GmcQ9rll9bYBCY", "4DiuznrM4BhTpQbPWOJZFjnmnBkhMunadbjpmbaS")
            .build();

    public void save(BoardDto boardDto, MultipartFile file) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // Bucket이 존재하는지 유무 체크
        if (!isBucketByUserId("test")) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket("test").build());
        }

        // 서버에 저장될 파일 이름 생성
        String serverFileName = UUID.randomUUID() + boardDto.getOriginalFileName();

        // 파일 저장
        minioClient.putObject(
                PutObjectArgs.builder()
                        .bucket("test")
                        .object(serverFileName)
                        .stream(file.getInputStream(), file.getSize(), 0)
                        .build()
        );
        boardDto.setServerFileName(serverFileName);
    }

    public void delete(BoardDto boardDto) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        // Bucket이 존재하는지 유무 체크
        if (!isBucketByUserId("test")) {
            minioClient.makeBucket(MakeBucketArgs.builder().bucket("test").build());
        }

        // 파일 삭제
        minioClient.removeObject(
                RemoveObjectArgs.builder()
                        .bucket("test")
                        .object(boardDto.getServerFileName())
                        .build()
        );
    }

    public boolean isBucketByUserId(String userId) throws ServerException, InsufficientDataException, ErrorResponseException, IOException, NoSuchAlgorithmException, InvalidKeyException, InvalidResponseException, XmlParserException, InternalException {
        return minioClient.bucketExists(BucketExistsArgs.builder().bucket("userId").build());
    }
}
