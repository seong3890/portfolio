package yms.shopping.portfolio.controller.dto.item;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.util.List;

@Data
@NoArgsConstructor
public class ItemPatchDto {
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

    public ItemPatchDto(String name, int price, int quantity, String writer, String isbn) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.writer = writer;
        this.isbn = isbn;
    }

    public ItemPatchDto(String name, int price, int quantity, String writer, String isbn, List<MultipartFile> uploadImageDtos) {
        this.name = name;
        this.price = price;
        this.quantity = quantity;
        this.writer = writer;
        this.isbn = isbn;
        this.uploadImageDtos = uploadImageDtos;
    }
}
