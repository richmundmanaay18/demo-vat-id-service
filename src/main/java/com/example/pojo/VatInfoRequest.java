package com.example.pojo;

import io.swagger.v3.oas.annotations.media.Schema;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class VatInfoRequest {

    @NotNull
    @NotBlank
    @Schema(description = "This is the 2 letter country code",example = "PL = Poland")
    private String countryCode;

    @NotNull
    @NotBlank
    @Schema(description = "This is the vat id",example = "PL7272445205")
    private String id;

    public String getCountryCode() {
        return countryCode;
    }

    public void setCountryCode(String countryCode) {
        this.countryCode = countryCode;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public String toString() {
        return "Country code: " + this.getCountryCode() + " | " + "Id: " + getId();
    }
}
