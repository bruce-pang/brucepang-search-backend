package com.brucepang.brucepang_search.model.vo;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.brucepang.brucepang_search.model.entity.Picture;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * @author BrucePang
 */
@Data
public class SearchVO implements Serializable {
    private static final long serialVersionUID = 1L;

    private List<UserVO> userList;

    private List<PostVO> postList;

    private List<Picture> pictureList;

    private List<?> dataList;
}
