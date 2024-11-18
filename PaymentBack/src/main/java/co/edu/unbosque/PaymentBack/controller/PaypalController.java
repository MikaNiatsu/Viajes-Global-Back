package co.edu.unbosque.PaymentBack.controller;

import co.edu.unbosque.PaymentBack.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import io.swagger.v3.oas.annotations.Parameter;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequiredArgsConstructor
@Slf4j
@RequestMapping("/payments")
@Tag(name = "PayPal Payments Controller", description = "Operations related to PayPal payment processing")
public class PaypalController {

    @Autowired
    private final PaypalService paypalService;

    @Operation(summary = "Create a PayPal payment", description = "Initiates a payment process using PayPal and returns an approval URL if successful.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Payment creation successful",
                    content = @Content(mediaType = "application/json", schema = @Schema(example = "{ 'approvalUrl': 'https://example.com' }"))),
            @ApiResponse(responseCode = "500", description = "Internal server error occurred")
    })
    @PostMapping("/payment/create")
    public ResponseEntity<?> createPayment(
            @RequestBody @Parameter(description = "Payment request object containing payment details") PaymentRequest paymentRequest) {
        try {
            String cancelUrl = "http://localhost:3000/payment/cancel";
            String successUrl = "http://localhost:3000/payment/success";

            Payment payment = paypalService.createPayment(
                    Double.valueOf(paymentRequest.getAmount()),
                    paymentRequest.getCurrency(),
                    paymentRequest.getMethod(),
                    "sale",
                    paymentRequest.getDescription(),
                    cancelUrl,
                    successUrl
            );

            for (Links links : payment.getLinks()) {
                if ("approval_url".equals(links.getRel())) {
                    return ResponseEntity.ok(Map.of("approvalUrl", links.getHref()));
                }
            }
        } catch (PayPalRESTException e) {
            log.error("Error occurred during PayPal payment creation: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating PayPal payment");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Approval URL not found");
    }
}

@Data
class PaymentRequest {
    @Schema(description = "Payment method (e.g., PayPal)", example = "paypal")
    private String method;

    @Schema(description = "Amount to be paid", example = "100.00")
    private String amount;

    @Schema(description = "Currency for the payment", example = "USD")
    private String currency;

    @Schema(description = "Description of the payment", example = "Purchase of product XYZ")
    private String description;
}