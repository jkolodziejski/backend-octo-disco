package pl.put.backendoctodisco.service;

import org.springframework.stereotype.Service;
import pl.put.backendoctodisco.entity.Alias;
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



}
