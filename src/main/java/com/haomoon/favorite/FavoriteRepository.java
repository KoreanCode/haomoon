package com.haomoon.favorite;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface FavoriteRepository {

    /*이상형 월드컵 초기 생성*/
    public FavoriteDTO.InsertDTO insertFavorite(FavoriteDTO.InsertDTO dto);


}
