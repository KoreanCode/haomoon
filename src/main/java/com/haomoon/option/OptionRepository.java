package com.haomoon.option;

import org.apache.ibatis.annotations.Mapper;

@Mapper
public interface OptionRepository {
    /*이상형 월드컵 이미지 삽입*/
    public  void insertFavoriteData();
}
