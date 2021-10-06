package com.dxy.server;

import com.dangdang.ddframe.job.api.ShardingContext;
import com.dangdang.ddframe.job.api.simple.SimpleJob;
import com.dxy.model.FileCustom;

import java.util.ArrayList;
import java.util.List;

public class FileBackupJob implements SimpleJob {
    private static int  num = 0;

    //每次任务执行要备份的文件的数量
    private final int FETCH_SIZE = 1;

    //文件列表（模拟）
    public static List<FileCustom> files = new ArrayList<>();


    // 代码逻辑
    public void execute(ShardingContext shardingContext) {
        System.out.println(shardingContext.getShardingItem());
        System.out.println(num++);
        List<FileCustom> fileCustoms = fetchUnBackUpFiles(FETCH_SIZE);
        backUpFiles(fileCustoms);

    }

    private void backUpFiles(List<FileCustom> fileCustoms) {

        for(FileCustom c :fileCustoms){
            c.setBackUp(true);
        }
    }

    /**
     * 获取未备份的数量
     * */
    public List<FileCustom> fetchUnBackUpFiles(int count){
        int num = 0;
        List<FileCustom> fetchfiles =new ArrayList<>();
        for(FileCustom fileCustom:files){
            if(num>=count) break;

            if(!fileCustom.getBackUp()) {
                fetchfiles.add(fileCustom);
                num++;
            }
        }
        return fetchfiles;
    }


}
