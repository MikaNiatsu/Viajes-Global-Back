package co.edu.unbosque.bookingback.service;

import co.edu.unbosque.bookingback.model.ClientDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;

@FeignClient(name = "ClientBack", url = "${https://localhost:8081/}")
public interface ClientServiceClient {

    @GetMapping("/clients/{clientId}")
    ClientDTO getClientById(@PathVariable("id_client") int id_client);
}
