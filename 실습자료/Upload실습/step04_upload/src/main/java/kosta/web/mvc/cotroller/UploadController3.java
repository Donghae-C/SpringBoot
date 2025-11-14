package kosta.web.mvc.cotroller;



import kosta.web.mvc.dto.FileVO1;
import kosta.web.mvc.dto.FileVO2;
import kosta.web.mvc.dto.FileVO3;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

@Controller
@Slf4j
public class UploadController3 {

	@Value("${file.upload-dir}")
	private String uploadDir;


	@RequestMapping("/uploadForm3")
	public void formFile() {}

	@PostMapping("/upload3")
	public ModelAndView saveFile(FileVO3 vo) {
		String fileName =  vo.getUploadFile().getOriginalFilename();
		log.info("uploadDir = {}", uploadDir);

		String resultStr = "";

		File isDir = new File(uploadDir);
		if (!isDir.isDirectory()) {
			isDir.mkdirs();
		}

		try {
			File f = new File(uploadDir+"/" + fileName);
			if (f.exists()) {
				resultStr += fileName + " : 파일이 이미 존재해요!!<br>";
			} else {
				vo.getUploadFile().transferTo(f);
				resultStr += fileName + " : 파일이 저장되었어요!!<br>";
			}
		} catch (IOException e) {
			e.printStackTrace();
			resultStr += fileName + " : 오류가 발생했어요!!";
		}

	   ModelAndView mav = new ModelAndView();
		mav.setViewName("uploadForm3");
		mav.addObject("msg", resultStr);
		return mav;
	}
}

