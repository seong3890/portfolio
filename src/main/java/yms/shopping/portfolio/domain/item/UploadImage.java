package yms.shopping.portfolio.domain.item;

import lombok.AccessLevel;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import yms.shopping.portfolio.domain.BaseTimeEntity;

import javax.persistence.*;
import java.util.List;

@Entity
@Getter
@Setter
@NoArgsConstructor(access = AccessLevel.PROTECTED)
public class UploadImage extends BaseTimeEntity {

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;

    private String uploadFiles;
    private String storeFileName;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "items_id")
    private Items items;




    public UploadImage(String uploadFiles, String storeFileName) {
        this.uploadFiles = uploadFiles;
        this.storeFileName = storeFileName;
    }
}
