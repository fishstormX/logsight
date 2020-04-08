package cn.fishmaple.logsight.object;

public class Result<T> {
    private Integer code;
    private String msg;
    private T data;
    private String META;

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public String getMETA() {
        return META;
    }

    public void setMETA(String META) {
        this.META = META;
    }

    public Result(T data) {
        this.code = 0;
        this.data = data;
        this.msg = "SUCCESS";
    }
}
