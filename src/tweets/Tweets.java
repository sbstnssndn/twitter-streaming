/*
 * To change this license header, choose License Headers in Project Properties.
 * To change this template file, choose Tools | Templates
 * and open the template in the editor.
 */
package tweets;

import java.io.IOException;
import java.util.HashSet;
import java.util.Set;

import org.apache.commons.io.IOUtils;

import twitter4j.FilterQuery;
import twitter4j.StallWarning;
import twitter4j.Status;
import twitter4j.StatusDeletionNotice;
import twitter4j.StatusListener;
import twitter4j.TwitterStream;
import twitter4j.TwitterStreamFactory;
import twitter4j.User;
import twitter4j.conf.ConfigurationBuilder;
/**
 *
 * @author sebasur4
 */
public class Tweets {
    
    private final TwitterStream twitterStream;
    private final ConfigurationBuilder cb;
    private Set<String> keywords;
    
    private Tweets() {
        this.cb = new ConfigurationBuilder();
        cb.setDebugEnabled(true);
        cb.setOAuthConsumerKey("asdf");
        cb.setOAuthConsumerSecret("asdf");
        cb.setOAuthAccessToken("asdf");
        cb.setOAuthAccessTokenSecret("asdf");
        
        this.twitterStream = new TwitterStreamFactory(cb.build()).getInstance();
        this.keywords = new HashSet<>();
        loadKeywords();
    }
    
    private void loadKeywords() {
        try {
                ClassLoader classLoader = getClass().getClassLoader();
                keywords.addAll(IOUtils.readLines(classLoader.getResourceAsStream("words.dat"), "UTF-8"));
        } catch (IOException e) {
                e.printStackTrace();
        }
    }
    
    public void init() {
        StatusListener listener = new StatusListener() {

            @Override
            public void onException(Exception arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onDeletionNotice(StatusDeletionNotice arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onScrubGeo(long arg0, long arg1) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStatus(Status status) {
                User user = status.getUser();
                
                // gets Username
                String username = status.getUser().getScreenName();
                System.out.println(username);
                String profileLocation = user.getLocation();
                System.out.println(profileLocation);
                long tweetId = status.getId(); 
                System.out.println(tweetId);
                String content = status.getText();
                System.out.println(content +"\n");
            }

            @Override
            public void onTrackLimitationNotice(int arg0) {
                // TODO Auto-generated method stub
            }

            @Override
            public void onStallWarning(StallWarning sw) {
                throw new UnsupportedOperationException("Not supported yet."); //To change body of generated methods, choose Tools | Templates.
            }

        };
        
        FilterQuery fq = new FilterQuery();
    
        //String keywords[] = {"ireland"};
        //fq.track(keywords);
        fq.track(keywords.toArray(new String[0]));
        
        this.twitterStream.addListener(listener);
        this.twitterStream.filter(fq);  
    }
    
    public static void main(String[] args) {
        new Tweets().init();
    }
}