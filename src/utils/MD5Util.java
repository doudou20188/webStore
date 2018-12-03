package utils;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MD5Util {
    public static String getMD5(String pwd){
        //MD5工具类 用于密码加密， 加密后存储密码不可逆
        String md5hashValue="";

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            StringBuffer sb= new StringBuffer();

            //计算md5数值 0F
            byte[] bytes = pwd.getBytes();
            byte[] digest1 = digest.digest(bytes);
            for (byte b:digest1) {

                int i= b&0x00FF;
                //FF
                //FFFFFFFf

                String s = Integer.toHexString(i );  //加盐

                if (s.length()==1) {
                    sb.append("0");
                }


                //System.out.println(s);
                sb.append(s);
            }


            md5hashValue=sb.toString();
            //System.out.println(md5hashValue);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return  md5hashValue;

    }
    public static String getMD5(String sault, String pwd){

        String md5hashValue="";

        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");
            StringBuffer sb= new StringBuffer();
            //进行加盐
            String value = pwd + "{"+sault+"}";

            //计算md5数值 0F
            byte[] digest1 = digest.digest(value.getBytes());
            for (byte b:digest1) {

                int i= b&0x00FF;

                String s = Integer.toHexString(i);

                if (s.length()==1) {
                    sb.append("0");
                }


                //System.out.println(s);
                sb.append(s);
            }


            md5hashValue=sb.toString();
            //System.out.println(md5hashValue);


        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }


        return  md5hashValue;

    }



    public  static String getFileMD5(String filepath){


        File file =new File(filepath);
        StringBuffer sb=new StringBuffer();
        String md5hashValue="";
        try {
            MessageDigest digest = MessageDigest.getInstance("MD5");

            FileInputStream fileInputStream=new FileInputStream(file);

            byte[] bs=new byte[1024];
            int len =-1;

            while((len=fileInputStream.read(bs, 0, 1024))!=-1){

                digest.update(bs, 0, len);

            }

            //文件的MD5值
            byte[] digest2 = digest.digest();

            for (byte b:digest2) {

                int i= b&0x000000FF;
                //FF
                //FFFFFFFf

                String s = Integer.toHexString(i);  //加盐

                if (s.length()==1) {
                    sb.append("0");
                }


                System.out.println(s);
                sb.append(s);
            }


            md5hashValue=sb.toString();
            System.out.println(md5hashValue);



        } catch (NoSuchAlgorithmException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (IOException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }



        return md5hashValue;

    }
    public static boolean verifyMd5(String encryptedPassword,String rawPassword,String sault){
        String xiaoming = getMD5(sault, rawPassword);

        return encryptedPassword.equals(xiaoming);
    }
    public static void main(String[] a){
        String md5 = getMD5("xiaoming","123456");

        System.out.println(md5);
        System.out.println(verifyMd5("40aa107ee8a10e6cc16b2d621b8576673","123456","xiaoming"));
    }
}
