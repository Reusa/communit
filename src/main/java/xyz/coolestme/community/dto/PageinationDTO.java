package xyz.coolestme.community.dto;

import xyz.coolestme.community.model.Question;

import java.util.ArrayList;
import java.util.List;

public class PageinationDTO<T> {
    private List<T> data;
    private boolean hasPrevious;
    private boolean hasFirstPage;
    private boolean showNest;
    private boolean showEndPage;
    private Integer currentPage;
    private List<Integer> pages = new ArrayList<>();
    private Integer totalPage;

    public Integer getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Integer totalPage) {
        this.totalPage = totalPage;
    }

    public List<Integer> getPages() {
        return pages;
    }

    public void setPages(List<Integer> pages) {
        this.pages = pages;
    }

    public boolean isShowNest() {
        return showNest;
    }

    public void setShowNest(boolean showNest) {
        this.showNest = showNest;
    }

    public boolean isShowEndPage() {
        return showEndPage;
    }

    public void setShowEndPage(boolean showEndPage) {
        this.showEndPage = showEndPage;
    }

    public Integer getCurrentPage() {
        return currentPage;
    }

    public void setCurrentPage(Integer currentPage) {
        this.currentPage = currentPage;
    }

    public boolean isHasFirstPage() {
        return hasFirstPage;
    }

    public void setHasFirstPage(boolean hasFirstPage) {
        this.hasFirstPage = hasFirstPage;
    }

    public boolean isHasPrevious() {
        return hasPrevious;
    }

    public void setHasPrevious(boolean hasPrevious) {
        this.hasPrevious = hasPrevious;
    }

    public List<T> getData() {
        return data;
    }

    public void setData(List<T> data) {
        this.data = data;
    }

    public void setPageination(Integer totalCount, Integer page, Integer size) {


        if (totalCount % size == 0){
            totalPage = totalCount / size;
        }else {
            totalPage = totalCount / size + 1;
        }



        this.currentPage = page;
        pages.add(page);
        for (int i = 1;i<=3;i++){
            if (page - i > 0){
                pages.add(0,page - i);
            }
            if (page + i <= totalPage){
                pages.add(page + i);
            }
        }

        //展示上一页
        if (page == 1){
            hasPrevious = false;
        }else {
            hasPrevious = true;
//            showEndPage = true;
        }

        //展示下一页
        if (page == totalPage){
            showNest = false;
        }else {
            showNest = true;
        }

        //展示第一页
        if (pages.contains(1)){
            hasFirstPage = false;
        }else {
            hasFirstPage = true;
        }

        //是否展示最后一页
        if (pages.contains(totalCount)){
            showEndPage = false;
        }else {
            showEndPage = true;
        }
    }
}
