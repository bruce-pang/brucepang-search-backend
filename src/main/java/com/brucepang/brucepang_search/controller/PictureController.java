package com.brucepang.brucepang_search.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.brucepang.brucepang_search.common.BaseResponse;
import com.brucepang.brucepang_search.common.ErrorCode;
import com.brucepang.brucepang_search.common.ResultUtils;
import com.brucepang.brucepang_search.exception.ThrowUtils;
import com.brucepang.brucepang_search.model.dto.picture.PictureQueryRequest;
import com.brucepang.brucepang_search.model.entity.Picture;
import com.brucepang.brucepang_search.service.PictureService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 图片接口
 */
@RestController
@RequestMapping("/picture")
@Slf4j
public class PictureController {
    @Resource
    private PictureService pictureService;

    private final static Gson GSON = new Gson();

    /**
     * 分页获取列表（封装类）
     */
    @PostMapping("/list/page/vo")
    public BaseResponse<Page<Picture>> listPictureByPage(@RequestBody PictureQueryRequest pictureQueryRequest,
                                                         HttpServletRequest request) {
        long current = pictureQueryRequest.getCurrent(); // 当前页
        long size = pictureQueryRequest.getPageSize(); // 每页条数
        // 限制爬虫
        ThrowUtils.throwIf(size > 20, ErrorCode.PARAMS_ERROR);
        Page<Picture> picturePage = pictureService.searchPicture(pictureQueryRequest.getSearchText(), current, size);
        return ResultUtils.success(picturePage);
    }
}

