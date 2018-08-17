package com.szy.myapplication.Entity;

import java.util.List;

public class BellePhoneEntity {

    /**
     * error : false
     * results : [{"_id":"5b33ccf2421aa95570db5478","createdAt":"2018-06-28T01:44:18.488Z","desc":"2018-06-28","publishedAt":"2018-06-28T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1fsq9iq8ttrj30k80q9wi4.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b32807e421aa95570db5471","createdAt":"2018-06-27T02:05:50.227Z","desc":"2018-06-27","publishedAt":"2018-06-27T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1fsp4iok6o4j30j60optbl.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b31aa33421aa9556d2cc4a7","createdAt":"2018-06-26T10:51:31.60Z","desc":"2018-06-26","publishedAt":"2018-06-26T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1fsoe3k2gkkj30g50niwla.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b2f8847421aa9556b44c666","createdAt":"2018-06-24T20:02:15.413Z","desc":"2018-06-24","publishedAt":"2018-06-25T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1fsmis4zbe7j30sg16fq9o.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b0d6ac0421aa97efda86560","createdAt":"2018-05-29T22:59:12.622Z","desc":"2018-06-02","publishedAt":"2018-06-22T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1frslruxdr1j30j60ok79c.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b27c7aa421aa923c0fbfda0","createdAt":"2018-06-18T22:54:34.199Z","desc":"2018-06-19","publishedAt":"2018-06-21T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1fsfq1k9cb5j30sg0y7q61.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b27c7bf421aa923c0fbfda1","createdAt":"2018-06-18T22:54:55.614Z","desc":"2018-06-20","publishedAt":"2018-06-20T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1fsfq1ykabxj30k00pracv.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b27c7eb421aa923c43fe485","createdAt":"2018-06-18T22:55:39.819Z","desc":"2018-06-22","publishedAt":"2018-06-19T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1fsfq2pwt72j30qo0yg78u.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b2269a6421aa92a5f2a35f9","createdAt":"2018-06-14T21:12:06.463Z","desc":"2018-06-15","publishedAt":"2018-06-15T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1fsb0lh7vl0j30go0ligni.jpg","used":true,"who":"lijinshanmx"},{"_id":"5b1fec10421aa9793930bf99","createdAt":"2018-06-12T23:51:44.815Z","desc":"2018-06-13","publishedAt":"2018-06-14T00:00:00.0Z","source":"web","type":"福利","url":"http://ww1.sinaimg.cn/large/0065oQSqly1fs8tym1e8ej30j60ouwhz.jpg","used":true,"who":"lijinshanmx"}]
     */

    private boolean error;
    private List<ResultsBean> results;

    public boolean isError() {
        return error;
    }

    public void setError(boolean error) {
        this.error = error;
    }

    public List<ResultsBean> getResults() {
        return results;
    }

    public void setResults(List<ResultsBean> results) {
        this.results = results;
    }

    public static class ResultsBean {
        /**
         * _id : 5b33ccf2421aa95570db5478
         * createdAt : 2018-06-28T01:44:18.488Z
         * desc : 2018-06-28
         * publishedAt : 2018-06-28T00:00:00.0Z
         * source : web
         * type : 福利
         * url : http://ww1.sinaimg.cn/large/0065oQSqly1fsq9iq8ttrj30k80q9wi4.jpg
         * used : true
         * who : lijinshanmx
         */


        private String url;

        public String getUrl() {
            return url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}
