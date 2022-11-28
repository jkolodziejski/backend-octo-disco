package pl.put.backendoctodisco.entity.responses;


import io.swagger.annotations.ApiModelProperty;
import lombok.Getter;
import lombok.ToString;
import pl.put.backendoctodisco.entity.QuizQuestion;

import javax.persistence.Column;
import java.util.List;

@ToString
@Getter
public class Quiz {
    @ApiModelProperty(notes = "List of questions", example="[\n" +
            "        {\n" +
            "            \"answers\": [\n" +
            "                {\n" +
            "                    \"word\": \"approach\",\n" +
            "                    \"translation\": \" podchodzić\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"word\": \"carefully\",\n" +
            "                    \"translation\": \"ostrożnie\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"word\": \"chemical\",\n" +
            "                    \"translation\": \"chemikalia\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"word\": \"create\",\n" +
            "                    \"translation\": \" tworzyć\"\n" +
            "                },\n" +
            "                {\n" +
            "                    \"word\": \"evil\",\n" +
            "                    \"translation\": \" nikczemny\"\n" +
            "                }\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"answer\": {\n" +
            "                \"word\": \"experiment\",\n" +
            "                \"translation\": \"eksperyment\"\n" +
            "            },\n" +
            "            \"false_options\": [\n" +
            "                \" starannie\",\n" +
            "                \"zły\",\n" +
            "                \" podchodzić\"\n" +
            "            ]\n" +
            "        },\n" +
            "        {\n" +
            "            \"flashcard\": {\n" +
            "                \"id\": 31,\n" +
            "                \"language\": \"en\",\n" +
            "                \"word\": \"noise\",\n" +
            "                \"isGlobal\": true,\n" +
            "                \"userId\": 36,\n" +
            "                \"alias\": [\n" +
            "                    \"hałas\",\n" +
            "                    \" szum \"\n" +
            "                ]\n" +
            "            }\n" +
            "        }\n" +
            "]", required = true)
    @Column(name = "questions", nullable = false)
    private List<QuizQuestion> questions;

    public Quiz(List<QuizQuestion> questions){
        this.questions = questions;
    }
}
