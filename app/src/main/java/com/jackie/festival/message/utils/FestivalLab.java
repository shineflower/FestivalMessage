package com.jackie.festival.message.utils;

import com.jackie.festival.message.bean.Festival;
import com.jackie.festival.message.bean.Message;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Jackie on 2016/2/18.
 * 节日数据
 */
public class FestivalLab {
    private List<Festival> mFestivalList = new ArrayList<>();
    private List<Message>  mMessageList = new ArrayList<>();

    private FestivalLab(){
        mFestivalList.add(new Festival(1, "国庆节"));
        mFestivalList.add(new Festival(2, "中秋节"));
        mFestivalList.add(new Festival(3, "元旦"));
        mFestivalList.add(new Festival(4, "春节"));
        mFestivalList.add(new Festival(5, "端午节"));
        mFestivalList.add(new Festival(6, "七夕节"));
        mFestivalList.add(new Festival(7, "圣诞节"));
        mFestivalList.add(new Festival(8, "儿童节"));

        mMessageList.add(new Message(1, 1,
                "祝福贺新岁，新岁祝平安，安居享乐业，乐业扬新帆，新帆乘大船，大船众人牵，牵手度新年，新年爱无限，无限幸福传，传递爱缠绵，缠绵春永驻，永驻你心愿，心愿全家欢，欢度快乐年，年年有今朝，今朝随心愿！"));
        mMessageList.add(new Message(2, 2,
                "猴年春节到，猴福乐逍遥，一朵跟头云，飘着好运来，祝福大礼包，猴哥送上前。一棵摇钱树，给你有钱花；一座花果山，陪伴你身边；一株美人蕉，爱情美娇娇；一枚人参果，事业结成功。春节快乐！"));
        mMessageList.add(new Message(3, 1,
                "许个心愿，你所有的愿望都能实现；祈祷心声，你所有的希望都能如愿；祝福心曲，你所有的好运都能兑现；祈福心情，你所有的付出都能香甜；祝福你在新的一年里，万事随缘，快乐相连，幸福猴年！"));
        mMessageList.add(new Message(4, 1,
                "迎新年，短信传，千家万户笑开颜；兴致高，情无限，辞旧迎新爱相牵；事业旺，家美满，大街小巷新衣穿；友相聚，结良缘，至爱亲朋心相连；敬长辈，爱少年，欢天喜地不夜天；身体棒，多赚钱，新年快乐到永远！"));
        mMessageList.add(new Message(5, 1,
                "猴年春节来，问候祝福到：愿你“颜值”如“宝宝”靓；愿你工作如“创客”“脑洞大开”；愿你快快乐乐“任性”的有钱花随便花，永远不做“剁手党”；愿你新年新气象“获得感”犹如春花秋实“互联网+”“网红”，呵呵“主要看气质”，我看好你。祝大吉大利。"));
        mMessageList.add(new Message(6, 1,
                "金猴年驾到，美猴王送福：愿你猴年身体猴灵猴灵的，挣钱猴多猴多的，心情猴好猴好的，运气猴顺猴顺的，吃睡猴香猴香的，爱情猴甜猴甜的。总之，愿你这猴年猴人猴起之秀，猴模猴样猴福无穷，一切都猴美猴美的！"));
        mMessageList.add(new Message(7, 1,
                "金猴来，春节到，带着喜庆和欢笑；生活好，步步高，烟花爆竹添热闹；定目标，加力跑，欢天喜地乐淘淘；重环保，健康牢，大红灯笼高高照；事业稳，看今朝，安居乐业心美妙；祝福到，问声好，一年四季有情调！"));
        mMessageList.add(new Message(8, 1,
                "终于忙里偷闲，迎来新的一年，好运与你团圆，幸福和你相牵，快乐喜结良缘，健康长寿连绵，吉祥如意点赞，精神抖擞饱满，祝福现在送上：望你开心快乐，一切尽在无言，你若发回红包，我将喜地欢天！"));
        mMessageList.add(new Message(9, 1,
                "写幅对联，书写着新春的喜庆；剪幅窗花，粘贴出新年的温馨；点盏花灯，祝福一年美好的前程；燃串爆竹，炸响新春幸福的美景；送个祝福，愿你洋洋得意好心情；送你祝贺，愿你每天开心快乐欣欣向荣！"));
        mMessageList.add(new Message(10, 1,
                "乡愁，是什么？余光中先生说，长大后，乡愁是一张窄窄的船票。游子说，长大后，乡愁是一张红色或蓝色的车票。过年了，无论票多么难买，无论你在路上，在车上，在家里，或在哪里，我都要送上最真挚的祝福：愿你新年快乐，合家幸福，生活美满，吉祥如意！"));
    }

    private static FestivalLab mInstance;

    public static FestivalLab getInstance() {
        if (mInstance == null) {
            synchronized (FestivalLab.class) {
                if (mInstance == null) {
                    mInstance = new FestivalLab();
                }
            }
        }

        return mInstance;
    }

    public List<Festival> getFestivals() {
        return new ArrayList<Festival>(mFestivalList);
    }

    public Festival getFestivalById(int id) {
        for (Festival festival : mFestivalList) {
            if (festival.getId() == id) {
                return festival;
            }
        }

        return null;
    }

    public List<Message> getMessages() {
        return new ArrayList<Message>(mMessageList);
    }

    public Message getMessageById(int id) {
        for (Message message : mMessageList) {
            if (message.getId() == id) {
                return message;
            }
        }

        return null;
    }

    public List<Message> getMessageByFestivalId(int festivalId) {
        List<Message> messageList = new ArrayList<>();
        for (Message message : mMessageList) {
            if (message.getFestivalId() == festivalId) {
                messageList.add(message);
            }
        }

        return messageList;
    }
}
