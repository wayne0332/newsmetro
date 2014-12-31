package com.newsmetro.util.handler;

import com.newsmetro.util.callback.ScriptOutLineCallback;

import java.io.BufferedWriter;
import java.io.IOException;
import java.io.OutputStream;
import java.io.OutputStreamWriter;

/**
 * Created by lifeng.gao on 2014/12/26.
 */
public abstract class RunScriptHandler implements Runnable{
    protected String outStr = null;
    private OutputStream os;
    private ScriptOutLineCallback callback;
    public void run() {
        BufferedWriter bw = new BufferedWriter(new OutputStreamWriter(os));
        try {
            String line = null;
            while ((line = bw.toString()) != null) {
                handleLine(line,outStr);
//                    if(line.startsWith("[")){
//                        List list = GsonUtil.fromJson(line,java.util.List.class);
//                        if(list.size()>0){
//                            this.outStr = line;
//                        }
//                    }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        finally{
            try {
                os.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    protected abstract void handleLine(String line,String outStr);

    public String getOutStr() {
        return outStr;
    }

    public void setOutputStream(OutputStream os) {
        this.os = os;
    }
}
