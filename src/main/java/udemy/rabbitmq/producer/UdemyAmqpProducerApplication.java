package udemy.rabbitmq.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;
import udemy.rabbitmq.entity.Employee;
import udemy.rabbitmq.entity.Picture;

import java.time.LocalDate;
import java.util.List;
import java.util.concurrent.ThreadLocalRandom;

@EnableScheduling
@SpringBootApplication
public class UdemyAmqpProducerApplication implements CommandLineRunner {

	@Autowired
	HelloWorldProducer producer;

	@Autowired
	EmployeeJsonProducer employeeJsonProducer;

	@Autowired
	PictureProducerToDirect pictureProducerDirect;

	@Autowired
	PictureProducerToTopic pictureProducerTopic;

	@Autowired
	PictureProducerWithError pictureProducerWithError;

	public static void main(String[] args) {
		SpringApplication.run(UdemyAmqpProducerApplication.class, args);
	}

	@Override
	public void run(String... args) throws Exception {
		producer.sendHelloWorldMessage("John");

//		Faker faker = new Faker();
		boolean produceEmployeesQueue = false;
		if (produceEmployeesQueue) {
			for (long i = 0; i < 10; i++) {
				LocalDate date = LocalDate.now().minusYears(20 + i).plusDays(i);

				Employee employee = new Employee(i, "Thing #" + i, date);
				employeeJsonProducer.sendMessage(employee);
			}
		}

		List<String> types = List.of("jpg", "png", "svg", "gif");
		List<String> sources = List.of("mobile", "desktop", "browser");
		boolean produceToDirectQueue = false;
		if(produceToDirectQueue) {
			for (int i = 0; i < 10; i++) {
				Picture picture = new Picture();
				picture.setName("Picture " + i);
				picture.setSize(ThreadLocalRandom.current().nextLong(1, 10001));
				picture.setType(types.get(i % types.size()));
				pictureProducerDirect.sendMessage(picture);
			}
		}

		boolean produceToTopic = false;
		if(produceToTopic) {
			for(int i = 0; i < 10; i++) {
				Picture picture = new Picture();
				picture.setName("Picture " + i);
				picture.setSource(sources.get(i % sources.size()));
				picture.setSize(ThreadLocalRandom.current().nextLong(1, 10001));
				picture.setType(types.get(i % types.size()));
				pictureProducerTopic.sendMessage(picture);
			}
		}

		boolean produceWithError = true;
		if(produceWithError) {
			for(int i = 0; i < 10; i++) {
				Picture picture = new Picture();
				picture.setName("Picture " + i);
				picture.setSource(sources.get(i % sources.size()));
				picture.setSize(ThreadLocalRandom.current().nextLong(9000, 10000));
				picture.setType(types.get(i % types.size()));
				pictureProducerWithError.sendMessage(picture);
			}
		}

	}
}
