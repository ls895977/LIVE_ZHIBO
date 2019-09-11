package com.kiwi.phonelive.bean;

import java.util.List;

public class FriendRequestBean {

    private List<RequestListBean> request_list;

    public List<RequestListBean> getRequest_list() {
        return request_list;
    }

    public void setRequest_list(List<RequestListBean> request_list) {
        this.request_list = request_list;
    }

    public static class RequestListBean {
        /**
         * addtime : 1562121932
         * id : 15
         * msg :
         * status : 0
         * touid : 24830
         * uid : 24829
         * uptime : 0
         * userinfo : {"avatar":"https://kiwiapp.vip/default.jpg","avatar_thumb":"https://kiwiapp.vip/default_thumb.jpg","birthday":"","city":"南京市","coin":"200470","consumption":"519","id":"24829","issuper":"0","level":"5","level_anchor":"1","liang":{"name":"0"},"province":"","sex":"2","signature":"这家伙很懒，什么都没留下","user_nicename":"手机用户1029","user_status":"1","vip":{"type":"0"},"votestotal":"0"}
         */

        private String addtime;
        private String id;
        private String msg;
        private String status;
        private String touid;
        private String uid;
        private String uptime;
        private UserinfoBean userinfo;

        public String getAddtime() {
            return addtime;
        }

        public void setAddtime(String addtime) {
            this.addtime = addtime;
        }

        public String getId() {
            return id;
        }

        public void setId(String id) {
            this.id = id;
        }

        public String getMsg() {
            return msg;
        }

        public void setMsg(String msg) {
            this.msg = msg;
        }

        public String getStatus() {
            return status;
        }

        public void setStatus(String status) {
            this.status = status;
        }

        public String getTouid() {
            return touid;
        }

        public void setTouid(String touid) {
            this.touid = touid;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public String getUptime() {
            return uptime;
        }

        public void setUptime(String uptime) {
            this.uptime = uptime;
        }

        public UserinfoBean getUserinfo() {
            return userinfo;
        }

        public void setUserinfo(UserinfoBean userinfo) {
            this.userinfo = userinfo;
        }

        public static class UserinfoBean {
            /**
             * avatar : https://kiwiapp.vip/default.jpg
             * avatar_thumb : https://kiwiapp.vip/default_thumb.jpg
             * birthday :
             * city : 南京市
             * coin : 200470
             * consumption : 519
             * id : 24829
             * issuper : 0
             * level : 5
             * level_anchor : 1
             * liang : {"name":"0"}
             * province :
             * sex : 2
             * signature : 这家伙很懒，什么都没留下
             * user_nicename : 手机用户1029
             * user_status : 1
             * vip : {"type":"0"}
             * votestotal : 0
             */

            private String avatar;
            private String avatar_thumb;
            private String birthday;
            private String city;
            private String coin;
            private String consumption;
            private String id;
            private String issuper;
            private String level;
            private String level_anchor;
            private LiangBean liang;
            private String province;
            private String sex;
            private String signature;
            private String user_nicename;
            private String user_status;
            private VipBean vip;
            private String votestotal;

            public String getAvatar() {
                return avatar;
            }

            public void setAvatar(String avatar) {
                this.avatar = avatar;
            }

            public String getAvatar_thumb() {
                return avatar_thumb;
            }

            public void setAvatar_thumb(String avatar_thumb) {
                this.avatar_thumb = avatar_thumb;
            }

            public String getBirthday() {
                return birthday;
            }

            public void setBirthday(String birthday) {
                this.birthday = birthday;
            }

            public String getCity() {
                return city;
            }

            public void setCity(String city) {
                this.city = city;
            }

            public String getCoin() {
                return coin;
            }

            public void setCoin(String coin) {
                this.coin = coin;
            }

            public String getConsumption() {
                return consumption;
            }

            public void setConsumption(String consumption) {
                this.consumption = consumption;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getIssuper() {
                return issuper;
            }

            public void setIssuper(String issuper) {
                this.issuper = issuper;
            }

            public String getLevel() {
                return level;
            }

            public void setLevel(String level) {
                this.level = level;
            }

            public String getLevel_anchor() {
                return level_anchor;
            }

            public void setLevel_anchor(String level_anchor) {
                this.level_anchor = level_anchor;
            }

            public LiangBean getLiang() {
                return liang;
            }

            public void setLiang(LiangBean liang) {
                this.liang = liang;
            }

            public String getProvince() {
                return province;
            }

            public void setProvince(String province) {
                this.province = province;
            }

            public String getSex() {
                return sex;
            }

            public void setSex(String sex) {
                this.sex = sex;
            }

            public String getSignature() {
                return signature;
            }

            public void setSignature(String signature) {
                this.signature = signature;
            }

            public String getUser_nicename() {
                return user_nicename;
            }

            public void setUser_nicename(String user_nicename) {
                this.user_nicename = user_nicename;
            }

            public String getUser_status() {
                return user_status;
            }

            public void setUser_status(String user_status) {
                this.user_status = user_status;
            }

            public VipBean getVip() {
                return vip;
            }

            public void setVip(VipBean vip) {
                this.vip = vip;
            }

            public String getVotestotal() {
                return votestotal;
            }

            public void setVotestotal(String votestotal) {
                this.votestotal = votestotal;
            }

            public static class LiangBean {
                /**
                 * name : 0
                 */

                private String name;

                public String getName() {
                    return name;
                }

                public void setName(String name) {
                    this.name = name;
                }
            }

            public static class VipBean {
                /**
                 * type : 0
                 */

                private String type;

                public String getType() {
                    return type;
                }

                public void setType(String type) {
                    this.type = type;
                }
            }
        }
    }
}
