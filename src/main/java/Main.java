import java.io.*;
import java.util.*;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class Main {
    private static final Logger log = LoggerFactory.getLogger(Main.class);
    private static final String 원본_폴더_경로 = "C:/Users/dakma/Downloads/완결_양말도깨비";
    private static final boolean 역방향 = true;

    public static void main(String[] args) {
        File 원본_폴더 = new File(원본_폴더_경로);
        String 정렬_방향 = 역방향 ? " (정방향 처리)" : " (정방향)";
        String 새_폴더_경로 = 원본_폴더_경로 + 정렬_방향;
        File 새_폴더 = new File(새_폴더_경로);
        if (!새_폴더.exists()) {
            새_폴더.mkdir();
        }

        File 원본_파일_목록들[] = 원본_폴더.listFiles();
        for (File 원본_파일_목록 : 원본_파일_목록들) {
            String 원본_파일_이름 = 원본_파일_목록.getName();
            String 원본_파일_이름_분할[] = 원본_파일_이름.split("_");
            String 에피소드_번호 = 원본_파일_이름_분할[1];

            String 에피소드_폴더_경로 = 새_폴더 + "/" + 에피소드_번호;
            File 에피소드_폴더 = new File(에피소드_폴더_경로);
            if (!에피소드_폴더.exists()) {
                에피소드_폴더.mkdir();
                log.debug("에피소드 폴더 생성 : {}", 에피소드_번호);
            }
        }
        log.debug("에피소드 폴더 생성 완료\n");

        List<File> 원본_파일_리스트 = Arrays.asList(원본_파일_목록들);
        for (int i = 0; 원본_파일_리스트.size() > i; i++) {
            String 원본_파일_이름 = 원본_파일_리스트.get(i).getName();
            String 원본_파일_이름_분할[] = 원본_파일_이름.split("_");
            String 에피소드_번호 = 원본_파일_이름_분할[1];
            log.debug("원본 이름 : {}", 원본_파일_이름);

            try {
                FileInputStream input = new FileInputStream(원본_파일_리스트.get(i));

                if (역방향) {
                    File 새_폴더_목록들[] = 새_폴더.listFiles();
                    에피소드_번호 = String.valueOf(새_폴더_목록들.length - (Integer.parseInt(에피소드_번호) - 1));
                    if (에피소드_번호.length() == 1) {
                        에피소드_번호 = "00" + 에피소드_번호;
                    } else if (에피소드_번호.length() == 2) {
                        에피소드_번호 = "0" + 에피소드_번호;
                    }
                    log.debug("역순 이름 : {}", 원본_파일_이름_분할[0] + "_" + 에피소드_번호 + "_" + 원본_파일_이름_분할[2]);
                }

                String 최종_파일_경로 = 새_폴더_경로 + "/" + 에피소드_번호 + "/" + 원본_파일_이름_분할[0] + "_" + 에피소드_번호 + "_" + 원본_파일_이름_분할[2];
                log.debug("최종_파일_경로 : {}", 최종_파일_경로 + "\n");
                File 최종_파일 = new File(최종_파일_경로);
                FileOutputStream output = new FileOutputStream(최종_파일);

                byte[] buf = new byte[1024];
                int readData;
                while ((readData = input.read(buf)) > 0) {
                    output.write(buf, 0, readData);
                }
            } catch (Exception e) {
                log.error(e.getMessage());
            }
        }
    }
}
