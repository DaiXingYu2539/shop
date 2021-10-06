package com.qf.handler;


import com.qf.entity.ResultEntity;
import com.qf.entity.ShopException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

@ControllerAdvice  //表示这是一个全局异常
@Slf4j
public class ShopExceptionHandle {
    //系统异常
    @ExceptionHandler(Exception.class)
    public ResultEntity systemException( Exception e){
        log.error("系统异常",e);
        return ResultEntity.error("系统正在维护，请联系管理员...");
    }

//业务异常
    @ExceptionHandler(ShopException.class)
    public ResultEntity businessException(ShopException e){
        log.error("业务异常",e);
        return ResultEntity.error("系统正在维护，请联系管理员..."+e.getMsg());
    }
}
