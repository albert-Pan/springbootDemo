package com.pw.example.demo4.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

//@Setter
//@Getter
public class UserInfo extends BaseInfo implements Serializable {
        private long uid;
        private String username;
        private String password;
        private String credentialsSalt="TAG";

        public long getUid() {
                return uid;
        }

        public void setUid(long uid) {
                this.uid = uid;
        }

        public String getUsername() {
                return username;
        }

        public void setUsername(String username) {
                this.username = username;
        }

        public String getPassword() {
                return password;
        }

        public void setPassword(String password) {
                this.password = password;
        }

        public String getCredentialsSalt() {
                return credentialsSalt;
        }

        public void setCredentialsSalt(String credentialsSalt) {
                this.credentialsSalt = credentialsSalt;
        }
}
