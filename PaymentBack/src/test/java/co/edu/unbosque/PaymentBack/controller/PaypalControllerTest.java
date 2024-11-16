package co.edu.unbosque.PaymentBack.controller;

import co.edu.unbosque.PaymentBack.service.PaypalService;
import com.paypal.api.payments.Links;
import com.paypal.api.payments.Payment;
import com.paypal.base.rest.PayPalRESTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.springframework.web.servlet.view.RedirectView;

import java.util.Collections;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class PaypalControllerTest {

    @Mock
    private PaypalService paypalService;

    @InjectMocks
    private PaypalController paypalController;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testCreatePaymentSuccess() throws PayPalRESTException {
        Payment mockPayment = new Payment();
        Links approvalLink = new Links();
        approvalLink.setRel("approval_url");
        approvalLink.setHref("http://mock-approval-url.com");
        mockPayment.setLinks(Collections.singletonList(approvalLink));

        when(paypalService.createPayment(100.0, "USD", "paypal", "sale", "Test Payment",
                "http://localhost:8080/payment/cancel", "http://localhost:8080/payment/success"))
                .thenReturn(mockPayment);

        RedirectView redirectView = paypalController.createPayment("paypal", "100.0", "USD", "Test Payment");

        assertEquals("http://mock-approval-url.com", redirectView.getUrl());
        verify(paypalService, times(1)).createPayment(100.0, "USD", "paypal", "sale", "Test Payment",
                "http://localhost:8080/payment/cancel", "http://localhost:8080/payment/success");
    }

    @Test
    void testCreatePaymentError() throws PayPalRESTException {
        when(paypalService.createPayment(anyDouble(), anyString(), anyString(), anyString(), anyString(), anyString(), anyString()))
                .thenThrow(new PayPalRESTException("Error creating payment"));

        RedirectView redirectView = paypalController.createPayment("paypal", "100.0", "USD", "Test Payment");

        assertEquals("/payment/error", redirectView.getUrl());
        verify(paypalService, times(1)).createPayment(100.0, "USD", "paypal", "sale", "Test Payment",
                "http://localhost:8080/payment/cancel", "http://localhost:8080/payment/success");
    }

    @Test
    void testPaymentSuccessApproved() throws PayPalRESTException {
        Payment mockPayment = new Payment();
        mockPayment.setState("approved");

        when(paypalService.executePayment("paymentId123", "payerId123")).thenReturn(mockPayment);

        String result = paypalController.paymentSuccess("paymentId123", "payerId123");

        assertEquals("paymentSuccess", result);
        verify(paypalService, times(1)).executePayment("paymentId123", "payerId123");
    }

    @Test
    void testPaymentSuccessNotApproved() throws PayPalRESTException {
        Payment mockPayment = new Payment();
        mockPayment.setState("failed");

        when(paypalService.executePayment("paymentId123", "payerId123")).thenReturn(mockPayment);

        String result = paypalController.paymentSuccess("paymentId123", "payerId123");

        assertEquals("paymentSuccess", result);
        verify(paypalService, times(1)).executePayment("paymentId123", "payerId123");
    }

    @Test
    void testPaymentSuccessException() throws PayPalRESTException {
        when(paypalService.executePayment("paymentId123", "payerId123")).thenThrow(new PayPalRESTException("Execution error"));

        String result = paypalController.paymentSuccess("paymentId123", "payerId123");

        assertEquals("paymentSuccess", result);
        verify(paypalService, times(1)).executePayment("paymentId123", "payerId123");
    }

    @Test
    void testPaymentCancel() {
        String result = paypalController.paymentCancel();

        assertEquals("paymentCancel", result);
    }

    @Test
    void testPaymentError() {
        String result = paypalController.paymentError();

        assertEquals("paymentError", result);
    }
}
