package com.lyc.recruit.controller;

import com.github.pagehelper.PageInfo;
import com.lyc.recruit.common.ApiRestResponse;
import com.lyc.recruit.exception.RecruitExceptionEnum;
import com.lyc.recruit.model.pojo.Article;
import com.lyc.recruit.service.ArticleService;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class ArticleController {

    @Autowired
    ArticleService articleService;

    @PostMapping("/addArticle")
    @ResponseBody
    public ApiRestResponse addArticle(@RequestBody Article article) {
        //System.out.println(article.getDescription());
        Document doc = Jsoup.parse(article.getDescription());
        Elements elements = doc.select("img");
        System.out.println(elements.size());
        if (elements.size() == 0) {
            return ApiRestResponse.error(RecruitExceptionEnum.NOT_EXISTENCE_COVER.getCode(), RecruitExceptionEnum.NOT_EXISTENCE_COVER.getMsg());
        } else {
            String cover = elements.first().attr("src");
            article.setCover(cover);
            articleService.addArticle(article);
            return ApiRestResponse.success();
        }
    }

    @PostMapping("/selectAllArticle")
    @ResponseBody
    public ApiRestResponse selectAllArticle(@RequestParam("pageNum")Integer pageNum, @RequestParam("pageSize") Integer pageSize){
        PageInfo pageInfo = articleService.selectAllArticle(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @PostMapping("/updateArticle")
    @ResponseBody
    public ApiRestResponse updateArticle(@RequestBody Article article){
        if (article == null) {
            return ApiRestResponse.error(RecruitExceptionEnum.NOT_EXISTENCED.getCode(),RecruitExceptionEnum.NOT_EXISTENCED.getMsg());
        }else {
            Document doc = Jsoup.parse(article.getDescription());
            Elements elements = doc.select("img");
            System.out.println(elements.size());
            if (elements.size() == 0) {
                return ApiRestResponse.error(RecruitExceptionEnum.NOT_EXISTENCE_COVER.getCode(), RecruitExceptionEnum.NOT_EXISTENCE_COVER.getMsg());
            } else {
                String cover = elements.first().attr("src");
                article.setCover(cover);
                articleService.updateArticle(article);
                return ApiRestResponse.success();
            }
        }
    }

    @PostMapping("/deleteArticle")
    @ResponseBody
    public ApiRestResponse deleteArticle(@RequestParam("id") Integer id){
        articleService.deleteArticle(id);
        return ApiRestResponse.success();
    }

    @PostMapping("/selectArticleById")
    @ResponseBody
    public ApiRestResponse selectArticleById(@RequestParam("id") Integer id){
        Article article = articleService.selectArticleById(id);
        return ApiRestResponse.success(article);
    }

    @PostMapping("/selectArticleByTag")
    @ResponseBody
    public ApiRestResponse selectArticleByTag(@RequestParam("tag") Integer tag){
        List<Article> articleList = articleService.selectArticleByTag(tag);
        return ApiRestResponse.success(articleList);
    }

}
