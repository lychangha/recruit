package com.lyc.recruit.controller;

import com.github.pagehelper.PageInfo;
import com.lyc.recruit.common.ApiRestResponse;
import com.lyc.recruit.model.pojo.Category;
import com.lyc.recruit.model.request.CategoryReq;
import com.lyc.recruit.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.List;

@Controller
public class CategoryController {

    @Autowired
    CategoryService categoryService;

    @PostMapping("/listCategory")
    @ResponseBody
    public ApiRestResponse listCategory() {
        List<CategoryReq> categoryReqList = categoryService.listCategoryForCustomer(0);
        return ApiRestResponse.success(categoryReqList);
    }

    @PostMapping("/admin/listCategoryForAdmin")
    @ResponseBody
    public ApiRestResponse listCategoryForAdmin(@RequestParam("pageNum") Integer pageNum, @RequestParam("pageSize") Integer pageSize) {
        PageInfo pageInfo = categoryService.listCategoryForAdmin(pageNum, pageSize);
        return ApiRestResponse.success(pageInfo);
    }

    @PostMapping("/admin/addCategory")
    @ResponseBody
    public ApiRestResponse addCategory(@RequestBody Category category) {
        categoryService.addCategory(category);
        return ApiRestResponse.success();
    }

    @PostMapping("/admin/updateCategory")
    @ResponseBody
    public ApiRestResponse updateCategory(@RequestBody Category category){
        categoryService.updateCategory(category);
        return ApiRestResponse.success();
    }

    @PostMapping("/admin/deleteCategory")
    @ResponseBody
    public ApiRestResponse deleteCategory(@RequestParam Integer id){
        categoryService.deleteCategoryById(id);
        return ApiRestResponse.success();
    }
}
