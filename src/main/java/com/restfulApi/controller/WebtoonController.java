package com.restfulApi.controller;

import com.restfulApi.entity.Webtoon;
import com.restfulApi.payload.WebtoonDto;
import com.restfulApi.service.WebtoonService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/webtoons")
public class WebtoonController {

    private WebtoonService webtoonService;

    public WebtoonController(WebtoonService webtoonService) {
        this.webtoonService = webtoonService;
    }

    //http://localhost:8080/api/v1/webtoons(I just developed the url, which can call backend of our application)
   @PreAuthorize("hasRole('ADMIN')")
    @PostMapping
    public ResponseEntity<WebtoonDto> createWebtoon(@Valid @RequestBody WebtoonDto webtoonDto)
    {
        WebtoonDto webDto = webtoonService.createWebtoon(webtoonDto);
        return new ResponseEntity<>(webDto, HttpStatus.CREATED);
    }
   // http://localhost:8080/api/v1/webtoons?id
    @PreAuthorize("hasRole('ADMIN')")
    @DeleteMapping
    public ResponseEntity<String> deleteWebtoonById(@RequestParam Long id){
        webtoonService.deleteWebtoonById(id);
        return new ResponseEntity<>("Webtoon deleted",HttpStatus.OK);
    }
    //http://localhost:8080/api/v1/webtoons?id
   @PreAuthorize("hasRole('ADMIN')")
    @PutMapping
    public ResponseEntity<?> updateWebtoon(@RequestParam long id,
                                        @Valid @RequestBody WebtoonDto webtoonDto,
                                                    BindingResult result)
    {
        if(result.hasErrors()){
            return new ResponseEntity<>(result.getFieldError().getDefaultMessage(),HttpStatus.BAD_REQUEST);
        }

       WebtoonDto dto= webtoonService.updateWebtoon(id,webtoonDto);
       return new ResponseEntity<>(dto,HttpStatus.OK);
    }
    //http://localhost:8080/api/v1/webtoons
    @GetMapping
    public ResponseEntity<List<WebtoonDto>> getAllWebtoons()
    {
         List<WebtoonDto> allWebtoons = webtoonService.getAllWebtoons();
         return new ResponseEntity<>(allWebtoons,HttpStatus.OK);
    }
   // http://localhost:8080/api/v1/webtoons/byid?id=1
    @GetMapping("/byid")
    public ResponseEntity<WebtoonDto> getWebtoonById(@RequestParam Long id) {
        WebtoonDto webtoonDto = webtoonService.getWebtoonById(id);
        return new ResponseEntity<>(webtoonDto, HttpStatus.OK);
    }

}
