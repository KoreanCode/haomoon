package com.haomoon.favorite;

import lombok.*;

import java.sql.Timestamp;

/**
 * Model
 * TODO 초기 모델 설계 - 추후 변수 추가 가능
 * 
 */
@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Builder
@ToString
public class FavoriteModel {
    private Long favoriteId;
    private String title;
    private String category;
    private Long userId;
    private int totalRound; // 최대 라운드 입력
    private Timestamp createdAt;
}
