package yms.shopping.portfolio.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yms.shopping.portfolio.domain.BaseTimeEntity;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
@Getter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String uploadFiles;
    private String storeFileName;




    public UploadImage(String uploadFiles, String storeFileName) {
        this.uploadFiles = uploadFiles;
        this.storeFileName = storeFileName;
    }
}
