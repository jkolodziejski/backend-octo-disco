package pl.put.backendoctodisco.entity.requests;

import io.swagger.annotations.ApiModelProperty;
import pl.put.backendoctodisco.utils.Language;

import javax.persistence.Column;
import java.util.List;

public class FlashcardRequest {
    @ApiModelProperty(notes = "Language of the word", allowableValues = "en", example = "en", required = true)
    @Column(name = "language", nullable = false)
    public String language;

    @ApiModelProperty(notes = "Word to learn", example = "Poland", required = true)
    @Column(name = "word", nullable = false)
    public String word;

    @ApiModelProperty(notes = "Translation of the word to polish", example = "[Polska]", required = true)
    @Column(name = "translation", nullable = false)
    public List<String> translation;

    @ApiModelProperty(notes = "Indicates if flashcard is globally available for users", example = "0", required = true)
    @Column(name = "is_global", nullable = false)
    public Boolean is_global;
}
