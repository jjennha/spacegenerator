import java.util.ArrayList;

import java.util.List;
import java.util.Random;
import java.util.Scanner;

import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.StatusUpdate;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

/**
 * Bot generates space periodically by processing tweets from NASA.
 * @author Jenny Ha
 *
 */
public class SpaceGenerator{
	private Twitter twitter;
	public SpaceGenerator() {
		ConfigurationBuilder cb = new ConfigurationBuilder();
		cb.setDebugEnabled(true).setOAuthConsumerKey("####")
				.setOAuthConsumerSecret("####")
				.setOAuthAccessToken("####")
				.setOAuthAccessTokenSecret("####");
		TwitterFactory tf = new TwitterFactory(cb.build());
		twitter = tf.getInstance();		
	}
	
	public void updateStatus(String str) throws TwitterException{
		Status status = twitter.updateStatus(str);
	}
	public void getUserTweets() throws TwitterException {
		List<Status> userTweets = twitter.getUserTimeline("NASA");
		for(Status tweet: userTweets) {		
			if(tweet.isRetweeted()) continue;
			generateTweet(tweet.getText()); break;
		}
	}
	private void generateTweet(String s) throws TwitterException {
		Random rand = new Random();
		String myTweet = "";
		String newS = s.toString();
		Scanner in = new Scanner(newS);
		System.out.println(myTweet);
		ArrayList<String> starCol = new ArrayList<String>();
		starCol.add("  .");starCol.add("˚  ");starCol.add("+");starCol.add("⋆  ");starCol.add("*");
		starCol.add("✧");starCol.add("✷");starCol.add("✵");starCol.add("✵ ");starCol.add("☼");starCol.add("☾");
		starCol.add("🌔");starCol.add("🌑");starCol.add("🌓");starCol.add("🌕");
		
		ArrayList<String> spaceCol = new ArrayList<String>();
		spaceCol.add(" ");spaceCol.add("   ");spaceCol.add("	  ");
		spaceCol.add("	   ");spaceCol.add("    ");spaceCol.add("\n"+"   ");spaceCol.add("    "+"\n");
		
		while (in.hasNext() && myTweet.length() < 120) {
			String word = in.next();
			myTweet += spaceCol.get(rand.nextInt(spaceCol.size()));
			if (word.length() > starCol.size()) {
				myTweet += starCol.get(rand.nextInt(starCol.size()));
			} else {
				myTweet += starCol.get(rand.nextInt(word.length()));
			}
			myTweet += spaceCol.get(rand.nextInt(spaceCol.size() - 1));
			myTweet += starCol.get(rand.nextInt(word.length()));

			System.out.println(myTweet);
		}
		in.close();
		updateStatus(myTweet); 
	}

	public static void main(String... args) throws TwitterException, InterruptedException {
		while (true) {
			SpaceGenerator bot = new SpaceGenerator();
			bot.getUserTweets();
			
			Thread.sleep(60*180*1000);
		}
	}
}
