package cz.cvut.userservice.dto.mapper;

import cz.cvut.userservice.dto.UserHistoryDto;
import cz.cvut.userservice.model.UserHistory;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserHistoryMapper {

    UserHistoryDto toDto(UserHistory userHistory);
}
