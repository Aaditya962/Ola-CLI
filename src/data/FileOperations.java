package data;

import java.io.*;
import java.util.Properties;

public class FileOperations {

        //to get the details of the admin account from the file
        public static Properties getProperty(String propertyType){
            Properties properties = new Properties();
            try {
                String file = "D://ola//"+ propertyType + ".properties";
                FileReader reader = new FileReader(file);
                properties.load(reader);
                reader.close();
            }
            catch (IOException exception){
                System.out.println("Properties cannot fetched");
                properties = null;
            }
            return properties;
        }
        //To check if any of the file is empty
        static public boolean isFileEmpty(String fileName) throws IOException {

            FileInputStream stream = null;
            ObjectInputStream objectStream = null;
            try {
                String file = "D:\\ola\\"+ fileName + ".txt";
                stream = new FileInputStream(file);
                objectStream = new ObjectInputStream(stream);
                Object object =  objectStream.readObject();
                if (object == null) return true;
            } catch (IOException | ClassNotFoundException exception){
                return true;
            }
            finally {
                if (objectStream != null) {
                    objectStream.close();
                    stream.close();
                }
            }
            return false;
        }

        //to get the details of from the file
        public static Object getList(String objectType){
            FileInputStream stream = null;
            ObjectInputStream objectStream = null;
            try {
                stream = new FileInputStream("D:\\ola\\"+ objectType +".txt");
                objectStream = new ObjectInputStream(stream);
                return objectStream.readObject();
            }
            catch (IOException | ClassNotFoundException exception){        //IOException,FileNotFoundException
                System.out.println(exception);
            }
            finally {
                try {
                    if (objectStream != null) {
                        objectStream.close();
                        stream.close();
                    }
                }
                catch (IOException exception){
                    System.out.println(exception);
                }
            }
            return null;
        }
        //to write in the file
        public static void writeToFile(String userType, Object list){
            FileOutputStream stream = null;
            ObjectOutputStream objectStream = null;
            try{
                stream = new FileOutputStream("D:\\ola\\"+userType+".txt");
                objectStream = new ObjectOutputStream(stream);
                objectStream.writeObject(list);
            }
            catch(IOException exception){                           //IOException,FileNotFoundException
               System.out.println(exception);
            }
            finally {
                try {
                    if (objectStream != null) {
                        objectStream.close();
                        stream.close();
                    }
                }
                catch (IOException exception){
                   System.out.println(exception);
                }
            }
        }
}
