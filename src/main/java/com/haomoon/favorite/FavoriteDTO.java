package com.haomoon.favorite;

import lombok.Data;
import lombok.Getter;

import java.sql.Timestamp;

public class FavoriteDTO {

    @Data
    class InsertDTO{

        private String title;
        private String category;
        private Long userId;
        private Timestamp createdAt;
    }

}
