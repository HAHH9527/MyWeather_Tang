package com.example.a10248.myweather_tang;

import com.example.a10248.myweather_tang.thread.MyHeWeatherThread;

import org.junit.Test;

import static org.junit.Assert.*;

/**
 * Example local unit test, which will execute on the development machine (host).
 *
 * @see <a href="http://d.android.com/tools/testing">Testing documentation</a>
 */
public class ExampleUnitTest {
    @Test
    public void addition_isCorrect() throws Exception {
        assertEquals(4, 2 + 2);
    }

    @Test
    public void testGetWeatherBean() {
        String str = "{\"HeWeather6\":[{\"basic\":{\"cid\":\"CN101251001\",\"location\":\"岳阳\",\"parent_city\":\"岳阳\",\"admin_area\":\"湖南\",\"cnty\":\"中国\",\"lat\":\"29.37029076\",\"lon\":\"113.13285828\",\"tz\":\"+8.0\"},\"update\":{\"loc\":\"2017-12-19 13:55\",\"utc\":\"2017-12-19 05:55\"},\"status\":\"ok\",\"now\":{\"cloud\":\"0\",\"cond_code\":\"100\",\"cond_txt\":\"晴\",\"fl\":\"3\",\"hum\":\"28\",\"pcpn\":\"0.0\",\"pres\":\"1037\",\"tmp\":\"11\",\"vis\":\"10\",\"wind_deg\":\"60\",\"wind_dir\":\"东北风\",\"wind_sc\":\"微风\",\"wind_spd\":\"10\"},\"daily_forecast\":[{\"cond_code_d\":\"100\",\"cond_code_n\":\"100\",\"cond_txt_d\":\"晴\",\"cond_txt_n\":\"晴\",\"date\":\"2017-12-19\",\"hum\":\"39\",\"mr\":\"07:46\",\"ms\":\"18:30\",\"pcpn\":\"0.0\",\"pop\":\"0\",\"pres\":\"1036\",\"sr\":\"07:16\",\"ss\":\"17:33\",\"tmp_max\":\"12\",\"tmp_min\":\"3\",\"uv_index\":\"4\",\"vis\":\"20\",\"wind_deg\":\"5\",\"wind_dir\":\"北风\",\"wind_sc\":\"微风\",\"wind_spd\":\"6\"},{\"cond_code_d\":\"100\",\"cond_code_n\":\"100\",\"cond_txt_d\":\"晴\",\"cond_txt_n\":\"晴\",\"date\":\"2017-12-20\",\"hum\":\"33\",\"mr\":\"08:33\",\"ms\":\"19:20\",\"pcpn\":\"0.0\",\"pop\":\"0\",\"pres\":\"1036\",\"sr\":\"07:17\",\"ss\":\"17:33\",\"tmp_max\":\"13\",\"tmp_min\":\"3\",\"uv_index\":\"3\",\"vis\":\"20\",\"wind_deg\":\"7\",\"wind_dir\":\"北风\",\"wind_sc\":\"微风\",\"wind_spd\":\"7\"},{\"cond_code_d\":\"100\",\"cond_code_n\":\"100\",\"cond_txt_d\":\"晴\",\"cond_txt_n\":\"晴\",\"date\":\"2017-12-21\",\"hum\":\"35\",\"mr\":\"09:19\",\"ms\":\"20:11\",\"pcpn\":\"0.0\",\"pop\":\"0\",\"pres\":\"1030\",\"sr\":\"07:17\",\"ss\":\"17:34\",\"tmp_max\":\"13\",\"tmp_min\":\"4\",\"uv_index\":\"3\",\"vis\":\"20\",\"wind_deg\":\"357\",\"wind_dir\":\"北风\",\"wind_sc\":\"微风\",\"wind_spd\":\"6\"}],\"lifestyle\":[{\"brf\":\"较舒适\",\"txt\":\"白天虽然天气晴好，但早晚会感觉偏凉，午后舒适、宜人。\",\"type\":\"comf\"},{\"brf\":\"较冷\",\"txt\":\"建议着厚外套加毛衣等服装。年老体弱者宜着大衣、呢外套加羊毛衫。\",\"type\":\"drsg\"},{\"brf\":\"较易发\",\"txt\":\"天凉，昼夜温差较大，较易发生感冒，请适当增减衣服，体质较弱的朋友请注意适当防护。\",\"type\":\"flu\"},{\"brf\":\"较适宜\",\"txt\":\"天气较好，无雨水困扰，较适宜进行各种运动，但因气温较低，在户外运动请注意增减衣物。\",\"type\":\"sport\"},{\"brf\":\"适宜\",\"txt\":\"天气较好，温度适宜，是个好天气哦。这样的天气适宜旅游，您可以尽情地享受大自然的风光。\",\"type\":\"trav\"},{\"brf\":\"中等\",\"txt\":\"属中等强度紫外线辐射天气，外出时建议涂擦SPF高于15、PA+的防晒护肤品，戴帽子、太阳镜。\",\"type\":\"uv\"},{\"brf\":\"较适宜\",\"txt\":\"较适宜洗车，未来一天无雨，风力较小，擦洗一新的汽车至少能保持一天。\",\"type\":\"cw\"},{\"brf\":\"良\",\"txt\":\"气象条件有利于空气污染物稀释、扩散和清除，可在室外正常活动。\",\"type\":\"air\"}]}]}";
        MyHeWeatherThread myHeWeather = new MyHeWeatherThread();
        System.out.println(str);
        WeatherBean weatherBean = myHeWeather.getWeatherBean(str);
        System.out.println("loc is:" + weatherBean.getHeWeather6().get(0).getBasic().getLocation());
        System.out.println("now is:" + weatherBean.getHeWeather6().get(0).getNow().getCond_txt());
    }
}