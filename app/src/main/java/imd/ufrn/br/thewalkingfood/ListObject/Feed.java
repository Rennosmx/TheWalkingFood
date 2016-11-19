package imd.ufrn.br.thewalkingfood.ListObject;

/**
 * Created by Kamilla on 11/19/2016.
 */

public class Feed {

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
}
