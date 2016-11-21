package imd.ufrn.br.thewalkingfood.ListObject;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Kamilla on 11/19/2016.
 */

public class Feed implements Comparable<Feed>{

    private String id;
    private String photourl;
    private String feedText;
    private String feedDate;


    public Feed(){

    }

    public Feed(String _photourl, String _feedText, String _feedDate){

        this.photourl = _photourl;
        this.feedText = _feedText;
        this.feedDate = _feedDate;

    }


    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getFeedText() {
        return feedText;
    }

    public void setFeedText(String feedText) {
        this.feedText = feedText;
    }

    public String getFeedDate() {
        return feedDate;
    }

    public void setFeedDate(String feedDate) {
        this.feedDate = feedDate;
    }


    public Date stringToDate(String dateString){
        Date date = null;
        SimpleDateFormat format = new SimpleDateFormat("dd/MM/yyyy");
        try {
            date = format.parse(dateString);

        } catch (ParseException e) {

            e.printStackTrace();
        }
        return date;
    }


    @Override
    public int compareTo(Feed feed) {
        Date thisDate;
        Date otherDate;

        thisDate = stringToDate(this.getFeedDate());
        otherDate = stringToDate(feed.getFeedDate());

        if (thisDate == null || otherDate == null)
            return 0;
        return thisDate.compareTo(otherDate);
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }
}
