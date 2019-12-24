package testsuite;

import java.util.*;

/**
 * @author GengNing
 * @description Bean
 * @date 2019/11/5
 */
public class Person {
    /** height */
    private float height;


    /** property */
    private double property;

    /** salary */
    private short salary;

    private byte age;

    /** One-time maximum expense */
    private int maxExpense;

    /** ID */
    private long id;

    /** ismarried,true or false */
    private boolean isMarried;

    /** sex */
    private char sex;

    /** birthday */
    private Date birthday ;

    /** name */
    private String name ;

    /** bowned vehicle */
    private Vehicle vehicle ;

    public Vehicle getVehicle() {
        return vehicle;
    }

    public void setVehicle(Vehicle vehicle) {
        this.vehicle = vehicle;
    }

    /** family */
    private Map<String,String> family ;

    /** favourite sports */
    private List<String> favoriteSports ;

    /** favourite food */
    private Set<String> favoriteFoods ;

    public enum Vehicle{
        Car, Motorcycle, Bike, E_Bike
    }

}
