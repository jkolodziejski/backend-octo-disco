package pl.put.backendoctodisco.entity.responses;


import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Metadata {
    private final Integer size;
    private final Integer totalNumberPages;

    public Metadata(Integer size, Integer totalNumberPages) {
        this.size = size;
        this.totalNumberPages = totalNumberPages;
    }
}
