package com.xiaobai.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangc on 2016/6/8.
 */
public class FindDetailsDto implements Serializable {


    public String Context;
    public String Type;
    public String CreateTime;
    public String Label;
    public int Id;
    public friendInfoDto friendInfo;
    public int UserId;
    public List<String> url;

}
