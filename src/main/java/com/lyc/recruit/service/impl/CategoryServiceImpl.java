package com.lyc.recruit.service.impl;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.lyc.recruit.exception.RecruitException;
import com.lyc.recruit.exception.RecruitExceptionEnum;
import com.lyc.recruit.model.dao.CategoryMapper;
import com.lyc.recruit.model.pojo.Category;
import com.lyc.recruit.model.pojo.User;
import com.lyc.recruit.model.request.CategoryReq;
import com.lyc.recruit.service.CategoryService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;

import java.util.ArrayList;
import java.util.List;

@Service
public class CategoryServiceImpl implements CategoryService {

    @Autowired
    CategoryMapper categoryMapper;

    @Override
    public List<CategoryReq> listCategoryForCustomer(Integer parentId) {
        ArrayList<CategoryReq> categoryReqList = new ArrayList<>();
        recursivelyFindCategories(categoryReqList, parentId);
        return categoryReqList;
    }

    private void recursivelyFindCategories(List<CategoryReq> categoryReqList, Integer parentId) {
        //递归获取所有子类别，并组合成为一个目录树
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(parentId);
        if (!CollectionUtils.isEmpty(categoryList)) {
            for (int i = 0; i < categoryList.size(); i++) {
                Category category =  categoryList.get(i);
                CategoryReq categoryReq = new CategoryReq();
                BeanUtils.copyProperties(category,categoryReq);
                categoryReqList.add(categoryReq);
                recursivelyFindCategories(categoryReq.getChildCategory(),categoryReq.getId());
            }
        }
    }

    private void recursivelyDeleteCategories(Integer id){
        List<Category> categoryList = categoryMapper.selectCategoriesByParentId(id);
        categoryMapper.deleteByPrimaryKey(id);
        if (!CollectionUtils.isEmpty(categoryList)){
            for (int i = 0; i < categoryList.size(); i++) {
                Category category =  categoryList.get(i);
                recursivelyDeleteCategories(category.getId());
            }
        }
    }

    @Override
    public PageInfo listCategoryForAdmin(Integer pageNum, Integer pageSize) {
        ArrayList<CategoryReq> categoryReqList = new ArrayList<>();
        PageHelper.startPage(pageNum, pageSize);
        recursivelyFindCategories(categoryReqList, 0);
        PageInfo pageInfo = new PageInfo(categoryReqList);
        return pageInfo;
    }

    @Override
    public void addCategory(Category category) throws RecruitException{
        int insertCount = categoryMapper.insertSelective(category);
        if (insertCount == 0) {
            throw new RecruitException(RecruitExceptionEnum.INSERT_FAILED);
        }
    }

    @Override
    public void updateCategory(Category category) throws RecruitException{
        int updateCount = categoryMapper.updateByPrimaryKeySelective(category);
        if (updateCount == 0) {
            throw new RecruitException(RecruitExceptionEnum.UPDATE_FAILED);
        }
    }

    @Override
    public void deleteCategoryById(Integer id){
        recursivelyDeleteCategories(id);
    }
}
