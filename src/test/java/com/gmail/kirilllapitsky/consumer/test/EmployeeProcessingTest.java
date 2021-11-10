package com.gmail.kirilllapitsky.consumer.test;

import com.gmail.kirilllapitsky.consumer.data.DataSet;
import com.gmail.kirilllapitsky.consumer.dto.EmployeeDto;
import com.gmail.kirilllapitsky.consumer.entity.Employee;
import com.gmail.kirilllapitsky.consumer.repository.EmployeeRepository;
import com.gmail.kirilllapitsky.consumer.util.Mapper;
import org.junit.ClassRule;
import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.test.context.ActiveProfiles;
import org.springframework.test.context.DynamicPropertyRegistry;
import org.springframework.test.context.DynamicPropertySource;
import org.springframework.test.context.junit4.SpringRunner;
import org.testcontainers.containers.KafkaContainer;
import org.testcontainers.containers.Network;
import org.testcontainers.utility.DockerImageName;

import static org.junit.jupiter.api.Assertions.assertEquals;

@ActiveProfiles("test")
@RunWith(SpringRunner.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.DEFINED_PORT)
class EmployeeProcessingTest {
    private static Network network = Network.newNetwork();
    @ClassRule
    public static KafkaContainer kafka =
            new KafkaContainer(DockerImageName.parse("confluentinc/cp-kafka:5.4.3"));

    @Autowired
    private TestRestTemplate testRestTemplate;

    @Autowired
    private EmployeeRepository employeeRepository;

    @DynamicPropertySource
    static void kafkaProperties(DynamicPropertyRegistry registry) {
        kafka.start();
        registry.add("spring.kafka.consumer.bootstrap-servers", () ->
                kafka.getHost() + ":" + kafka.getFirstMappedPort()
        );
        registry.add("spring.kafka.producer.bootstrap-servers", () ->
                kafka.getHost() + ":" + kafka.getFirstMappedPort()
        );
    }

    @Test
    public void shouldSaveNewEmployeeTest() {
        testRestTemplate.postForEntity("/api/employee", new HttpEntity<>(DataSet.getNewEmployeeDto()), Void.class);
        assertEquals(1, employeeRepository.findAll().size());
    }

    @Test
    public void shouldReturnEmployeeTest() {
        Employee employee = DataSet.getEmployeeEntity();
        employeeRepository.save(employee);
        EmployeeDto employeeDtoShouldBeReturned = Mapper.mapEmployeeDto(employee);

        EmployeeDto employeeDto = testRestTemplate.getForObject("/api/employee/1", EmployeeDto.class);

        assertEquals(employeeDtoShouldBeReturned, employeeDto);
    }
}