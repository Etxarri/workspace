package edu.mondragon.webengl;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.ResponseEntity;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import org.junit.Test;

//@SpringBootTest(classes = MainApplication.class, webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class HelloControllerTests2 {

  @Autowired
  private TestRestTemplate template;

  @Test
  public void hello_ok() throws Exception {
      ResponseEntity<String> response = template.getForEntity("/", String.class);
      assertThat(response.getBody()).isEqualTo("Hello World, Spring Boot!");
  }

}

