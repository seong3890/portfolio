package yms.shopping.portfolio.repository;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import org.springframework.web.multipart.MultipartFile;
import yms.shopping.portfolio.domain.item.UploadImage;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Component
public class FileStore {

    @Value("${file.dir}")
    private String fileDir;

    public String getFullPath(String fileName) {
        return fileDir + fileName;
    }

    public List<UploadImage> saveImages(List<MultipartFile> multipartFiles) throws IOException {
        List<UploadImage> storeFileResult = new ArrayList<>();
        for (MultipartFile multipartFile : multipartFiles) {
            if (!multipartFile.isEmpty()) {
                storeFileResult.add(saveImage(multipartFile));
            }
        }
        return storeFileResult;
    }

    public UploadImage saveImage(MultipartFile multipartFile) throws IOException {

        if (multipartFile.isEmpty()) {
            return null;
        }
        String originalFilename = multipartFile.getOriginalFilename();
        //uuid.확장자명 생성
        String storeFileName = createStoreFileName(originalFilename);
        //파일 저장
        multipartFile.transferTo(new File(getFullPath(storeFileName)));
        return new UploadImage(originalFilename, storeFileName);
    }

    private static String createStoreFileName(String originalFilename) {

        String uuid = UUID.randomUUID().toString();
        String ext = extracted(originalFilename);
        return uuid + "." + ext;
    }

    private static String extracted(String originalFilename) {
        int pos = originalFilename.lastIndexOf(".");
        return originalFilename.substring(pos + 1);
    }
}
