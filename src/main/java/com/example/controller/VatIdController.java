package com.example.controller;

import com.example.client.VeisApiClient;
import com.example.pojo.VatInfoRequest;
import com.example.pojo.VatInfoResponse;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.net.MalformedURLException;

@RestController
@RequestMapping("/api")
public class VatIdController {

    @Autowired
    private VeisApiClient veisApiClient;

    @PostMapping( "/validateUsingRestAPI")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Results are ok", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VatInfoResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "resource not found",
                    content = @Content) })
    @Operation(summary = "Springdoc open api sample API")
    public VatInfoResponse validateUsingRestAPI(@RequestBody VatInfoRequest vatInfoRequest){
        VatInfoResponse vatInfoResponse = new VatInfoResponse();

        vatInfoResponse.setMessage("Veis response: " + veisApiClient.validateUsingRestAPI(vatInfoRequest));
        return vatInfoResponse;
    }

    @PostMapping( "/validateUsingLibrary")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Results are ok", content = { @Content(mediaType = "application/json",
                    schema = @Schema(implementation = VatInfoResponse.class)) }),
            @ApiResponse(responseCode = "400", description = "Invalid request",
                    content = @Content),
            @ApiResponse(responseCode = "404", description = "resource not found",
                    content = @Content) })
    @Operation(summary = "Springdoc open api sample API")
    public VatInfoResponse validateUsingLibrary(@RequestBody VatInfoRequest vatInfoRequest){
        VatInfoResponse vatInfoResponse = new VatInfoResponse();

        try {
            vatInfoResponse.setMessage("Veis response: " + veisApiClient.validateUsingLibrary(vatInfoRequest));
        } catch (MalformedURLException e) {
            throw new RuntimeException(e);
        }
        return vatInfoResponse;
    }

    private static final String VALID_REQUEST = """
			{
			  "countryCode": "string",
			  "id": "string"
			}""";

    private static final String INVALID_REQUEST = """
			{
			  "countryCode": "number",
			  "id": "1.1.1"
			}""";

}
