package com.lyc.recruit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.exception.RecruitExceptionEnum;
import com.lyc.recruit.model.dao.ArticleMapper;
import com.lyc.recruit.model.pojo.Article;
import com.lyc.recruit.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ArticleServiceImpl implements ArticleService {

    @Autowired
    ArticleMapper articleMapper;

    @Override
    public void addArticle(Article article) throws RecruitException{
        if (article == null){
            throw new RecruitException(RecruitExceptionEnum.NOT_EXISTENCED);
        }
        int insertCount = articleMapper.insertSelective(article);
        if (insertCount == 0){
            throw new RecruitException(RecruitExceptionEnum.INSERT_FAILED);
        }
    }

    @Override
    public PageInfo selectAllArticle(Integer pageNum, Integer pageSize){
        PageHelper.startPage(pageNum, pageSize);
        List<Article> articleList = articleMapper.selectAll();
        PageInfo pageInfo = new PageInfo(articleList);
        return pageInfo;
    }

    @Override
    public void updateArticle(Article article) throws  RecruitException{
        if (article == null) {
            throw new RecruitException(RecruitExceptionEnum.NOT_EXISTENCED);
        }
        int updateCount = articleMapper.updateByPrimaryKeySelective(article);
        if (updateCount == 0) {
            throw new RecruitException(RecruitExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void deleteArticle(Integer id) throws RecruitException{
        Article article = articleMapper.selectByPrimaryKey(id);
        if (article == null) {
            throw new RecruitException(RecruitExceptionEnum.NOT_EXISTENCE_ARTICLE);
        }
        int deleteCount = articleMapper.deleteByPrimaryKey(id);
        if (deleteCount == 0) {
            throw new RecruitException(RecruitExceptionEnum.DELETE_FAILED);
        }
    }

    @Override
    public Article selectArticleById(Integer id) throws RecruitException{
        Article article = articleMapper.selectByPrimaryKey(id);
        if (article == null) {
            throw new RecruitException(RecruitExceptionEnum.NOT_EXISTENCE_ARTICLE);
        }
        return article;
    }

    @Override
    public List<Article> selectArticleByTag(Integer tag){
        List<Article> articleList = articleMapper.selectAllByTag(tag);
        return articleList;
    }
}
