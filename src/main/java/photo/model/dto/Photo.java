package photo.model.dto;

import java.sql.Date;

public class Photo {
    private int no;
    private String memberId;
    private String content;
    private String originalFileName;
    private String renamedFileName;
    private int readCount;
    private Date regDate;

    public Photo(int no, String memberId, String content, String originalFileName, String renamedFileName, int readCount, Date regDate) {
        this.no = no;
        this.memberId = memberId;
        this.content = content;
        this.originalFileName = originalFileName;
        this.renamedFileName = renamedFileName;
        this.readCount = readCount;
        this.regDate = regDate;
    }

    public Photo() {
    }

    @Override
    public String toString() {
        return "Photo{" +
                "no=" + no +
                ", memberId='" + memberId + '\'' +
                ", content='" + content + '\'' +
                ", originalFileName='" + originalFileName + '\'' +
                ", renamedFileName='" + renamedFileName + '\'' +
                ", readCount=" + readCount +
                ", regDate=" + regDate +
                '}';
    }

    public int getNo() {
        return no;
    }

    public void setNo(int no) {
        this.no = no;
    }

    public String getMemberId() {
        return memberId;
    }

    public void setMemberId(String memberId) {
        this.memberId = memberId;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getOriginalFileName() {
        return originalFileName;
    }

    public void setOriginalFileName(String originalFileName) {
        this.originalFileName = originalFileName;
    }

    public String getRenamedFileName() {
        return renamedFileName;
    }

    public void setRenamedFileName(String renamedFileName) {
        this.renamedFileName = renamedFileName;
    }

    public int getReadCount() {
        return readCount;
    }

    public void setReadCount(int readCount) {
        this.readCount = readCount;
    }

    public Date getRegDate() {
        return regDate;
    }

    public void setRegDate(Date regDate) {
        this.regDate = regDate;
    }
}
