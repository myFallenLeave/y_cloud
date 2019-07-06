package com.yhw.tio.websocket.command;

import com.yhw.tio.websocket.cmd.CmdProcess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

public final class CommandManager {

    private  Map<Integer,CmdProcess> cmdRepositry = new HashMap<>();

    public static CommandManager getInstance(){
        return Holder.manager;
    }

    public CmdProcess getCmdProcess(int code){
        return cmdRepositry.get(code);
    }

    public CmdProcess getCmdProcess(Command command){
        return cmdRepositry.get(command.getCode());
    }

    /**
     * 批量添加
     * @param processes
     */
    public void addCmdProcesss(List<CmdProcess> processes){
        if(processes != null && !processes.isEmpty()){
            for (CmdProcess process : processes) {
                cmdRepositry.put(process.getCommand().getCode(),process);
            }
        }
    }

    public void addCmdProcess(CmdProcess process){
        cmdRepositry.put(process.getCommand().getCode(),process);
    }

    static class Holder{
        private static CommandManager manager = new CommandManager();
    }

}
