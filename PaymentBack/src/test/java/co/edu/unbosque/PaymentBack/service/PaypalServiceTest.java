package co.edu.unbosque.PaymentBack.service;

import com.paypal.api.payments.*;
import com.paypal.base.rest.APIContext;
import com.paypal.base.rest.PayPalRESTException;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;

import java.util.Collections;
import java.util.Locale;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;
import static org.mockito.MockitoAnnotations.openMocks;

class PaypalServiceTest {

    @Mock
    private APIContext apiContext;

    @InjectMocks
    private PaypalService paypalService;

    @BeforeEach
    void setUp() {
        openMocks(this);
    }

    @Test
    void testCreatePaymentSuccess() throws PayPalRESTException {
        Payment mockPayment = new Payment();
        mockPayment.setId("PAYMENT-ID");
        Links approvalLink = new Links();
        approvalLink.setRel("approval_url");
        approvalLink.setHref("http://mock-approval-url.com");
        mockPayment.setLinks(Collections.singletonList(approvalLink));

        when(mockPayment.create(apiContext)).thenReturn(mockPayment);

        Payment result = paypalService.createPayment(
                100.0,
                "USD",
                "paypal",
                "sale",
                "Test Payment",
                "http://localhost:8080/payment/cancel",
                "http://localhost:8080/payment/success"
        );

        assertEquals("PAYMENT-ID", result.getId());
        assertEquals("http://mock-approval-url.com", result.getLinks().get(0).getHref());
        verify(apiContext, times(1));
    }

    @Test
    void testExecutePaymentSuccess() throws PayPalRESTException {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId("PAYER-ID");

        Payment mockPayment = new Payment();
        mockPayment.setId("PAYMENT-ID");
        mockPayment.setState("approved");

        when(new Payment().execute(apiContext, paymentExecution)).thenReturn(mockPayment);

        Payment result = paypalService.executePayment("PAYMENT-ID", "PAYER-ID");

        assertEquals("PAYMENT-ID", result.getId());
        assertEquals("approved", result.getState());
        verify(apiContext, times(1));
    }

    @Test
    void testCreatePaymentError() throws PayPalRESTException {
        when(new Payment().create(apiContext)).thenThrow(new PayPalRESTException("Error creating payment"));

        Exception exception = assertThrows(PayPalRESTException.class, () ->
                paypalService.createPayment(
                        100.0,
                        "USD",
                        "paypal",
                        "sale",
                        "Test Payment",
                        "http://localhost:8080/payment/cancel",
                        "http://localhost:8080/payment/success"
                )
        );

        assertEquals("Error creating payment", exception.getMessage());
        verify(apiContext, times(1));
    }

    @Test
    void testExecutePaymentError() throws PayPalRESTException {
        PaymentExecution paymentExecution = new PaymentExecution();
        paymentExecution.setPayerId("PAYER-ID");

        when(new Payment().execute(apiContext, paymentExecution)).thenThrow(new PayPalRESTException("Error executing payment"));

        Exception exception = assertThrows(PayPalRESTException.class, () ->
                paypalService.executePayment("PAYMENT-ID", "PAYER-ID")
        );

        assertEquals("Error executing payment", exception.getMessage());
        verify(apiContext, times(1));
    }
}
