package pl.put.backendoctodisco.service;

import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.Alias;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.entity.responses.FlashcardResponse;
import pl.put.backendoctodisco.exceptions.AliasAlreadyExistsException;
import pl.put.backendoctodisco.repository.AliasRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AliasService {

    private final AliasRepository aliasRepository;


    public AliasService(AliasRepository aliasRepository) {
        this.aliasRepository = aliasRepository;
    }


    public Alias createAlias(Alias alias){
        return aliasRepository.save(alias);
    }

    public List<Alias> findByWordId(Long word_id){
        return aliasRepository.findByWordId(word_id);
    }

    public List<String> findAliasbyWordId(Long word_id){
        List<Alias> foundedAliases = aliasRepository.findAliasesByWordId(word_id);
        List<String> alias = new ArrayList<>();
        for (Alias alia : foundedAliases){
            alias.add(alia.getAlias());
        }
        return alias;
    }

    public FlashcardResponse getFlashcardWithAlias(Flashcard flashcard){
        List<String> foundedAlias = findAliasbyWordId(flashcard.getId());
        return new FlashcardResponse(flashcard,foundedAlias);
    }

    public void checkAndcreateAlias(Long word_id, String word) throws AliasAlreadyExistsException {
        List <Alias>  foundAlias = findByWordId(word_id);
        List <Alias> filteredAlias = foundAlias
                .stream().filter(alias ->
                        alias.getAlias().equals(word))
                .toList();
        if(!filteredAlias.isEmpty()){
            throw new AliasAlreadyExistsException();
        }
        createAlias(new Alias(word,word_id));
    }

}
