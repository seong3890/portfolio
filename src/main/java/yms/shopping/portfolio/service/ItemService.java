package yms.shopping.portfolio.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import yms.shopping.portfolio.domain.item.Items;
import yms.shopping.portfolio.repository.ItemJpaRepository;

import java.util.List;

@Service
@RequiredArgsConstructor
@Slf4j
public class ItemService {
    private final ItemJpaRepository itemJpaRepository;

    public List<Items> findAll() {
        return itemJpaRepository.findAll();
    }
}
