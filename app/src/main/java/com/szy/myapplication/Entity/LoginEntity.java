package com.szy.myapplication.Entity;


import com.szy.myapplication.Base.BaseEntity;

import java.util.List;

/**
 * Created by songzhiiyn on 2017/9/25.
 * 登录接口返回的实体类
 */

public class LoginEntity extends BaseEntity {


    /**
     * data : {"userId":1,"username":"Android","sessionId":"158625f3b3904af88fb1db424864a734","hasSignature":true,"departmentName":"技术部","avatar":"","signature":"/file/a3f502df3a634461af18d8ef77114bc5/imageusericonsignature.jpg","permissions":["*"]}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * userId : 1
         * username : Android
         * sessionId : 158625f3b3904af88fb1db424864a734
         * hasSignature : true
         * departmentName : 技术部
         * avatar :
         * signature : /file/a3f502df3a634461af18d8ef77114bc5/imageusericonsignature.jpg
         * permissions : ["*"]
         */

        private int userId;
        private String username;
        private String sessionId;
        private boolean hasSignature;
        private String departmentName;
        private String avatar;
        private int companyId;
        private String signature;
        private List<String> permissions;

        public int getCompanyId() {
            return companyId;
        }

        public void setCompanyId(int companyId) {
            this.companyId = companyId;
        }

        public int getUserId() {
            return userId;
        }

        public void setUserId(int userId) {
            this.userId = userId;
        }

        public String getUsername() {
            return username;
        }

        public void setUsername(String username) {
            this.username = username;
        }

        public String getSessionId() {
            return sessionId;
        }

        public void setSessionId(String sessionId) {
            this.sessionId = sessionId;
        }

        public boolean isHasSignature() {
            return hasSignature;
        }

        public void setHasSignature(boolean hasSignature) {
            this.hasSignature = hasSignature;
        }

        public String getDepartmentName() {
            return departmentName;
        }

        public void setDepartmentName(String departmentName) {
            this.departmentName = departmentName;
        }

        public String getAvatar() {
            return avatar;
        }

        public void setAvatar(String avatar) {
            this.avatar = avatar;
        }

        public String getSignature() {
            return signature;
        }

        public void setSignature(String signature) {
            this.signature = signature;
        }

        public List<String> getPermissions() {
            return permissions;
        }

        public void setPermissions(List<String> permissions) {
            this.permissions = permissions;
        }
    }
}
