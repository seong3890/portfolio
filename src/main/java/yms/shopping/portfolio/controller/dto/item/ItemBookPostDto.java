package yms.shopping.portfolio.controller.dto.item;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ItemBookPostDto {

    @NotBlank
    private String name;

    @NotNull
    private int price;
    @NotNull
    private int quantity;
    @NotBlank
    private String writer;
    @NotBlank
    private String isbn;

    private List<MultipartFile> uploadImageDtos;
}
