package pl.put.backendoctodisco.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.FlashcardListContent;
import pl.put.backendoctodisco.entity.FlashcardListInfo;
import pl.put.backendoctodisco.entity.User;
import pl.put.backendoctodisco.repository.FlashcardListContentRepository;
import pl.put.backendoctodisco.repository.FlashcardListInfoRepository;

import java.util.List;
import java.util.Optional;

@Service
public class FlashcardListService {
    private final FlashcardListInfoRepository infoRepository;
    private final FlashcardListContentRepository contentRepository;

    @Autowired
    public FlashcardListService(FlashcardListInfoRepository infoRepository, FlashcardListContentRepository contentRepository) {
        this.infoRepository = infoRepository;
        this.contentRepository = contentRepository;
    }

    public FlashcardListInfo createFlashcardList(FlashcardListInfo flashcardListInfo) {
        return infoRepository.save(flashcardListInfo);
    }

    public int updateFlashcardList(Long id, FlashcardListInfo flashcardListInfo) {
        int changes=0;
        if(flashcardListInfo.getName() != null) {
            changes+=infoRepository.updateListName(id, flashcardListInfo.getName());
        }
        if(flashcardListInfo.getDescription() != null) {
            changes+=infoRepository.updateListDescription(id, flashcardListInfo.getDescription());
        }
        return changes;
    }

    public Optional<FlashcardListInfo> findByName(Long userId, String name) {
        return infoRepository.findByName(userId, name).stream().findFirst();
    }

    public List<FlashcardListInfo> findByName(String name) {
        return infoRepository.findByName(name);
    }

    public FlashcardListContent addToFlashcardList(FlashcardListContent flashcardListContent) {
        return contentRepository.save(flashcardListContent);
    }

    public Optional<FlashcardListContent> findCardInList(FlashcardListContent flashcardListContent) {
        return contentRepository.findCardInList(flashcardListContent.getFlashcardId(), flashcardListContent.getListId()).stream().findFirst();
    }

    public Optional<FlashcardListInfo> findListById(Long id) {
        return infoRepository.findById(id).stream().findFirst();
    }

    public boolean deleteList(Long id){
        return infoRepository.deleteById(id) > 0;
    }

    public boolean deleteFromList(Long id){
        return contentRepository.deleteById(id) > 0;
    }

    public List<FlashcardListInfo> findUsersLists(User user) {
        return infoRepository.findByUserId(user.getId());
    }

    public List<FlashcardListContent> findListContent(Long listId) {
        return contentRepository.findByListId(listId);
    }

    public int countByListId(Long listId){
        return contentRepository.countByListId(listId);
    }

}
