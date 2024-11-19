package com.haomoon.option;

import lombok.*;

import java.sql.Blob;
@NoArgsConstructor
@AllArgsConstructor
@Data
@Builder
@ToString
/**
 *  이상형 월드컵 옵션 선택
 */
public class OptionModel {
    private Long optionId;
    private Long favoriteId;
    private String title;
    private Blob image ;
    private String text ;
}
