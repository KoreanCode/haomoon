package com.haomoon.favorite;

import org.springframework.stereotype.Service;

@Service
public class FavoriteService {
    FavoriteRepository favoriteRepository;

    public FavoriteDTO.InsertDTO newFavorite (FavoriteDTO.InsertDTO dto){

        return favoriteRepository.insertFavorite(dto);
    }

    public void newData (){

    }

}
