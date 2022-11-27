package pl.put.backendoctodisco.entity.responses;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;

@Getter
@ToString
public class Metadata {

    @ApiModelProperty(notes = "Total number of element from this request", example = "1", required = true)
    private final Long size;

    @ApiModelProperty(notes = "Total number of pages from this request", example = "1", required = true)
    private final Integer totalNumberPages;

    public Metadata(Long size, Integer totalNumberPages) {
        this.size = size;
        this.totalNumberPages = totalNumberPages;
    }
}
