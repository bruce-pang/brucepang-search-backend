package com.brucepang.brucepang_search;

import java.io.IOException;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.brucepang.brucepang_search.model.entity.Picture;
import com.brucepang.brucepang_search.model.entity.Post;
import com.brucepang.brucepang_search.service.PostService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static cn.hutool.core.lang.Console.log;

/**
 * @author BrucePang
 */
@SpringBootTest
public class CrawlerTest {

    @Resource
    private PostService postService;

    @Test
    void testFetchPicture() throws IOException {
        int current = 1;
        String url = "https://www.bing.com/images/search?q=%e5%b0%8f%e9%bb%91%e5%ad%90&form=HDRSC3&first=" + current;
        Document doc = Jsoup.connect(url).get();
        Elements elements = doc.select(".iuscp.isv");
        List<Picture> pictureList = elements.stream().map(element -> {
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
        System.out.println(pictureList);
    }

    @Test
    void testFetchPassage() {

        // 1.获取数据
        String json = "{\n" +
                "    \"current\": 1,\n" +
                "    \"pageSize\": 8,\n" +
                "    \"sortField\": \"createTime\",\n" +
                "    \"sortOrder\": \"descend\",\n" +
                "    \"category\": \"文章\",\n" +
                "    \"reviewStatus\": 1\n" +
                "}";
        String url = "https://www.code-nav.cn/api/post/search/page/vo";
        String result = HttpRequest.post(url)
                .body(json)
                .execute().body();
//        System.out.println(result);
        // 2.json转换为对象
        Map<String, Object> map = JSONUtil.toBean(result, Map.class);
//        System.out.println(map);
        if (map != null && map.get("code").equals(0)) {
            JSONObject data = (JSONObject) map.get("data");
            JSONArray records = (JSONArray) data.get("records");
            List<Post> postList = records.toList(JSONObject.class) // JSONArray转换为List<JSONObject>
                    .stream()
                    .map(jsonObj -> {
                        Post post = new Post();
                        // 注意，PO使用setter转换放入的时候，如果是null，需要转换为非null，否则会报错
                        post.setTitle(jsonObj.getStr("title") == null ? "" : jsonObj.getStr("title"));
                        post.setContent(jsonObj.getStr("content") == null ? "" : jsonObj.getStr("content"));
                        JSONArray tags = (JSONArray) jsonObj.get("tags");
                        List<String> tagsList = tags.toList(String.class);
                        post.setTags(JSONUtil.toJsonStr(tagsList));
                        post.setUserId(1L);
                        return post;
                    })
                    .collect(Collectors.toList());
//            System.out.println(postList);
            // 3.保存到数据库
            boolean b = postService.saveBatch(postList);// 批量插入
            Assertions.assertTrue(b);
        }
    }
}
