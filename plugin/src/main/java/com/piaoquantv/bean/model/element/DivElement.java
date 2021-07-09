package com.piaoquantv.bean.model.element;

import java.util.ArrayList;
import java.util.List;

/**
 * Create by nieqi on 2021/7/9
 */
public class DivElement extends BaseElement {

    public DivElement() {
        this.tag = "div";
    }

    public List<Filed> fields = new ArrayList<>();

}
