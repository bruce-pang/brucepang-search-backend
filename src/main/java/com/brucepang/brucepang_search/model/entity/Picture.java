package com.brucepang.brucepang_search.model.entity;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * @author BrucePang
 */
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Picture {
    /**
     * 图片标题
     */
    private String title;
    /**
     * 图片地址
     */
    private String murl;
}
