package com.brucepang.brucepang_search.service.impl;

import cn.hutool.json.JSONUtil;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.brucepang.brucepang_search.model.entity.Picture;
import com.brucepang.brucepang_search.service.PictureService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * @author BrucePang
 */
@Service
public class PictureServiceImpl implements PictureService {
    @Override
    public Page<Picture> searchPicture(String searchText, long pageNum, long pageSize) {
        long current = (pageNum - 1) * pageSize;
//        String url = "https://www.bing.com/images/search?q=" + searchText + "&first=" + current;
        String url = String.format("https://www.bing.com/images/search?q=%s&first=%s",searchText,current);
        Document doc = null;
        try {
            doc = Jsoup.connect(url).get();
        } catch (IOException e) {
            e.printStackTrace();
        }
        Elements elements = doc.select(".iuscp.isv");
        List<Picture> pictureList = elements
                .stream()
                // .skip(current)  // 跳过前面的数据，达到分页效果
                .limit(pageSize)  // 限制每页返回的数据量
                .map(element -> {
                    // 获取图片地址(murl)
                    String m = element.select(".iusc").get(0).attr("m");
                    // 2.json转换为对象
                    Map<String, Object> map = JSONUtil.toBean(m, Map.class);
                    String murl = (String) map.get("murl");
                    // 获取标题
                    String title = element.select(".inflnk").get(0).attr("aria-label");
                    Picture picture = Picture.builder().murl(murl).title(title).build();
                    return picture;
                }).collect(Collectors.toList());
        Page<Picture> picturePage = new Page<>(pageNum, pageSize);
        picturePage.setRecords(pictureList);
        return picturePage;
    }
}
