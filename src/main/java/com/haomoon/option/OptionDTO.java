package com.haomoon.option;

import lombok.Data;

import java.sql.Blob;
@Data
public class OptionDTO {
    @Data
    class InsertDataDTO{
        private int favoriteId;
        private Blob image;
    }
}
