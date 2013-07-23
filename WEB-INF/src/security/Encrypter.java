package security;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class Encrypter {
    public static final String ALG_MD5= "MD5";
    public static final String ALG_SHA1= "SHA-1";
    public static final String ALG_SHA256= "SHA-256";
    public static final String ALG_SHA384= "SHA-384";
    public static final String ALG_SHA512= "SHA-512";
    
    /**
     * ハッシュ値の計算
     * @param org 元になる文字列
     * @param algorithm ハッシュアルゴリズムの指定(Encrypter.ALG_xxxでアルゴリズムの文字列を取得できる)
     * @return ハッシュ値
     */
    public static String getHash(String org, String algorithm){
        // 文字列とアルゴリズムが指定されていることを確認
        if ((org == null)||(algorithm == null)){
            return null;
        }
        
        // メッセージダイジェストを取得
        MessageDigest md= null;
        try{
            md= MessageDigest.getInstance(algorithm);
        }
        catch(NoSuchAlgorithmException e){
            return null;
        }
        
        md.reset();
        md.update(org.getBytes());
        byte[] hash= md.digest();
        
        // ハッシュ値の計算
        StringBuffer sb= new StringBuffer();
        int cnt= hash.length;
        for(int i= 0; i< cnt; i++){
            sb.append(Integer.toHexString( (hash[i]>> 4) & 0x0F ) );
            sb.append(Integer.toHexString( hash[i] & 0x0F ) );
        }
        return sb.toString();
    }
}
