package pl.put.backendoctodisco.service;

import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.Alias;
import pl.put.backendoctodisco.entity.Flashcard;
import pl.put.backendoctodisco.entity.responses.FlashcardResponse;
import pl.put.backendoctodisco.exceptions.FlashcardAlreadyExistsException;
import pl.put.backendoctodisco.repository.AliasRepository;

import java.util.ArrayList;
import java.util.List;

@Service
public class AliasService {

    private final AliasRepository repository;


    public AliasService(AliasRepository repository) {
        this.repository = repository;
    }


    public Alias createAlias(Alias alias){
        return repository.save(alias);
    }

    public List<Alias> findByWordId(Long word_id){
        return repository.findByWordId(word_id);
    }

    public List<String> findAliasbyWordId(Long word_id){
        List<Alias> foundedAliases = repository.findAliasesByWordId(word_id);
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

    public void checkAndcreateAlias(Long word_id, List<String> aliases) throws FlashcardAlreadyExistsException {
        List <Alias>  foundAlias = findByWordId(word_id);
        for (String aliasReq : aliases) {
            List<Alias> filteredAlias = foundAlias
                    .stream().filter(alias ->
                            alias.getAlias().equals(aliasReq))
                    .toList();
            if (!filteredAlias.isEmpty()) {
                throw new FlashcardAlreadyExistsException();
            }
            createAlias(new Alias(aliasReq,word_id));
        }

    }

}
