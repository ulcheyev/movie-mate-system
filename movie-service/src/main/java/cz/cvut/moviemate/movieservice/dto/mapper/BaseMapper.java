package cz.cvut.moviemate.movieservice.dto.mapper;

import cz.cvut.moviemate.movieservice.dto.PageDto;
import org.mapstruct.Mapper;
import org.springframework.data.domain.Page;

import java.io.Serializable;
import java.util.List;

@Mapper(componentModel = "spring")
public interface BaseMapper {

    default <T extends Serializable> PageDto<T> convertToPageDto(List<T> elements, int pageNo, int pageSize, Page<?> page) {
        return PageDto.<T>builder()
                .elements(elements)
                .pageNo(pageNo)
                .pageSize(pageSize)
                .totalElements(page.getTotalElements())
                .totalPages(page.getTotalPages())
                .isLast(page.isLast())
                .build();
    }
}
