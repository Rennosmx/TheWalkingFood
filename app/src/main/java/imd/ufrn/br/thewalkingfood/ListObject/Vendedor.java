package imd.ufrn.br.thewalkingfood.ListObject;

/**
 * Created by Kamilla on 11/18/2016.
 */

public class Vendedor {

    private String id;
    private String photourl;
    private String number;
    private String distance;


    public Vendedor(){

    }

    public Vendedor(String _id, String _photourl, String _number, String _distance){

        this.id = _id;
        this.photourl = _photourl;
        this.number = _number;
        this.distance = _distance;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getPhotourl() {
        return photourl;
    }

    public void setPhotourl(String photourl) {
        this.photourl = photourl;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDistance() {
        return distance;
    }

    public void setDistance(String distance) {
        this.distance = distance;
    }
}
