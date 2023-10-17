package com.bjw.shopping;

public class Shopping {
    private int idx;
    private String article;
    private String category;
    private int price;
    private String created;

    public Shopping() {
    }

    public Shopping(int idx, String article, String category, int price, String created) {
        this.idx = idx;
        this.article = article;
        this.category = category;
        this.price = price;
        this.created = created;
    }

    public int getIdx() {
        return idx;
    }

    public String getArticle() {
        return article;
    }

    public String getCategory() {
        return category;
    }

    public int getPrice() {
        return price;
    }

    public String getCreated() {
        return created;
    }

    public void setIdx(int idx) {
        this.idx = idx;
    }

    public void setArticle(String article) {
        this.article = article;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public void setPrice(int price) {
        this.price = price;
    }

    public void setCreated(String created) {
        this.created = created;
    }
}
