package a1;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class Main {
    public static void main(String[] args){
        Integer[] array = {0,1,4,5,6,7};//可用芯片编号
        int num = 4;//需要的芯片个数
        List<List<Integer>> list = getList(array, num);
        for (int i = 0; i < list.size(); i++) {
            System.out.println(list.get(i).toString());
        }

    }

    /**
     * 按照亲和性调度的需求来获得所有可能的组合
     * @param array 可用芯片编号
     * @param num 需要的芯片个数
     * @return 满足亲和性调度的所有组合
     */
    public static List<List<Integer>> getList(Integer[] array,int num){
        List<List<Integer>> result = new ArrayList<List<Integer>>();
        List<Integer> line1 = new ArrayList<Integer>();//记录1号线路的所有节点
        List<Integer> line2 = new ArrayList<Integer>();//记录2号线路的所有节点
        //将数组中两条线路的芯片分离--优化方向：获得线路1的最后一个芯片的索引或线路2第一个芯片的索引
        for (int i = 0; i < array.length; i++) {
            if (array[i]<4){
                line1.add(array[i]);
            }else {
                line2.add(array[i]);
            }
        }
        //根据需要的芯片个数来确定匹配策略
        switch (num){
            case 8:
                if (array.length == 8){
                    result.add(Arrays.asList(array));
                }
                break;
            default:
                int level1 = getLevel(num,line1);
                int level2 = getLevel(num,line2);
                //根据优先级来获得所有符合条件的组合
                if (level1 < level2){
                    getArrays(result,num,line1);
                }else if (level1 == level2 && level1 != 4){
                    getArrays(result,num,line1);
                    getArrays(result,num,line2);
                }else {
                    getArrays(result,num,line2);
                }
                break;
        }
        return result;
    }

    /**
     * 获取每条链路的亲和度等级
     * @param num
     * @param line
     * @return
     */
    public static int getLevel(int num,List<Integer> line){
        int res = 4;
        switch (num){
            case 1:
                    switch (line.size()){
                        case 1:
                            res = 0;
                            break;
                        case 3:
                            res = 1;
                            break;
                        case 2:
                            res = 2;
                            break;
                        case 4:
                            res = 3;
                            break;
                    }
                break;
            case 2:
                switch (line.size()){
                    case 2:
                        res = 0;
                        break;
                    case 4:
                        res = 1;
                        break;
                    case 3:
                        res = 2;
                        break;
                }
                break;
            case 4:
                switch (line.size()){
                    case 4:
                        res = 0;
                        break;
                }
                break;
        }
        return res;
    }

    /**
     * 获取所有满足条件的组合
     * @param result
     * @param num
     * @param line
     */
    public static void getArrays(List<List<Integer>> result,int num,List<Integer> line){
        if (num == 4){
            result.add(line);
            return;
        }
        for (int i = 0; i < line.size(); i++) {
            List<Integer> tmp = new ArrayList<Integer>();
            switch (num){
                case 1:
                    tmp.add(line.get(i));
                    result.add(tmp);
                    break;
                case 2:
                    for (int j = i+1; j < line.size(); j++) {
                        tmp.add(line.get(i));
                        tmp.add(line.get(j));
                        result.add(tmp);
                    }
                    break;
            }
        }

    }
}
