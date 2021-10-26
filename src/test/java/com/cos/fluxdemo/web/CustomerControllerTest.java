package com.cos.fluxdemo.web;

import static org.mockito.Mockito.when;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.reactive.AutoConfigureWebTestClient;
import org.springframework.boot.test.autoconfigure.web.reactive.WebFluxTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.reactive.server.WebTestClient;

import com.cos.fluxdemo.domain.Customer;
import com.cos.fluxdemo.domain.CustomerRepository;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@WebFluxTest
public class CustomerControllerTest {
	
	@MockBean
	private CustomerRepository customerRepository;

	@Autowired
	private WebTestClient webClient;
	
	@Test
	public void findOneTest() {
		Mono<Customer> givenData = Mono.just(new Customer("Jack", "Bauer"));
		when(customerRepository.findById(1L)).thenReturn(givenData);
		
		webClient.get().uri("/customer/{id}", 1L)
		.exchange()
		.expectBody()
		.jsonPath("$.firstName").isEqualTo("Jack")
		.jsonPath("$.lastName").isEqualTo("Bauer");
	}
}
