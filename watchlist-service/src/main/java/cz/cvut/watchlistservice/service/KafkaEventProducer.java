package cz.cvut.watchlistservice.service;

import cz.cvut.moviemate.commonlib.dto.events.RatingActivityEvent;
import cz.cvut.moviemate.commonlib.dto.events.WatchlistEditEvent;
import cz.cvut.moviemate.commonlib.exception.MovieMateBaseException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.KafkaException;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.EnableAsync;
import org.springframework.stereotype.Service;

import static cz.cvut.moviemate.commonlib.utils.Constants.SERVICE_ACTIVITY_RATING_TOPIC;
import static cz.cvut.moviemate.commonlib.utils.Constants.SERVICE_WATCHLIST_EDIT_TOPIC;


@Service
@EnableAsync
@EnableKafka
@Slf4j(topic = "KAFKA_WATCHLIST_EVENT_PRODUCER")
@RequiredArgsConstructor
public class KafkaEventProducer {

  private final KafkaTemplate<String, WatchlistEditEvent> kafkaTemplateWatchlist;


  @Async
  public void publishWatchlistEvent(WatchlistEditEvent watchlistEditEvent) {
    try {
      kafkaTemplateWatchlist.send(
          SERVICE_WATCHLIST_EDIT_TOPIC, String.valueOf(watchlistEditEvent.getUserId()), watchlistEditEvent);
      log.info("Published watchlist event: {}", watchlistEditEvent);
    } catch (KafkaException e) {
      log.error(
          "Error occurred while publishing watchlist event: {} \n {}", watchlistEditEvent, e.getMessage());
      throw new MovieMateBaseException("Error occurred while publishing watchlist event");
    }
  }

}
