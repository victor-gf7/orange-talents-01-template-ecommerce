package br.com.zup.freemarket.uploadimage;

import org.springframework.web.multipart.MultipartFile;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class NewImageRequest {

    @NotNull
    @Size(min = 1)
    private List<MultipartFile> files = new ArrayList<>();

    public NewImageRequest(@NotNull @Size(min = 1)List<MultipartFile> files) {
        this.files = files;
    }

    public List<MultipartFile> getFiles() {
        return files;
    }
}
