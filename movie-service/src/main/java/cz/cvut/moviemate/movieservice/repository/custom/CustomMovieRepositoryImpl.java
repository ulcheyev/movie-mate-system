package cz.cvut.moviemate.movieservice.repository.custom;

import cz.cvut.moviemate.movieservice.model.Movie;
import lombok.RequiredArgsConstructor;
import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;

@RequiredArgsConstructor
public class CustomMovieRepositoryImpl implements CustomMovieRepository {

    private final MongoTemplate mongoTemplate;

    @Override
    public void removeGenreFromMovieGenres(String genreId) {
        Query query = new Query(Criteria.where("genre._id").is(new ObjectId(genreId)));
        Update update = new Update().pull("genres", Query.query(Criteria.where("_id").is(new ObjectId(genreId))));

        mongoTemplate.updateMulti(query, update, Movie.class);
    }
}
