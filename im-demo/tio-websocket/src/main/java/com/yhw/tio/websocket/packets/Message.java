package com.yhw.tio.websocket.packets;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = -4112956041869795095L;

    /**
     * 编码指令，通过编码指令找到具体的Handler
     */
    private int code;

    /**
     * 具体的返回结果
     */
    private Object result;

    public Message(){}

    public Message(int code,Object result){
        this.code = code;
        this.result = result;
    }

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }


    public static class Builder{
        private int code;

        private Object result;

        private Builder(){}

        public Builder setCode(int code){
            this.code = code;
            return this;
        }
        public Builder setCode(Object result){
            this.result = result;
            return this;
        }

        public Message builder(){
            return new Message(this.code,this.result);
        }

        public static Builder newBuiler(){
            return new Builder();
        }

    }
}
