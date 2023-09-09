package com.brucepang.brucepang_search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.brucepang.brucepang_search.common.ErrorCode;
import com.brucepang.brucepang_search.exception.BusinessException;
import com.brucepang.brucepang_search.model.dto.user.UserQueryRequest;
import com.brucepang.brucepang_search.model.vo.UserVO;
import com.brucepang.brucepang_search.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * 用户搜索服务实现
 *
 * @author: BrucePang
 */
@Component
@Slf4j
public class UserDataSource implements DataSource<UserVO> {

    @Resource
    private UserService userService;


    @Override
    public Page<UserVO> doSearch(String searchText, long pageNum, long pageSize) {
        // 非空校验
        if (pageNum < 1 || pageSize < 1) {
            throw new BusinessException(ErrorCode.PARAMS_ERROR);
        }
        // 构建查询条件
        UserQueryRequest userQueryRequest = new UserQueryRequest();
        userQueryRequest.setUserName(searchText == null ? "" : searchText);
        userQueryRequest.setCurrent(pageNum);
        userQueryRequest.setPageSize(pageSize);
        return userService.listUserVOByPage(userQueryRequest);
    }
}
