package cz.cvut.userservice.dto;

import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.RequiredArgsConstructor;
import lombok.experimental.SuperBuilder;

@Data
@SuperBuilder
@RequiredArgsConstructor
@EqualsAndHashCode(callSuper = true)
public class AppUserDetailsDto extends AppUserDto {
    private final UserHistoryDto userHistory;
}
