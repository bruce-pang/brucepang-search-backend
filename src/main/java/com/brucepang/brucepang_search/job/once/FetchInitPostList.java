package com.brucepang.brucepang_search.job.once;

import cn.hutool.http.HttpRequest;
import cn.hutool.json.JSONArray;
import cn.hutool.json.JSONObject;
import cn.hutool.json.JSONUtil;
import com.brucepang.brucepang_search.model.entity.Post;
import com.brucepang.brucepang_search.service.PostService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.CommandLineRunner;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

/**
 * 获取初始帖子到表
 * @author BrucePang
 */
// todo 取消@Component的注释开启任务
// @Component // 加上此注解的话，代表每次启动springboot项目时，都会执行一次本类中的run方法
@Slf4j
public class FetchInitPostList implements CommandLineRunner {

    @Resource
    private PostService postService;



    @Override
    public void run(String... args) {

        // 1.获取数据
        String json = "{\n" +
                "    \"current\": 2,\n" +
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
        // 2.json转换为对象
        Map<String, Object> map = JSONUtil.toBean(result, Map.class);
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
                        post.setUserId(1693863681682583553L);
                        return post;
                    })
                    .collect(Collectors.toList());
//            System.out.println(postList);
            // 3.保存到数据库
            boolean b = postService.saveBatch(postList);// 批量插入
            if(b){
                log.info("获取初始化帖子列表 success, total {}", postList.size());
            }else {
                log.error("FetchInitPostList failed");
            }
        }
    }
}
