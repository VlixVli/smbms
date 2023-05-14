package cloud.vlix.util;

import lombok.Data;

@Data
public class Page {
    private int index;
    private int pageSize;
    private int totalPage;
}
