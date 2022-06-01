package com.lyc.recruit.service;

import com.github.pagehelper.PageInfo;
import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.model.pojo.Article;

import java.util.List;

public interface ArticleService {

    void addArticle(Article article) throws RecruitException;

    PageInfo selectAllArticle(Integer pageNum, Integer pageSize);

    void updateArticle(Article article) throws  RecruitException;

    void deleteArticle(Integer id) throws RecruitException;

    Article selectArticleById(Integer id) throws RecruitException;

    List<Article> selectArticleByTag(Integer tag);
}
