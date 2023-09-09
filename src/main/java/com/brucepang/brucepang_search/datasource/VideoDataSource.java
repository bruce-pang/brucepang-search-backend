package com.brucepang.brucepang_search.datasource;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import org.springframework.stereotype.Component;

/**
 * @author BrucePang
 */
@Component
public class VideoDataSource implements DataSource {
    @Override
    public Page doSearch(String searchText, long pageNum, long pageSize) {
        return null;
    }
}
