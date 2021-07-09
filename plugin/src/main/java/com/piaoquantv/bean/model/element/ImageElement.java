package com.piaoquantv.bean.model.element;

/**
 * Create by nieqi on 2021/7/9
 */
public class ImageElement extends BaseElement {

    public ImageElement() {
        this.tag = "img";
    }

    public String img_key = "";

    public TagText alt = new TagText("点击查看大图");
}
