package com.brucepang.brucepang_search.controller;


import com.brucepang.brucepang_search.common.BaseResponse;
import com.brucepang.brucepang_search.common.ResultUtils;
import com.brucepang.brucepang_search.datasource.PictureDataSource;
import com.brucepang.brucepang_search.datasource.PostDataSource;
import com.brucepang.brucepang_search.datasource.UserDataSource;
import com.brucepang.brucepang_search.manager.SearchFacade;
import com.brucepang.brucepang_search.model.dto.search.SearchRequest;
import com.brucepang.brucepang_search.model.vo.SearchVO;
import com.brucepang.brucepang_search.service.PictureService;
import com.brucepang.brucepang_search.service.PostService;
import com.brucepang.brucepang_search.service.UserService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 图片接口
 * @author: BrucePang
 */
@RestController
@RequestMapping("/search")
@Slf4j
public class SearchController {


    @Resource
    private SearchFacade searchFacade;

    private final static Gson GSON = new Gson();

    @PostMapping("/all")
    public BaseResponse<SearchVO> searchAll(@RequestBody SearchRequest searchRequest, HttpServletRequest request) {
        return ResultUtils.success(searchFacade.searchAll(searchRequest, request));
    }
}

