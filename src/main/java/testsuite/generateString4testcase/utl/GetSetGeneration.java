package testsuite.generateString4testcase.utl;

/**
 * @Description:
 * @auther phantom
 * @create 2019-12-09 下午2:26
 */
public class GetSetGeneration {

    public static String generateGetSetMethod(String object){
        String str = "";
        if (!object.equals("vehicle")){
            String upperCase = object.substring(0, 1).toUpperCase() + object.substring(1);
            String type = getType(object);
            str += "    private " + type + " " +object + ";\n";
            str += "    public " + type + " get" + upperCase + "() { \n";
            str += "        return " + object + "; \n    } \n";
            str += "    public void set" + upperCase + "(" + type + " " + object + ") { \n";
            str += "        this." + object + " = " + object + "; \n    } \n";
            return str;
        }else {
            str += "    public enum Vehicle { \n         Car, Motorcycle, Bike, E_Bike \n     } \n";
            str += "    private Vehicle vehicle;\n";
            String upperCase = object.substring(0, 1).toUpperCase() + object.substring(1);
            String type = getType(object);
            str += "    public " + type + " get" + upperCase + "() { \n";
            str += "        return " + object + "; \n    } \n";
            str += "    public void set" + upperCase + "(" + type + " " + object + ") { \n";
            str += "        this." + object + " = " + object + "; \n    } \n";
            return str;

        }
    }


    public static String generateGetSetMethod4Follow(String object){
        String str = "";
        if (!object.equals("vehicle") && !object.equals("family") && !object.equals("favoriteSports")
           && !object.equals("favoriteFoods")){
            String upperCase = object.substring(0, 1).toUpperCase() + object.substring(1);
            String type = getType(object);
            str += "    private " + type + " " +object + ";\n";
            str += "    public " + type + " get" + upperCase + "() { \n";
            str += "        return " + object + "; \n    } \n";
            str += "    public void set" + upperCase + "(" + type + " " + object + ") { \n";
            str += "        this." + object + " = " + object + "; \n    } \n";
            return str;
        }else if (object.equals("vehicle")){
            str += "    public enum Vehicle { \n         Car, Motorcycle, Bike, E_Bike \n     } \n";
            str += "    private Vehicle vehicle;\n";
            String upperCase = object.substring(0, 1).toUpperCase() + object.substring(1);
            String type = getType(object);
            str += "    public " + type + " get" + upperCase + "() { \n";
            str += "        return " + object + "; \n    } \n";
            str += "    public void set" + upperCase + "(" + type + " " + object + ") { \n";
            str += "        this." + object + " = " + object + "; \n    } \n";
            return str;
        }else if (object.equals("family")){
            String upperCase1 = "Family1";
            String type1 = getType(object);
            str += "    private " + type1 + " " + "family1" + ";\n";
            str += "    public " + type1 + " get" + upperCase1 + "() { \n";
            str += "        return " + "family1" + "; \n    } \n";
            str += "    public void set" + upperCase1 + "(" + type1 + " " + "family1" + ") { \n";
            str += "        this." + "family1" + " = " + "family1" + "; \n    } \n";
            String upperCase2 = "Family2";
            String type2 = getType(object);
            str += "    private " + type2 + " " + "family2" + ";\n";
            str += "    public " + type2 + " get" + upperCase2 + "() { \n";
            str += "        return " + "family2" + "; \n    } \n";
            str += "    public void set" + upperCase2 + "(" + type2 + " " + "family2" + ") { \n";
            str += "        this." + "family2" + " = " + "family2" + "; \n    } \n";
            return str;
        }else if (object.equals("favoriteSports")){
            String upperCase1 = "FavoriteSports1";
            String type1 = getType(object);
            str += "    private " + type1 + " " + "favoriteSports1" + ";\n";
            str += "    public " + type1 + " get" + upperCase1 + "() { \n";
            str += "        return " + "favoriteSports1" + "; \n    } \n";
            str += "    public void set" + upperCase1 + "(" + type1 + " " + "favoriteSports1" + ") { \n";
            str += "        this." + "favoriteSports1" + " = " + "favoriteSports1" + "; \n    } \n";
            String upperCase2 = "FavoriteSports2";
            String type2 = getType(object);
            str += "    private " + type2 + " " + "favoriteSports2" + ";\n";
            str += "    public " + type2 + " get" + upperCase2 + "() { \n";
            str += "        return " + "favoriteSports2" + "; \n    } \n";
            str += "    public void set" + upperCase2 + "(" + type2 + " " + "favoriteSports2" + ") { \n";
            str += "        this." + "favoriteSports2" + " = " + "favoriteSports2" + "; \n    } \n";
            return str;
        }else {
            String upperCase1 = "FavoriteFoods1";
            String type1 = getType(object);
            str += "    private " + type1 + " " + "favoriteFoods1" + ";\n";
            str += "    public " + type1 + " get" + upperCase1 + "() { \n";
            str += "        return " + "favoriteFoods1" + "; \n    } \n";
            str += "    public void set" + upperCase1 + "(" + type1 + " " + "favoriteFoods1" + ") { \n";
            str += "        this." + "favoriteFoods1" + " = " + "favoriteFoods1" + "; \n    } \n";
            String upperCase2 = "FavoriteFoods2";
            String type2 = getType(object);
            str += "    private " + type2 + " " + "favoriteFoods2" + ";\n";
            str += "    public " + type2 + " get" + upperCase2 + "() { \n";
            str += "        return " + "favoriteFoods2" + "; \n    } \n";
            str += "    public void set" + upperCase2 + "(" + type2 + " " + "favoriteFoods2" + ") { \n";
            str += "        this." + "favoriteFoods2" + " = " + "favoriteFoods2" + "; \n    } \n";
            return str;
        }
    }



    private static String getType(String object){
        switch (object){
            case "height":
                return "float";
            case "property":
                return "double";
            case "salary":
                return "short";
            case "age":
                return "byte";
            case "maxExpense":
                return "int";
            case "id":
                return "long";
            case "isMarried":
                return "boolean";
            case "sex":
                return "char";
            case "birthday":
                return "Date";
            case "name":
                return "String";
            case "vehicle":
                return "Vehicle";
            case "family":
                return "Map<String,String>";
            case "favoriteSports":
                return "List<String>";
            case "favoriteFoods":
                return "Set<String>";
            default:
                System.out.println("error");
                return "null";
        }
    }

    public static void main(String[] args) {

        System.out.println(GetSetGeneration.generateGetSetMethod4Follow("favoriteFoods"));
    }
}
