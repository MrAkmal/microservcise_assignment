package com.example.keyword_microservice.keyword;


import com.example.keyword_microservice.country.CountryBaseService;
import org.springframework.stereotype.Component;

@Component
public class KeywordBaseMapper {


    public KeywordBaseDTO toDTO(KeywordBase keywordBase,String countryName){

        return KeywordBaseDTO.builder()
                .id(keywordBase.getId())
                .genericName(keywordBase.getGenericName())
                .country(countryName)
                .wiseName(keywordBase.getWiseName())
                .build();
    }


    public KeywordBase fromDTO(KeywordBaseCreateDTO dto){
        return KeywordBase.builder()
                .genericName(dto.getGenericName())
                .countryId(dto.getCountryId())
                .wiseName(dto.getWiseName())
                .build();
    }

    public KeywordBase fromUpdateDto(KeywordBase keywordBase, KeywordBaseUpdateDTO dto) {

        return KeywordBase.builder()
                .id(keywordBase.getId())
                .genericName(dto.getGenericName())
                .countryId(dto.getCountryId())
                .wiseName(dto.getWiseName())
                .build();
    }
}
