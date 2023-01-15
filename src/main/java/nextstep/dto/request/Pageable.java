package nextstep.dto.request;

public class Pageable {

    private int page;
    private int size;

    public static Pageable of(int page, int size) {
        return new Pageable(page * size, size);
    }

    private Pageable(int page, int size) {
        this.page = page;
        this.size = size;
    }

    public int getPage() {
        return page;
    }

    public int getSize() {
        return size;
    }
}
