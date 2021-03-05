package com.xiaozhu.washcar.common;

import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.util.StringUtils;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

/**
 * @Description:
 * @Author hans
 * @Date 2020/12/6 14:46
 * @Version 1.0
 */
public class EquipmentStatus implements ApplicationRunner {
    public static Map<String,Integer> equipments = new ConcurrentHashMap<>(1);


    @Override
    public void run(ApplicationArguments args) throws Exception {
        equipments.put("EQUIPMENT-1",0);
    }

    public static Integer getStatus(String equipmentNo){
        if(StringUtils.isEmpty(equipmentNo)){
            equipmentNo = "EQUIPMENT-1";
        }
        return equipments.getOrDefault(equipmentNo,1);
    }

    public static Boolean use(String equipmentNo){
        if(StringUtils.isEmpty(equipmentNo)){
            equipmentNo = "EQUIPMENT-1";
        }
        equipments.put(equipmentNo,1);
        return true;
    }

    public static Boolean release(String equipmentNo){
        if(StringUtils.isEmpty(equipmentNo)){
            equipmentNo = "EQUIPMENT-1";
        }
        equipments.put(equipmentNo,0);
        return true;
    }
}
