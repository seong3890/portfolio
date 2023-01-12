package yms.shopping.portfolio.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import yms.shopping.portfolio.controller.dto.item.ItemBookDto;
import yms.shopping.portfolio.controller.dto.item.UploadImageDto;
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

@Slf4j
@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class ItemService {
    private final ItemJpaRepository itemJpaRepository;
    private final UploadImageJpaRepository uploadImageJpaRepository;
    private final MemberJpaRepository memberJpaRepository;
    private final FileStore fileStore;

    public List<Items> findAll() {
        return itemJpaRepository.findAll();
    }

    /**
     * 리팩토링 할 예정
     */
    @Transactional
    public void create(Long id, ItemBookDto itemBookDto) throws IOException {
        Book book = new Book();
        Member member = memberJpaRepository.findById(id).orElseThrow(()->new RuntimeException("회원이 존재하지 않습니다."));
        List<UploadImage> uploadImages = fileStore.saveImages(itemBookDto.getUploadImageDtos());
        uploadImageJpaRepository.saveAll(uploadImages);
        book.setMember(member);
        for (UploadImage uploadImage : uploadImages) {
            book.setUploadImage(uploadImage);
        }

        book.setName(itemBookDto.getName());
        book.setWriter(itemBookDto.getWriter());
        book.setPrice(itemBookDto.getPrice());
        book.setQuantity(itemBookDto.getQuantity());
        book.setIsbn(itemBookDto.getIsbn());

        itemJpaRepository.save(book);




    }
}
