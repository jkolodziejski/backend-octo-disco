package pl.put.backendoctodisco.entity.responses;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Metadata {
    private final Long size;
    private final Integer totalNumberPages;

    public Metadata(Long size, Integer totalNumberPages) {
        this.size = size;
        this.totalNumberPages = totalNumberPages;
    }
}
