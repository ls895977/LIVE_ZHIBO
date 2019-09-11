package com.kiwi.phonelive.bean;

import java.util.List;

public class NewAdvertBean {

    /**
     * code : 0
     * msg : 操作成功
     * count : 1
     * data : [{"id":12,"created_at":"2019-07-23 13:20:42","updated_at":"2019-07-25 16:54:31","position_id":3,"upload_url":"http://advert.banana6666.com/storage/admin/2019-07-25/1564044848_5d396e30a7ba2.png","link":"https://www.baidu.com","api_id":7,"is_show":1,"name":"主页下3","size":"300*20","is_web":1,"api":{"id":7,"name":"Jimat","alias_name":"Jimat"}}]
     */

    private int code;
    private String msg;
    private int count;
    private List<DataBean> data;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * id : 12
         * created_at : 2019-07-23 13:20:42
         * updated_at : 2019-07-25 16:54:31
         * position_id : 3
         * upload_url : http://advert.banana6666.com/storage/admin/2019-07-25/1564044848_5d396e30a7ba2.png
         * link : https://www.baidu.com
         * api_id : 7
         * is_show : 1
         * name : 主页下3
         * size : 300*20
         * is_web : 1
         * api : {"id":7,"name":"Jimat","alias_name":"Jimat"}
         */

        private int id;
        private String created_at;
        private String updated_at;
        private int position_id;
        private String upload_url;
        private String link;
        private int api_id;
        private int is_show;
        private String name;
        private String size;
        private int is_web;
        private ApiBean api;

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getCreated_at() {
            return created_at;
        }

        public void setCreated_at(String created_at) {
            this.created_at = created_at;
        }

        public String getUpdated_at() {
            return updated_at;
        }

        public void setUpdated_at(String updated_at) {
            this.updated_at = updated_at;
        }

        public int getPosition_id() {
            return position_id;
        }

        public void setPosition_id(int position_id) {
            this.position_id = position_id;
        }

        public String getUpload_url() {
            return upload_url;
        }

        public void setUpload_url(String upload_url) {
            this.upload_url = upload_url;
        }

        public String getLink() {
            return link;
        }

        public void setLink(String link) {
            this.link = link;
        }

        public int getApi_id() {
            return api_id;
        }

        public void setApi_id(int api_id) {
            this.api_id = api_id;
        }

        public int getIs_show() {
            return is_show;
        }

        public void setIs_show(int is_show) {
            this.is_show = is_show;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getSize() {
            return size;
        }

        public void setSize(String size) {
            this.size = size;
        }

        public int getIs_web() {
            return is_web;
        }

        public void setIs_web(int is_web) {
            this.is_web = is_web;
        }

        public ApiBean getApi() {
            return api;
        }

        public void setApi(ApiBean api) {
            this.api = api;
        }

        public static class ApiBean {
            /**
             * id : 7
             * name : Jimat
             * alias_name : Jimat
             */

            private int id;
            private String name;
            private String alias_name;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getName() {
                return name;
            }

            public void setName(String name) {
                this.name = name;
            }

            public String getAlias_name() {
                return alias_name;
            }

            public void setAlias_name(String alias_name) {
                this.alias_name = alias_name;
            }
        }
    }
}
