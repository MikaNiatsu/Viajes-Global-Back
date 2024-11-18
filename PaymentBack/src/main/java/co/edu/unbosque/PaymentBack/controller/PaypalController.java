package co.edu.unbosque.PaymentBack.controller;

import co.edu.unbosque.PaymentBack.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
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

public class PaypalController {

    @Autowired
    private final PaypalService paypalService;

    @PostMapping("/payment/create")
    public ResponseEntity<?> createPayment(@RequestBody PaymentRequest paymentRequest) {
        try {
            String cancelUrl = "http://localhost:3000/payment/cancel"; // Actualizar al puerto del frontend
            String successUrl = "http://localhost:3000/payment/success"; // Actualizar al puerto del frontend

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
            log.error("Error occurred:: ", e);
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Error creating PayPal payment");
        }
        return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Approval URL not found");
    }
}

@Data
class PaymentRequest {
    private String method;
    private String amount;
    private String currency;
    private String description;
}