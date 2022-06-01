package com.lyc.recruit.model.request;

public class Conditions {
    private String title;
    private String city;
    private String experience;
    private String degree;
    private String type;
    private Integer employeesUp;
    private Integer employeesDown;
    private Integer salaryDown;
    private Integer salaryUp;
    private String rank;
    private Integer currentPage;
    private Integer pageSize;

    public Conditions() {
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getCity() {
        return city;
    }

    public void setCity(String city) {
        this.city = city;
    }

    public String getExperience() {
        return experience;
    }

    public void setExperience(String experience) {
        this.experience = experience;
    }

    public String getDegree() {
        return degree;
    }

    public void setDegree(String degree) {
        this.degree = degree;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public Integer getEmployeesUp() {
        return employeesUp;
    }

    public void setEmployeesUp(Integer employeesUp) {
        this.employeesUp = employeesUp;
    }

    public Integer getEmployeesDown() {
        return employeesDown;
    }

    public void setEmployeesDown(Integer employeesDown) {
        this.employeesDown = employeesDown;
    }

    public Integer getSalaryDown() {
        return salaryDown;
    }

    public void setSalaryDown(Integer salaryDown) {
        this.salaryDown = salaryDown;
    }

    public Integer getSalaryUp() {
        return salaryUp;
    }

    public void setSalaryUp(Integer salaryUp) {
        this.salaryUp = salaryUp;
    }

    public String getRank() {
        return rank;
    }

    public void setRank(String rank) {
        this.rank = rank;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public Integer getPageSize() {
        return pageSize;
    }

    public void setPageSize(Integer pageSize) {
        this.pageSize = pageSize;
    }
}
