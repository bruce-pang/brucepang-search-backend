package com.brucepang.brucepang_search.model.dto.picture;

import com.brucepang.brucepang_search.common.PageRequest;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * @author BrucePang
 */
@Data
@EqualsAndHashCode(callSuper = true)
public class PictureQueryRequest extends PageRequest implements java.io.Serializable {
    /**
     * 搜索词
     */
    private String searchText;

    private static final long serialVersionUID = 1L;

}
