package cz.cvut.moviemate.movieservice.repository.custom;

import cz.cvut.moviemate.movieservice.model.Movie;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.aggregation.Aggregation;
import org.springframework.data.mongodb.core.aggregation.AggregationResults;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

import java.util.List;
import java.util.Map;

@RequiredArgsConstructor
public class CustomMovieRepositoryImpl implements CustomMovieRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public void removeGenreFromMovieGenres(String genreId) {
        Query query = new Query(Criteria.where("genres._id").is(genreId));
        Update update = new Update().pull("genres", Query.query(Criteria.where("_id").is(genreId)));

        mongoTemplate.updateMulti(query, update, Movie.class);
    }

    @Override
    public List<String> getTopGenres() {
        Aggregation aggregation = Aggregation.newAggregation(
                Aggregation.unwind("genres"),
                Aggregation.group("genres._id").count().as("count"),
                Aggregation.sort(Sort.Direction.DESC, "count"),
                Aggregation.limit(5),
                Aggregation.project("_id")
        );

        AggregationResults<Map> results = mongoTemplate.aggregate(aggregation, "movies", Map.class);
        return results.getMappedResults().stream()
                .map(res -> (String) res.get("_id"))
                .toList();
    }
}
