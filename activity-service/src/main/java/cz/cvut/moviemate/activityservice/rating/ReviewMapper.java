package cz.cvut.moviemate.activityservice.rating;

import cz.cvut.moviemate.commonlib.utils.SecurityUtils;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.springframework.security.core.context.SecurityContextHolder;

import java.time.Instant;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface ReviewMapper {

    RatingReview toEntity(ReviewDto reviewDto);

    @Mapping(target = "timestamp", source = "key.timestamp")
    ReviewDto toDto(RatingReview review);

    void update(@MappingTarget RatingReview entity, RatingReview updateEntity);

    default RatingReviewKey extractRatingReviewKey(ReviewDto reviewDto) {
        return new RatingReviewKey(reviewDto.movieId(), SecurityUtils.getPrincipalUsername(
                SecurityContextHolder.getContext().getAuthentication()
        ), reviewDto != null
                ? reviewDto.timestamp()
                : Instant.now());
    }
}
