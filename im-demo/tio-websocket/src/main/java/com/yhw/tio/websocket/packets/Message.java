package com.yhw.tio.websocket.packets;

import java.io.Serializable;

public class Message implements Serializable {

    private static final long serialVersionUID = -4112956041869795095L;

    /**
     * 编码指令，通过编码指令找到具体的Handler
     */
    private int command;

    /**
     * 具体的返回结果
     */
    private Object result;

    public Message(){}

    public Message(int command,Object result){
        this.command = command;
        this.result = result;
    }



    public Object getResult() {
        return result;
    }

    public void setResult(Object result) {
        this.result = result;
    }

    public int getCommand() {
        return command;
    }

    public void setCommand(int command) {
        this.command = command;
    }


    public static class Builder{
        private int command;

        private Object result;

        private Builder(){}

        public Builder setCommand(int command){
            this.command = command;
            return this;
        }
        public Builder setResult(Object result){
            this.result = result;
            return this;
        }

        public Message builder(){
            return new Message(this.command,this.result);
        }

        public static Builder newBuiler(){
            return new Builder();
        }

    }
}
