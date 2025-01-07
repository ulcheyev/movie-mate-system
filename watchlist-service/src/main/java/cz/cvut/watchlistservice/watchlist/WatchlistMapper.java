package cz.cvut.watchlistservice.watchlist;

import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;
import org.mapstruct.NullValuePropertyMappingStrategy;
import org.mapstruct.factory.Mappers;

@Mapper(componentModel = "spring", nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
public interface WatchlistMapper {

    WatchlistMapper INSTANCE = Mappers.getMapper(WatchlistMapper.class);

    WatchlistDTO toDTO(Watchlist watchlist);

    @Mapping(target = "userId", ignore = true)
    Watchlist toEntity(WatchlistDTO watchlistDTO);

    void update(@MappingTarget Watchlist entity, Watchlist updateEntity);
}
