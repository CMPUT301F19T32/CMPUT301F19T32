package com.example.myapplication;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MoodTest {

    private Mood createTestMood(){
        Mood mood = new Mood("Happy", "1", "happy", "11:09",
                "2019-11-05", "Single", "puquan", new Geolocation(53.484310, -113.506133));

        return mood;
    }

    @Test
    void testEmotionstr(){
        Mood mood = createTestMood();
        assertEquals(mood.getEmotionstr(),"Happy");

        mood.setEmotionstr("Angry");
        assertEquals(mood.getEmotionstr(),"Angry");

    }

    @Test
    void testEmotionState(){
        Mood mood = createTestMood();
        assertEquals(mood.getEmotionState(),"1");
        mood.setEmotionState("2");
        assertEquals(mood.getEmotionState(),"2");
    }
    
    @Test
    void testReason(){
        Mood mood = createTestMood();
        assertEquals(mood.getReason(),"happy");
        mood.setReason("Angry");
        assertEquals(mood.getReason(),"Angry");
    }
    
    @Test
    void testTime(){
        Mood mood = createTestMood();
        assertEquals(mood.getTime(),"11:09");
        mood.setTime("11:12");
        assertEquals(mood.getTime(),"11:12");
    }
    @Test
    void testDate(){
        Mood mood = createTestMood();
        assertEquals(mood.getDate(),"2019-11-05");
        mood.setDate("2019-11-06");
        assertEquals(mood.getDate(),"2019-11-06");
    }
    @Test
    void testSocialState(){
        Mood mood = createTestMood();
        assertEquals(mood.getSocialState(),"Single");
        mood.setSocialState("Not Single");
        assertEquals(mood.getSocialState(),"Not Single");
    }
    
    @Test
    void testUsername(){
        Mood mood = createTestMood();
        assertEquals(mood.getUsername(),"puquan");
        mood.setUsername("xinyue11");
        assertEquals(mood.getUsername(),"xinyue11");
    }

    @Test
    void testGeolocation(){
        Mood mood = createTestMood();
        double la = 53.486592;
        double lg = -113.502130;
        assertEquals(mood.getGeolocation().getLatitude(),53.484310);
        assertEquals(mood.getGeolocation().getLongitude(),-113.506133);
        Geolocation geolocation = new Geolocation(la,lg);
        mood.setGeolocation(geolocation);
        assertEquals(mood.getGeolocation().getLatitude(),la);
        assertEquals(mood.getGeolocation().getLongitude(),lg);

    }




}


