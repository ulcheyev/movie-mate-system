package cz.cvut.moviemate.recommendationservice.recommendation;

import cz.cvut.moviemate.commonlib.dto.GenreResponse;
import cz.cvut.moviemate.commonlib.dto.events.RatingActivityEvent;
import cz.cvut.moviemate.commonlib.dto.events.UserSignUpEvent;
import cz.cvut.moviemate.commonlib.dto.events.WatchlistEditEvent;
import cz.cvut.moviemate.recommendationservice.client.MovieServiceClient;
import cz.cvut.moviemate.recommendationservice.foaf.FOAFService;
import cz.cvut.moviemate.recommendationservice.model.Movie;
import cz.cvut.moviemate.recommendationservice.model.User;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static cz.cvut.moviemate.commonlib.utils.Constants.*;

@Service
@RequiredArgsConstructor
public class GenreBasedRecommendationService implements RecommendationService{

    private final MovieServiceClient movieServiceClient;
    private final FOAFService foafService;
    private final MovieRepository movieRepository;
    private final static Integer RECOMMENDATIONS_QUANTITY = 6;


    @Override
    public Recommendations getRecommendations(String userId) {
        Recommendations recommendations = new Recommendations();
        Set<String> recommendedMovieIds = new HashSet<>();
        recommendations.setUserId(userId);
        User user = foafService.getUserNodeOrThrow(userId);

        if(user.getInterestedIn().isEmpty()
        && user.getRatedMovies().isEmpty()
        && user.getFriends().isEmpty())
        {
            recommendedMovieIds.addAll(getNewUserRecommendations());
            recommendations.setRecommendedMovieIds(recommendedMovieIds);
            return recommendations;
        }

        else
        {
            // 2
            List<Movie> friendsRecommendations = movieRepository.findMoviesThatUserFriendsRatedOrInterested(userId);
            recommendedMovieIds.addAll(friendsRecommendations.stream()
                    .map(Movie::getId)
                    .collect(Collectors.toSet()));

            // 2
            List<Movie> similarUsersRecommendations = movieRepository.findMoviesThatOtherUsersRatedOrInterested(userId);
            recommendedMovieIds.addAll(similarUsersRecommendations.stream()
                    .map(Movie::getId)
                    .collect(Collectors.toSet()));


            // 2
            List<String> preferredGenres = getUserPreferredGenres(user);
            Set<String> genreBasedRandomMovies = movieServiceClient.getMovieIdsByGenre(preferredGenres);
            recommendedMovieIds.addAll(genreBasedRandomMovies.stream()
                    .limit(2)
                    .collect(Collectors.toSet()));

            if (recommendedMovieIds.size() < RECOMMENDATIONS_QUANTITY) {
                List<GenreResponse> topGenres = movieServiceClient.getTopGenres();
                List<String> genresNames = new ArrayList<>();
                for(var genre: topGenres) {
                    genresNames.add(genre.getName());
                }
                genresNames.removeAll(preferredGenres);
                Set<String> additionalMovies = movieServiceClient.getMovieIdsByGenre(genresNames);
                for (String movieId : additionalMovies) {
                    if (recommendedMovieIds.size() <= 6) {
                        recommendedMovieIds.add(movieId);
                    }
                    else {
                        break;
                    }
                }
            }

        }

        recommendations.setRecommendedMovieIds(recommendedMovieIds);
        return recommendations;
    }

    private List<String> getUserPreferredGenres(User user) {
        Set<String> genres = new HashSet<>();

        if (user.getInterestedIn() != null) {
            genres.addAll(user.getInterestedIn().stream()
                    .map(ir -> ir.getMovie().getGenre())
                    .collect(Collectors.toSet()));
        }

        if (user.getRatedMovies() != null) {
            genres.addAll(user.getRatedMovies().stream()
                    .map(rr -> rr.getMovie().getGenre())
                    .collect(Collectors.toSet()));
        }

        return new ArrayList<>(genres);
    }

    @Override
    @KafkaListener(
            topics = SERVICE_USER_SIGN_UP_TOPIC,
            groupId = "recommendation-group"
    )
    public void syncGraphToNewUser(UserSignUpEvent event) {
        foafService.createUserNode(String.valueOf(event.userId()), event.username());
    }

    protected Set<String> getNewUserRecommendations() {
        var topGenres = movieServiceClient.getTopGenres();
        List<String> genresNames = new ArrayList<>();
        for(var genre: topGenres) {
            genresNames.add(genre.getName());
        }
        return movieServiceClient.getMovieIdsByGenre(genresNames);
    }


    @KafkaListener(
            topics = SERVICE_WATCHLIST_EDIT_TOPIC,
            groupId = "recommendation-group"
    )
    public void syncGraphToUserWatchlistEditActivity(WatchlistEditEvent event) {
        
    }

    @KafkaListener(
            topics = SERVICE_ACTIVITY_RATING_TOPIC,
            groupId = "recommendation-group"
    )
    public void syncGraphToUserRatingActivity(RatingActivityEvent event) {

    }

    public void syncGraphToUserSocialConnectionActivity(UserSignUpEvent event) {

    }
}
