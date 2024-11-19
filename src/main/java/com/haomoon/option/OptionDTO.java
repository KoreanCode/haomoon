package com.haomoon.option;

import lombok.Data;

import java.sql.Blob;

public class OptionDTO {
    @Data
    class InsertDataDTO{
        private Blob image ;
    }
}
