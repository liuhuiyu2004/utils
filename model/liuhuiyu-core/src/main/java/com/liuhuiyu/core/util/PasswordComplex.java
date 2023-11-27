package com.liuhuiyu.core.util;

/**
 * 密码复杂度
 *
 * @author LiuHuiYu
 * @version v1.0.0.0
 * Created DateTime 2023-11-27 9:43
 */
public class PasswordComplex {
    public static final int DEFAULT_PASSWORD_MINIMUM_LENGTH = 8;
    public static final int DEFAULT_PASSWORD_MINIMUM_COMPLEXITY = 3;
    public static final int PASSWORD_MINIMUM_COMPLEXITY = 1;
    public static final int PASSWORD_MAXIMUM_COMPLEXITY = 4;
    private final String password;
    private final int passwordMinLength;
    private final int passwordMinComplexity;

    /**
     * 密码复杂度计算
     *
     * @param password              密码
     * @param passwordMinLength     密码长度
     * @param passwordMinComplexity 密码复杂度
     * @author LiuHuiYu
     * Created DateTime 2023-11-27 19:50
     */
    private PasswordComplex(String password, int passwordMinLength, int passwordMinComplexity) {
        this.password = password;
        this.passwordMinLength = passwordMinLength;
        this.passwordMinComplexity = passwordMinComplexity;
    }

    /**
     * 检查密码长度是否至少为 passwordMinLength 个字符。
     *
     * @return boolean
     * @author LiuHuiYu
     * Created DateTime 2023-11-27 9:52
     */
    public boolean validLength() {
        return password.length() >= passwordMinLength;
    }

    /**
     * 检查密码是否至少包含一个小写字母
     *
     * @return boolean
     * @author LiuHuiYu
     * Created DateTime 2023-11-27 9:53
     */
    public boolean validContainsLowercaseLetter() {
        return password.matches(".*[a-z].*");
    }

    /**
     * 检查密码是否至少包含一个大写字母
     *
     * @return boolean
     * @author LiuHuiYu
     * Created DateTime 2023-11-27 9:53
     */
    public boolean validContainsUppercaseLetter() {
        return password.matches(".*[A-Z].*");
    }

    /**
     * 检查密码是否至少包含一个数字
     *
     * @return boolean
     * @author LiuHuiYu
     * Created DateTime 2023-11-27 9:53
     */
    public boolean validContainsNumber() {
        return password.matches(".*\\d.*");
    }

    /**
     * 检查密码是否至少包含一个特殊字符
     *
     * @return boolean
     * @author LiuHuiYu
     * Created DateTime 2023-11-27 9:53
     */
    public boolean validContainsSpecialCharacter() {
        return password.matches(".*[^a-zA-Z0-9].*");
    }

    /**
     * 获取复杂度指数(0-4)
     *
     * @return int
     * @author LiuHuiYu
     * Created DateTime 2023-11-27 9:59
     */
    public int getComplexity() {
        int complexity = 0;
        if (this.validContainsLowercaseLetter()) {
            complexity++;
        }
        if (this.validContainsUppercaseLetter()) {
            complexity++;
        }
        if (this.validContainsNumber()) {
            complexity++;
        }
        if (this.validContainsSpecialCharacter()) {
            complexity++;
        }
        return complexity;
    }

    /**
     * 符合复杂度的密码
     *
     * @return boolean
     * @author LiuHuiYu
     * Created DateTime 2023-11-27 9:58
     */
    public boolean validPassword() {
        return this.validLength() && this.getComplexity() >= this.passwordMinComplexity;
    }


    /**
     * @author LiuHuiYu
     * @version v1.0.0.0
     * Created DateTime 2023-11-27 10:19
     */
    public static final class Builder {
        private final String password;
        private int passwordMinLength = PasswordComplex.DEFAULT_PASSWORD_MINIMUM_LENGTH;
        private int passwordMinComplexity = PasswordComplex.DEFAULT_PASSWORD_MINIMUM_COMPLEXITY;

        private Builder(String password) {
            this.password = password;
        }

        public static Builder createPasswordComplex(String password) {
            Assert.assertNotNull(password, new RuntimeException("密码不能为null"));
            return new Builder(password);
        }

        public Builder withPasswordMinLength(int passwordMinLength) {
            Assert.assertTrue(passwordMinLength >= 0, new RuntimeException("密码长度不能为负数。"));
            this.passwordMinLength = passwordMinLength;
            return this;
        }

        public Builder withPasswordMinComplexity(int passwordMinComplexity) {
            Assert.assertTrue(passwordMinComplexity >= PasswordComplex.PASSWORD_MINIMUM_COMPLEXITY, new RuntimeException("不能低于最低复杂度"));
            Assert.assertTrue(passwordMinComplexity <= PasswordComplex.PASSWORD_MAXIMUM_COMPLEXITY, new RuntimeException("不能高于最高复杂度"));
            this.passwordMinComplexity = passwordMinComplexity;
            return this;
        }

        public PasswordComplex build() {
            return new PasswordComplex(password, passwordMinLength, passwordMinComplexity);
        }
    }

}
