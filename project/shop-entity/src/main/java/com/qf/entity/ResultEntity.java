package com.qf.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ResultEntity<T> {
    private  static  final  String SUCCESS = "success";
    private  static  final  String ERROR = "error";
    private  String status; // 表示这次请求是成功还是失败

    private  String msg; //失败的原因

    private  T data;//返回的数据
//只返回成功的状态
    public  static <T> ResultEntity<T> success(){
        return new ResultEntity(SUCCESS,null,null);
    }
    //带参数的。
    public  static <T> ResultEntity<T> success(T t){
        return new ResultEntity(SUCCESS,null,t);
    }

    //返回错误
    public  static <T> ResultEntity<T> error(){
        return new ResultEntity(ERROR,null,null);
    }
    public  static <T> ResultEntity<T> error(T t){
        return new ResultEntity(ERROR,null,t);
    }


    public  static  ResultEntity responseClient(boolean flag){
        if(flag) return ResultEntity.success();
        else return ResultEntity.error("添加用户失败");
    }


}
