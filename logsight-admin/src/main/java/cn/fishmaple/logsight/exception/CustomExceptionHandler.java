package cn.fishmaple.logsight.exception;

import cn.fishmaple.logsight.object.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

@ControllerAdvice
public class CustomExceptionHandler {

	@ExceptionHandler(value = DefaultException.class)
	@ResponseBody
	public Result methodErrorHandle(DefaultException e){
		return new Result(e.getCode(),e.getMsg());
	}

}
