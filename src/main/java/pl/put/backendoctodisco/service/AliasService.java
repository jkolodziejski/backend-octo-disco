package pl.put.backendoctodisco.service;

import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.Alias;
import pl.put.backendoctodisco.exceptions.FlashcardAlreadyExistsException;
import pl.put.backendoctodisco.repository.AliasRepository;

import java.util.List;

@Service
public class AliasService {

    private final AliasRepository repository;

    public AliasService(AliasRepository repository) {
        this.repository = repository;
    }

    public Alias createAlias(Alias alias) {
        return repository.save(alias);
    }

    public List<Alias> findByWordId(Long word_id) {
        return repository.findByWordId(word_id);
    }

    public void checkAlias(Long word_id, List<String> aliases) throws FlashcardAlreadyExistsException {
        List<Alias> foundAlias = findByWordId(word_id);
        for (String aliasReq : aliases) {
            List<Alias> filteredAlias = foundAlias
                    .stream().filter(alias ->
                            alias.getAlias().equals(aliasReq))
                    .toList();
            if (!filteredAlias.isEmpty()) {
                throw new FlashcardAlreadyExistsException();
            }
        }
    }

}
