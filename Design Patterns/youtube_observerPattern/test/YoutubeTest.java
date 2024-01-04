package youtube_observerPattern.test;

import youtube_observerPattern.Produccer;
import youtube_observerPattern.User;
import youtube_observerPattern.Video;

public class YoutubeTest {
    
    public static void main(String[] args) {
        Produccer up = new Produccer("Up");
        up.subscribeBy(new User("Follower"));
    
        new Video("title", up, 10.0);
    }
    // @Test
    // public void testStandradNotification() {
    // }
}
