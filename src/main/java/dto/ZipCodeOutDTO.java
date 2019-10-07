
package dto;
import io.swagger.v3.oas.annotations.media.Schema;

@Schema(name = "ZipCodeInfo")
public class ZipCodeOutDTO {
    private int zipCodeID;
    
    @Schema(required = true, example = "1234")
    private int zipCode;

    public ZipCodeOutDTO(int zipCode) {
        this.zipCode = zipCode;
    }

    public int getZipCodeID() {
        return zipCodeID;
    }

    public void setZipCodeID(int zipCodeID) {
        this.zipCodeID = zipCodeID;
    }

    public int getZipCode() {
        return zipCode;
    }

    public void setZipCode(int zipCode) {
        this.zipCode = zipCode;
    }
}
