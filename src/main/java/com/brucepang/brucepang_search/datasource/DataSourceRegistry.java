package com.brucepang.brucepang_search.datasource;

import com.brucepang.brucepang_search.model.enums.SearchTypeEnum;
import org.apache.poi.ss.formula.functions.T;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import javax.annotation.Resource;
import java.util.HashMap;
import java.util.Map;

/**
 * 采用注册模式，根据 type 获取对应的数据源
 * @author BrucePang
 */
@Component
public class DataSourceRegistry {
    @Resource
    private PostDataSource postDataSource;

    @Resource
    private UserDataSource userDataSource;

    @Resource
    private PictureDataSource pictureDataSource;

    private Map<String, DataSource<T>> typeDataSourceMap = null;

    @PostConstruct // 在 Spring 容器创建 bean 之后执行初始化操作。它标识的方法会在构造函数执行完成后，依赖注入完成之前被调用。
    public void init() {
        typeDataSourceMap = new HashMap() {{
            put(SearchTypeEnum.POST.getValue(), postDataSource);
            put(SearchTypeEnum.USER.getValue(), userDataSource);
            put(SearchTypeEnum.PICTURE.getValue(), pictureDataSource);
        }};
    }


    public DataSource<T> getDataSourceByType(String type) {
        if (typeDataSourceMap == null) { // 防止项目启动的时候被别人调用
            return null;
        }
        return typeDataSourceMap.get(type);
    }
}
