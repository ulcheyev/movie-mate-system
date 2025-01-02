package cz.cvut.moviemate.activityservice.service;

import cz.cvut.moviemate.commonlib.dto.events.RatingActivityEvent;
import cz.cvut.moviemate.commonlib.exception.MovieMateBaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import static cz.cvut.moviemate.commonlib.utils.Constants.SERVICE_ACTIVITY_RATING_TOPIC;


@Service
@EnableAsync
@Slf4j(topic = "KAFKA_ACTIVITY_EVENT_PRODUCER")
@RequiredArgsConstructor
public class KafkaEventProducer {

  private final KafkaTemplate<String, RatingActivityEvent> kafkaTemplateSignUp;


  @Async
  public void publishRatingEvent (RatingActivityEvent activityEvent) {
    try {
      kafkaTemplateSignUp.send(
          SERVICE_ACTIVITY_RATING_TOPIC, String.valueOf(activityEvent.getUserId()), activityEvent);
      log.info("Published rating event: {}", activityEvent);
    } catch (KafkaException e) {
      log.error(
          "Error occurred while publishing rating event: {} \n {}", activityEvent, e.getMessage());
      throw new MovieMateBaseException("Error occurred while publishing rating event");
    }
  }

}
