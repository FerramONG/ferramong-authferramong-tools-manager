package ferramong.toolsmanager.controllers;

import ferramong.toolsmanager.converters.RentedToolConverter;
import ferramong.toolsmanager.dto.ErrorResponse;
import ferramong.toolsmanager.dto.RentalRequest;
import ferramong.toolsmanager.dto.RentedTool;
import ferramong.toolsmanager.exceptions.RentalNotFoundException;
import ferramong.toolsmanager.exceptions.ToolNotAvailableException;
import ferramong.toolsmanager.exceptions.ToolNotFoundException;
import ferramong.toolsmanager.services.RentalsService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.cors.CorsConfiguration;

import javax.validation.Valid;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@RestController
@RequestMapping("/rentals")
@CrossOrigin(
        origins = CorsConfiguration.ALL,
        methods = {RequestMethod.GET, RequestMethod.POST, RequestMethod.PUT, RequestMethod.DELETE}
)
@AllArgsConstructor
@Tag(name = "Rentals", description = "Manage tools' rentals")
public class RentalsController {
    private final RentalsService service;

    @GetMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "List all tools rented by an existing dweller.",
            parameters = @Parameter(name = "dwellerId", description = "Tool renter ID", required = true),
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully retrieved tools rented by dweller"),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Unexpected error occurred.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public List<RentedTool> getRentalsByDweller(@RequestHeader int dwellerId) {
        return service.getAllRentedTools(dwellerId).stream()
                .map(RentedToolConverter::from)
                .filter(Objects::nonNull)
                .collect(Collectors.toList());
    }

    @PostMapping(consumes = APPLICATION_JSON_VALUE, produces = APPLICATION_JSON_VALUE)
    @ResponseStatus(HttpStatus.CREATED)
    @Operation(
            summary = "Rent an existing tool to a dweller.",
            responses = {
                    @ApiResponse(responseCode = "201", description = "Successfully rented tool."),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Tool not found or not available.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Unexpected error occurred.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public RentedTool rentTool(@RequestBody @Valid RentalRequest rentalRequest)
            throws ToolNotAvailableException, ToolNotFoundException {
        var rentedToolEntity = service.rentTool(rentalRequest);
        return RentedToolConverter.from(rentedToolEntity);
    }

    @PutMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Return a rented tool.",
            parameters = {
                    @Parameter(name = "ownerDwellerId", description = "Tool owner ID", required = true),
                    @Parameter(name = "toolId", description = "Tool ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully returned tool."),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Rental not found.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Unexpected error occurred.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public RentedTool returnTool(@RequestHeader int ownerDwellerId, @RequestHeader int toolId)
            throws RentalNotFoundException {
        var returnedToolEntity = service.returnTool(toolId, ownerDwellerId);
        return RentedToolConverter.from(returnedToolEntity);
    }

    @DeleteMapping(produces = APPLICATION_JSON_VALUE)
    @Operation(
            summary = "Cancel an existing rental.",
            parameters = {
                    @Parameter(name = "ownerDwellerId", description = "Tool owner ID", required = true),
                    @Parameter(name = "toolId", description = "Tool ID", required = true)
            },
            responses = {
                    @ApiResponse(responseCode = "200", description = "Successfully canceled rental."),
                    @ApiResponse(
                            responseCode = "404",
                            description = "Rental not found.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class))
                    ),
                    @ApiResponse(
                            responseCode = "500",
                            description = "Unexpected error occurred.",
                            content = @Content(schema = @Schema(implementation = ErrorResponse.class)))
            }
    )
    public RentedTool cancelRental(@RequestHeader int ownerDwellerId, @RequestHeader int toolId)
            throws RentalNotFoundException {
        var canceledRentalEntity = service.cancelRental(toolId, ownerDwellerId);
        return RentedToolConverter.from(canceledRentalEntity);
    }
}
