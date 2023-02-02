package yms.shopping.portfolio.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import yms.shopping.portfolio.controller.dto.item.ItemBookPostDto;
import yms.shopping.portfolio.controller.dto.item.ItemMainDto;
import yms.shopping.portfolio.controller.dto.item.ItemPatchDto;
import yms.shopping.portfolio.domain.Member;
import yms.shopping.portfolio.domain.item.Book;
import yms.shopping.portfolio.domain.item.Items;
import yms.shopping.portfolio.domain.item.UploadImage;
import yms.shopping.portfolio.repository.FileStore;
import yms.shopping.portfolio.repository.ItemJpaRepository;
import yms.shopping.portfolio.repository.MemberJpaRepository;
import yms.shopping.portfolio.repository.UploadImageJpaRepository;

import java.io.IOException;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemJpaRepository itemJpaRepository;
    private final UploadImageJpaRepository uploadImageJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final FileStore fileStore;

    public Page<ItemMainDto> findItemJoinUpload(Pageable pageable) {
        Page<Items> items = itemJpaRepository.findItemsAndUpload(pageable);
        Page<ItemMainDto> itemMainDtos = items.map(items1 -> new ItemMainDto(items1));


        /*for (Items item : items) {
            Items items1 = itemJpaRepository.findById(item.getId()).orElseThrow();
            UploadImage uploadImage = uploadImageJpaRepository.findById(items1.getId()).orElseThrow();
            itemMainDtos.add(new ItemMainDto(items1));
        }*/
        for (ItemMainDto itemMainDto : itemMainDtos) {
            log.info("itemMainDto {}", itemMainDto.getUploadDtos());
        }
        return itemMainDtos;

    }


    /**
     * 리팩토링 할 예정
     */
    @Transactional
    public void create(Long id, ItemBookPostDto itemBookPostDto) throws IOException {
        Book book = new Book();
        Member member = memberJpaRepository.findById(id).orElseThrow(() -> new RuntimeException("회원이 존재하지 않습니다."));
        List<UploadImage> uploadImages = fileStore.saveImages(itemBookPostDto.getUploadImageDtos());
        for (UploadImage uploadImage : uploadImages) {
            uploadImage.setItems(book);
            book.getUploadImage().add(uploadImage);
        }
        book.setMember(member);
        uploadImageJpaRepository.saveAll(uploadImages);

//        for (UploadImage uploadImage : uploadImages) {
//            book.up;
//        }

        book.setName(itemBookPostDto.getName());
        book.setWriter(itemBookPostDto.getWriter());
        book.setPrice(itemBookPostDto.getPrice());
        book.setQuantity(itemBookPostDto.getQuantity());
        book.setIsbn(itemBookPostDto.getIsbn());

        itemJpaRepository.save(book);


    }


    public ItemMainDto findItem(Long itemId) {
        Items itemAndUpload = itemJpaRepository.findItemAndUpload(itemId).orElseThrow();
        ItemMainDto itemMainDto = new ItemMainDto(itemAndUpload);
        return itemMainDto;
    }

    public ItemPatchDto findPetchItem(Long itemId) {
        Book items = itemJpaRepository.findBook(itemId).orElseThrow();
        log.info("bookId={}",items.getId());
//        UploadImage uploadImage = uploadImageJpaRepository.findById(items.getId()).orElseThrow();
        ItemPatchDto itemPatchDto = new ItemPatchDto(items.getName(), items.getPrice(), items.getQuantity(), items.getWriter(), items.getIsbn());
        return itemPatchDto;
    }

    @Transactional
    public void PetchItem(ItemPatchDto itemPatchDto,Long itemId) {
        Book book = itemJpaRepository.findBook(itemId).orElseThrow();
        book.setName(itemPatchDto.getName());
        book.setPrice(itemPatchDto.getPrice());
        book.setQuantity(itemPatchDto.getQuantity());
        book.setWriter(itemPatchDto.getWriter());
        book.setIsbn(itemPatchDto.getIsbn());

    }

    public List<Items> findItems() {
        return itemJpaRepository.findAll();
    }
}
