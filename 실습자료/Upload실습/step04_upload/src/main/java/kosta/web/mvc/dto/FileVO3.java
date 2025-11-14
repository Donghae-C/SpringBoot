package kosta.web.mvc.dto;

import lombok.Getter;
import lombok.Setter;
import org.springframework.web.multipart.MultipartFile;

@Getter
@Setter
public class FileVO3 {
	private String subject;
	private  MultipartFile uploadFile;
}
