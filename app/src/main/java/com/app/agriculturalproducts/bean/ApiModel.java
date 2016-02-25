package com.app.agriculturalproducts.bean;

import java.util.ArrayList;

/**
 * Created by ALPHONSO on 2016/2/25.
 */
public class ApiModel<T> extends BaseModel {
    /**
     * 不变的部分：写在API基类中
     */
    private String return_code;
    private String return_msg;
    /**
     * 变化的部分：使用泛型，数据类型的确认延迟到子类里。
     */
    protected ArrayList<T> data;

    public String getReturn_code() {
        return return_code;
    }

    public void setReturn_code(String return_code) {
        this.return_code = return_code;
    }

    public String getReturn_msg() {
        return return_msg;
    }

    public void setReturn_msg(String return_msg) {
        this.return_msg = return_msg;
    }
    // getter setter toString 等方法已删减掉...
}
