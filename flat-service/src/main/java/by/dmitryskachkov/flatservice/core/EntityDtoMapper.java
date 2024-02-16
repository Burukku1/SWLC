package by.dmitryskachkov.flatservice.core;

import by.dmitryskachkov.flatservice.repo.entity.Flat;
import org.mapstruct.factory.Mappers;

import java.util.List;

public interface EntityDtoMapper {
    EntityDtoMapper INSTANCE = Mappers.getMapper(EntityDtoMapper.class);
    Flat flatDtoToFlat(by.dmitryskachkov.flatservice.core.dto.Flat flat);

    by.dmitryskachkov.flatservice.core.dto.Flat flatToFlatDto(Flat flat);

    default List<by.dmitryskachkov.flatservice.core.dto.Flat> flatListToFlatDTOList(List<Flat> content) {
        return content.stream().map(INSTANCE::flatToFlatDto).toList();
    }

}
