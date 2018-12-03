package bean;

/**
 * @Auther: YangTao
 * @Date: 2018/11/13 0013
 */
public class User {
    //定义用户信息
    int uid;
    String username;
    String nickname;
    String password;
    String email;
    String  birthday;
    String updatetime;
    // 邮箱校验状态 staus  当前用户唯一标志 uuid
    int staus;
    String  uuid;

    public User() {
    }

    public User(int uid, String username, String nickname, String password, String email, String birthday, String updatetime, int staus, String uuid) {
        this.uid = uid;
        this.username = username;
        this.nickname = nickname;
        this.password = password;
        this.email = email;
        this.birthday = birthday;
        this.updatetime = updatetime;
        this.staus = staus;
        this.uuid = uuid;
    }

    public int getUid() {
        return uid;
    }

    public void setUid(int uid) {
        this.uid = uid;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getNickname() {
        return nickname;
    }

    public void setNickname(String nickname) {
        this.nickname = nickname;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getBirthday() {
        return birthday;
    }

    public void setBirthday(String birthday) {
        this.birthday = birthday;
    }

    public String getUpdatetime() {
        return updatetime;
    }

    public void setUpdatetime(String updatetime) {
        this.updatetime = updatetime;
    }

    public int getStaus() {
        return staus;
    }

    public void setStaus(int staus) {
        this.staus = staus;
    }

    public String getUuid() {
        return uuid;
    }

    public void setUuid(String uuid) {
        this.uuid = uuid;
    }

    @Override
    public String toString() {
        return "User{" +
                "uid=" + uid +
                ", username='" + username + '\'' +
                ", nickname='" + nickname + '\'' +
                ", password='" + password + '\'' +
                ", email='" + email + '\'' +
                ", birthday='" + birthday + '\'' +
                ", updatetime='" + updatetime + '\'' +
                ", staus=" + staus +
                ", uuid='" + uuid + '\'' +
                '}';
    }
}
