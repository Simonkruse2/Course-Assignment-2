package dto;

import entities.Hobby;
import io.swagger.v3.oas.annotations.media.Schema;

/**
 *
 * @author simon
 */
@Schema(name = "HobbyInfo")
public class HobbyOutDTO {

    private int hobbyID;
    @Schema(required = true, example = "Football")
    private String name;
    @Schema(required = true, example = "A game revolving around a ball")
    private String description;

    public HobbyOutDTO(Hobby hobby) {
        this.name = hobby.getName();
        this.description = hobby.getDescription();
    }

    public HobbyOutDTO() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

}
