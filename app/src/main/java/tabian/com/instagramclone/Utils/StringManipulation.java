package tabian.com.instagramclone.Utils;

/**
 * Created by j on 2018/4/24.
 */

public class StringManipulation {

    public static String expandUsername(String username){
        return username.replace(".", " ");
    }

    public static String condenseUsername(String username){
        return username.replace(" " , ".");
    }

}
