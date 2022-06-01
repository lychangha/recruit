package com.lyc.recruit.service;

import com.github.pagehelper.PageInfo;
import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.model.pojo.Category;
import com.lyc.recruit.model.request.CategoryReq;

import java.util.List;

public interface CategoryService {

    List<CategoryReq> listCategoryForCustomer(Integer parentId);

    PageInfo listCategoryForAdmin(Integer pageNum, Integer pageSize);

    void addCategory(Category category);

    void updateCategory(Category category) throws RecruitException;

    void deleteCategoryById(Integer id);
}
