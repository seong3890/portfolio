package yms.shopping.portfolio.controller.dto.item;

import lombok.Data;
import org.springframework.web.multipart.MultipartFile;
import yms.shopping.portfolio.domain.item.Items;
import yms.shopping.portfolio.domain.item.UploadImage;

import java.util.List;
import java.util.stream.Collectors;

@Data
public class ItemMainDto {
    private Long id;
    private String name;
    private UploadDto uploadDto;
    private List<UploadDto> uploadDtos;

//    private String storeFileName;

    public ItemMainDto(Items items) {
        this.id = items.getId();
        this.name = items.getName();
        this.uploadDtos = items.getUploadImage().stream().map(u-> new UploadDto(u)).collect(Collectors.toList());
        this.uploadDto = uploadDtos.get(0);
    }

    @Data
    static class UploadDto {
        String storeFileName;

        public UploadDto(UploadImage uploadImage) {
            this.storeFileName = uploadImage.getStoreFileName();
        }
    }

}


