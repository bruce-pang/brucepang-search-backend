package com.brucepang.brucepang_search.datasource;


import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.brucepang.brucepang_search.common.ErrorCode;
import com.brucepang.brucepang_search.exception.BusinessException;
import com.brucepang.brucepang_search.model.dto.post.PostQueryRequest;
import com.brucepang.brucepang_search.model.entity.Post;
import com.brucepang.brucepang_search.model.vo.PostVO;
import com.brucepang.brucepang_search.service.PostService;
import com.google.gson.Gson;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;
import org.springframework.web.context.request.RequestAttributes;
import org.springframework.web.context.request.RequestContextHolder;
import org.springframework.web.context.request.ServletRequestAttributes;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * 帖子搜索服务实现
 * @Author: BrucePang
 */
@Component
@Slf4j
public class PostDataSource implements DataSource<PostVO> {

    private final static Gson GSON = new Gson();

    @Resource
    private PostService postService;


    // 适配器模式
    @Override
    public Page<PostVO> doSearch(String searchText, long pageNum, long pageSize) {
        // 非空校验
        if (pageNum < 1 || pageSize < 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 构建查询条件
        PostQueryRequest postQueryRequest = new PostQueryRequest();
        postQueryRequest.setSearchText(searchText == null ? "" : searchText);
        postQueryRequest.setCurrent(pageNum);
        postQueryRequest.setPageSize(pageSize);
        // 此处的request不一定是httpServletRequest，也可能是其他的系统的request
        ServletRequestAttributes servletRequestAttributes = (ServletRequestAttributes) RequestContextHolder.getRequestAttributes();
        HttpServletRequest request = servletRequestAttributes.getRequest();
       // return postService.listPostVOByPage(postQueryRequest, request);

        Page<Post> postPage = postService.searchFromEs(postQueryRequest);
        // 转换为需要的结果集
        return postService.getPostVOPage(postPage, request);
    }
}




