package com.haomoon.favorite;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/app/user")
public class FavoriteController {
    @Autowired
    private  FavoriteService favoriteService;

    /**
     * 처음 이상형월드컵 생성
     * TODO---
     * 진행 단계
     * 1.방생성 - FavoriteService 에서 수행
     * 2.이미지삽입 - OptionService 에서 수헹
     * 3.이미지 정보 추가 - OptionService 에서 Update 로 수행되어야함
     *
     */
    @PostMapping
    public ResponseEntity<FavoriteDTO.InsertDTO> createFavorite(FavoriteDTO.InsertDTO favoriteDTO){
        FavoriteDTO.InsertDTO favorite = favoriteService.newFavorite(favoriteDTO);

        return ResponseEntity.status(HttpStatus.CREATED).body(favorite);
    }




}
