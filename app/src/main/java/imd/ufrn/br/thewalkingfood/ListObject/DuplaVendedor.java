package imd.ufrn.br.thewalkingfood.ListObject;

/**
 * Created by Kamilla on 11/18/2016.
 */

public class DuplaVendedor {

    private String idA;
    private String photourlA;
    private String numberA;
    private String distanceA;

    private String idB;
    private String photourlB;
    private String numberB;
    private String distanceB;

    private Vendedor A;
    private Vendedor B;


    public DuplaVendedor(){

    }


    public DuplaVendedor(String _idA, String _photourlA, String _numberA, String _distanceA, String _idB, String _photourlB, String _numberB, String _distanceB){

        this.idA = _idA;
        this.photourlA = _photourlA;
        this.numberA = _numberA;
        this.distanceA = _distanceA;

        this.idB = _idB;
        this.photourlB = _photourlB;
        this.numberB = _numberB;
        this.distanceB = _distanceB;
    }

    public DuplaVendedor(Vendedor A, Vendedor B){

        this.idA = A.getId();
        this.photourlA = A.getPhotourl();
        this.numberA = A.getNumber();
        this.distanceA = A.getDistance();

        this.idB = B.getId();
        this.photourlB = B.getPhotourl();
        this.numberB = B.getNumber();
        this.distanceB = B.getDistance();
    }


    public String getIdA() {
        return idA;
    }

    public void setIdA(String idA) {
        this.idA = idA;
    }

    public String getPhotourlA() {
        return photourlA;
    }

    public void setPhotourlA(String photourlA) {
        this.photourlA = photourlA;
    }

    public String getNumberA() {
        return numberA;
    }

    public void setNumberA(String numberA) {
        this.numberA = numberA;
    }

    public String getDistanceA() {
        return distanceA;
    }

    public void setDistanceA(String distanceA) {
        this.distanceA = distanceA;
    }

    public String getIdB() {
        return idB;
    }

    public void setIdB(String idB) {
        this.idB = idB;
    }

    public String getPhotourlB() {
        return photourlB;
    }

    public void setPhotourlB(String photourlB) {
        this.photourlB = photourlB;
    }

    public String getNumberB() {
        return numberB;
    }

    public void setNumberB(String numberB) {
        this.numberB = numberB;
    }

    public String getDistanceB() {
        return distanceB;
    }

    public void setDistanceB(String distanceB) {
        this.distanceB = distanceB;
    }
}


