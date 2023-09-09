package com.brucepang.brucepang_search.model.dto.search;

import com.brucepang.brucepang_search.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BrucePang
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class SearchRequest extends PageRequest implements java.io.Serializable {
    /**
     * 搜索词
     */
    private String searchText;
    /**
     * 搜索类型
     */
    private String type;

    private static final long serialVersionUID = 1L;

}
