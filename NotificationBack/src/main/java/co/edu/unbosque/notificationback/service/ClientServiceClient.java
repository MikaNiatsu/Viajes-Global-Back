package co.edu.unbosque.notificationback.service;

import co.edu.unbosque.notificationback.model.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ClientBack", url = "${https://localhost:8081/}")
public interface ClientServiceClient {

    @GetMapping("/clients/{clientId}")
    ClientDTO getClientById(@PathVariable("customer_id") int customer_id);
}
