package com.yhw.netty.websocket.context;

import com.yhw.netty.websocket.process.CmdProcess;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author yhw
 */
public class ProcessManager {

    private Map<Integer,CmdProcess> cmdRepository = new HashMap<>();

    public static ProcessManager getInstance(){
        return Holder.manager;
    }

    public CmdProcess getCmdProcess(int code){
        return cmdRepository.get(code);
    }


    /**
     * 批量添加
     * @param processes
     */
    public void addCmdProcesss(List<CmdProcess> processes){
        if(processes != null && !processes.isEmpty()){
            for (CmdProcess process : processes) {
                cmdRepository.put(process.getCmdCode(),process);
            }
        }
    }

    public void addCmdProcess(CmdProcess process){
        cmdRepository.put(process.getCmdCode(),process);
    }

    static class Holder{
        private static ProcessManager manager = new ProcessManager();
    }
}
