package com.lyc.recruit.model.request;

import com.lyc.recruit.model.pojo.Category;

import java.util.Date;

public class PositionCategoryReq {
    private Integer id;

    private String title;

    private String subtitle;

    private String quantity;

    private String degree;

    private String experience;

    private String city;

    private String address;

    private Integer salaryUp;

    private Integer salaryDown;

    private String describe;

    private String requirements;

    private Integer hits;

    private Date release;

    private Integer categoryId;

    private Integer resourceManId;

    private Category category;

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title == null ? null : title.trim();
    }

    public String getSubtitle() {
        return subtitle;
    }

    public void setSubtitle(String subtitle) {
        this.subtitle = subtitle == null ? null : subtitle.trim();
    }

    public String getQuantity() {
        return quantity;
    }

    public void setQuantity(String quantity) {
        this.quantity = quantity == null ? null : quantity.trim();
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree == null ? null : degree.trim();
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience == null ? null : experience.trim();
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city == null ? null : city.trim();
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address == null ? null : address.trim();
    }

    public Integer getSalaryUp() {
        return salaryUp;
    }

    public void setSalaryUp(Integer salaryUp) {
        this.salaryUp = salaryUp;
    }

    public Integer getSalaryDown() {
        return salaryDown;
    }

    public void setSalaryDown(Integer salaryDown) {
        this.salaryDown = salaryDown;
    }

    public String getDescribe() {
        return describe;
    }

    public void setDescribe(String describe) {
        this.describe = describe == null ? null : describe.trim();
    }

    public String getRequirements() {
        return requirements;
    }

    public void setRequirements(String requirements) {
        this.requirements = requirements == null ? null : requirements.trim();
    }

    public Integer getHits() {
        return hits;
    }

    public void setHits(Integer hits) {
        this.hits = hits;
    }

    public Date getRelease() {
        return release;
    }

    public void setRelease(Date release) {
        this.release = release;
    }

    public Integer getCategoryId() {
        return categoryId;
    }

    public void setCategoryId(Integer categoryId) {
        this.categoryId = categoryId;
    }

    public Integer getResourceManId() {
        return resourceManId;
    }

    public void setResourceManId(Integer resourceManId) {
        this.resourceManId = resourceManId;
    }
}