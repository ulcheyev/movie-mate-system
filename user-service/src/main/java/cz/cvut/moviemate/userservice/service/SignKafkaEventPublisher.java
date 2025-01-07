package cz.cvut.moviemate.userservice.service;

import cz.cvut.moviemate.commonlib.dto.events.UserSignUpEvent;
import cz.cvut.moviemate.commonlib.exception.MovieMateBaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import static cz.cvut.moviemate.commonlib.utils.Constants.SERVICE_USER_SIGN_UP_TOPIC;


@Service
@EnableAsync
@Slf4j(topic = "KAFKA_SIGN_EVENT_PRODUCER")
@RequiredArgsConstructor
public class SignKafkaEventPublisher {

  private final KafkaTemplate<String, UserSignUpEvent> kafkaTemplateSignUp;


  @Async
  public void publishSignUpEvent(UserSignUpEvent signUpEvent) {
    try {
      kafkaTemplateSignUp.send(
          SERVICE_USER_SIGN_UP_TOPIC, String.valueOf(signUpEvent.userId()), signUpEvent);
      log.info("Published sign up event: {}", signUpEvent);
    } catch (KafkaException e) {
      log.error(
          "Error occurred while publishing sign up event: {} \n {}", signUpEvent, e.getMessage());
      throw new MovieMateBaseException("Error occurred while publishing sign up event");
    }
  }

}
