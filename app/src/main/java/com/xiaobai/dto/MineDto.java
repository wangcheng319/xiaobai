package com.xiaobai.dto;

import java.io.Serializable;
import java.util.List;

/**
 * Created by wangc on 2016/6/13.
 */
public class MineDto implements Serializable {

    public int recordId;
    public int likeCount;
    public int UserId;
    public int commentCount;

    public String Context;
    public String CreateTime;
    public String Label;
    public List<String> url;
}
