package servlet.reader;

import java.awt.image.VolatileImage;
import java.io.*;
import java.net.MalformedURLException;
import java.net.Proxy;
import java.net.URL;
import java.net.URLConnection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
 
public class StudyResource {private final static String[] keyWords = {" 养殖 ", " 牧畜 "};
 
    public static void main(String[] args) {List<String> urls = generateUrl();
        int threadCount = 200;
        ExecutorService executor = Executors.newFixedThreadPool(threadCount);
        for (String url :
                urls) {executor.execute(() -> {visitWeb(url);
            });
        }
        executor.shutdown();}
 
    public static List<String> generateUrl(){List<String> urls = new ArrayList<>();
        List<String> baseChar = new ArrayList<>();
        String urlHead = "https://";
        String urlEnd = ".com";
        // 加入数字
        for(int i=0;i<=9;i++){baseChar.add("" + i);
        }
        // 加入大小写字符
        for(char ch = 'a';ch <= 'z';ch++){baseChar.add("" + ch);
        }
        // 生成四位长度的域名
        for (int i = 0; i < baseChar.size(); i++) {for (int j = 0; j < baseChar.size(); j++) {for (int k = 0; k < baseChar.size(); k++) {for (int l = 0; l < baseChar.size(); l++) {//urls.add(urlHead + baseChar.get(i) + baseChar.get(j) + baseChar.get(k) + baseChar.get(l) + urlEnd);
                        String url = urlHead + baseChar.get(i) + baseChar.get(j) + baseChar.get(k) + baseChar.get(l) + urlEnd;
                        urls.add(url);
                    }
                }
            }
        }
        return urls;
    }
 
    public static void saveToFile(String url,String fileName){try (BufferedWriter writer = new BufferedWriter(new FileWriter(fileName + ".txt",true))) {writer.write(url + "\n");
            System.out.println(" 数据已成功写入文件 ");
        } catch (IOException e) {e.printStackTrace();
        }
    }
    public static void visitWeb(String visitUrl){
        try {URL url = new URL(visitUrl);
            URLConnection urlConnection = url.openConnection();
            urlConnection.setConnectTimeout(5000);
            BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(urlConnection.getInputStream()));
            String line;
            while ((line = bufferedReader.readLine()) != null){if(check(line)){System.out.println(" 网址：" + visitUrl + " 发现一个结果！");
                    saveToFile(visitUrl," 学习资料 ");
                    break;
                }
            }
        } catch (MalformedURLException e) {System.out.println("URL 格式不正确:" + visitUrl);
        } catch (IOException e) {System.out.println(" 网络连接异常:" + visitUrl);
        }
    }
    public static boolean check(String line){
        for (String e :
                keyWords) {if(line.contains(e)){return true;}
        }
 
        return false;
    }
}
