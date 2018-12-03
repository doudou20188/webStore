package utils;

import bean.Category;

import java.util.ArrayList;
import java.util.List;

/**
 * @Auther: YangTao
 * @Date: 2018/11/10 0010
 */
public class PageInfo<T> {
    //分页工具类
    List<T> pageList;
    /**总页数
     * 记录当前页
     * 前一页
     * 下一页
     */
    int totalPageNumber;
    int currentPageNumber;
    int previousPageNumber;
    int nextPageNumber;
    /**
     * 总记录数
     */
    int totalRecordNumber;


    public List<T> getPageList() {
        return pageList;
    }

    public void setPageList(List<T> pageList) {
        this.pageList = pageList;
    }

    public int getTotalPageNumber() {
        return totalPageNumber;
    }

    public void setTotalPageNumber(int totalPageNumber) {
        this.totalPageNumber = totalPageNumber;
    }

    public int getCurrentPageNumber() {
        return currentPageNumber;
    }

    public void setCurrentPageNumber(int currentPageNumber) {
        this.currentPageNumber = currentPageNumber;
    }

    public int getPreviousPageNumber() {
        return previousPageNumber;
    }

    public void setPreviousPageNumber(int previousPageNumber) {
        this.previousPageNumber = previousPageNumber;
    }

    public int getNextPageNumber() {
        return nextPageNumber;
    }

    public void setNextPageNumber(int nextPageNumber) {
        this.nextPageNumber = nextPageNumber;
    }

    public int getTotalRecordNumber() {
        return totalRecordNumber;
    }

    public void setTotalRecordNumber(int totalRecordNumber) {
        this.totalRecordNumber = totalRecordNumber;
    }

    @Override
    public String toString() {
        return "PageInfo{" +
                "pageList=" + pageList +
                ", totalPageNumber=" + totalPageNumber +
                ", currentPageNumber=" + currentPageNumber +
                ", previousPageNumber=" + previousPageNumber +
                ", nextPageNumber=" + nextPageNumber +
                ", totalRecordNumber=" + totalRecordNumber +
                '}';
    }
}
