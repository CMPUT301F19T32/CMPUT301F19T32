package com.example.myapplication;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class MoodTest {

    private Mood createTestMood(){
        Mood mood = new Mood("happy","nihao","2019-12-10 19:20","Alone","puquan","53.484310","-113.506133");
        return mood;
    }



    @Test
    void testEmotionState(){
        Mood mood = createTestMood();
        assertEquals(mood.getEmotionState(),"happy");
        mood.setEmotionState("angry");
        assertEquals(mood.getEmotionState(),"angry");
    }

    @Test
    void testReason(){
        Mood mood = createTestMood();
        assertEquals(mood.getReason(),"nihao");
        mood.setReason("buhao");
        assertEquals(mood.getReason(),"buhao");
    }

    @Test
    void testTime(){
        Mood mood = createTestMood();
        assertEquals(mood.getTime(),"2019-12-10 19:20");
        mood.setTime("2019-12-10 19:23");
        assertEquals(mood.getTime(),"2019-12-10 19:23");
    }

    @Test
    void testSocialState(){
        Mood mood = createTestMood();
        assertEquals(mood.getSocialState(),"Alone");
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
    void testLatitude(){
        Mood mood = createTestMood();
        assertEquals(mood.getLatitude(),"53.484310");
        mood.setLatitude("-113.506133");
        assertEquals(mood.getLatitude(),"-113.506133");
    }

    @Test
    void testLongitude(){
        Mood mood = createTestMood();
        assertEquals(mood.getLongitude(),"-113.506133");
        mood.setLongitude("53.484310");
        assertEquals(mood.getLongitude(),"53.484310");
    }




}


