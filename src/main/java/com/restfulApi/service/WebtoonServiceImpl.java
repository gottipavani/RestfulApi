package com.restfulApi.service;

import com.restfulApi.entity.Webtoon;
//import com.restfulApi.exception.ResourceNotFoundException;
import com.restfulApi.exception.ResourceNotFoundException;
import com.restfulApi.payload.WebtoonDto;
import com.restfulApi.repository.WebtoonRepository;

import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class WebtoonServiceImpl implements WebtoonService{
    //2)Now i want a repository layer to get all data  from the db or save the data in to the db
    private WebtoonRepository webtoonRepository;

    public WebtoonServiceImpl(WebtoonRepository webtoonRepository)
    {
        this.webtoonRepository = webtoonRepository;
    }

//    @Override
//    public Webtoon createWebtoon(Webtoon webtoon) {
//        // now to save the record we use webtoonRepository which will save the webtoon.
//        //once we save,save method  return backs the content as an entity object
//        Webtoon saveEntity= webtoonRepository.save(webtoon);
//        return saveEntity ;
//    }

    @Override
    public WebtoonDto createWebtoon(WebtoonDto webtoonDto) {
         Webtoon webtoon = mapToEntity(webtoonDto);
         Webtoon saveEntity = webtoonRepository.save(webtoon);
         WebtoonDto dto = mapToDto(saveEntity);
        return dto;
    }

    @Override
    public void deleteWebtoonById(Long id) {
        webtoonRepository.deleteById(id);
    }
    @Override
    public WebtoonDto updateWebtoon(long id, WebtoonDto webtoonDto) {
//         Optional<Webtoon> optionalWebtoon = webtoonRepository.findById(id);
//         Webtoon webtoon = optionalWebtoon.get();

        Webtoon webtoon = webtoonRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Webtoon", "id", id));
         webtoon.setTitle(webtoonDto.getTitle());
         webtoon.setDescription(webtoonDto.getDescription());
         webtoon.setCharacters(webtoonDto.getCharacters());
         Webtoon savedEntity = webtoonRepository.save(webtoon);
         WebtoonDto dto = mapToDto(savedEntity);
         return dto;
    }
    @Override
    public List<WebtoonDto> getAllWebtoons() {
         List<Webtoon> webtoons = webtoonRepository.findAll();
         List<WebtoonDto> webtoonDtos = webtoons.stream().map(w -> mapToDto(w)).collect(Collectors.toList());
        return webtoonDtos;
    }
    @Override
    public WebtoonDto getWebtoonById(Long id) {
//         Optional<Webtoon> optWebtoon = webtoonRepository.findById(id);
//         Webtoon webtoon = optWebtoon.get();
         Webtoon webtoon = webtoonRepository.findById(id).orElseThrow(
                () -> new ResourceNotFoundException("Webtoon", "id", id)
        );
        WebtoonDto webtoonDto = mapToDto(webtoon);
         return webtoonDto;

    }
    Webtoon mapToEntity(WebtoonDto dto){
        Webtoon entity=new Webtoon();
        entity.setId(dto.getId());
        entity.setTitle(dto.getTitle());
       entity.setDescription(dto.getDescription());
        entity.setCharacters(dto.getCharacters());
        return entity;
  }
    WebtoonDto mapToDto(Webtoon webtoon){
        WebtoonDto dto=new WebtoonDto();
        dto.setId(webtoon.getId());
        dto.setTitle(webtoon.getTitle());
        dto.setDescription(webtoon.getDescription());
        dto.setCharacters(webtoon.getCharacters());
        return dto;
    }

}
