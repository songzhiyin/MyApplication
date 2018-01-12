package com.szy.myapplication.Entity;

import com.szy.myapplication.Base.BaseEntity;

import java.util.List;

/**
 * Created by Administrator on 2018/1/12 0012.
 */

public class NetMessageEntity extends BaseEntity {

    /**
     * data : {"list":[{"id":71,"messageDetail":"您有一条待审批的物资采购计划单，单据PL201801110001，请及时审批","isRead":false,"type":"物资采购计划单","receiptId":167,"sendMessageTimeLabel":"2018-01-11 15:51:50"},{"id":70,"messageDetail":"您有一条待审批的物资采购计划单，单据PL201801110001，请及时审批","isRead":false,"type":"物资采购计划单","receiptId":167,"sendMessageTimeLabel":"2018-01-11 15:51:50"},{"id":69,"messageDetail":"您有一条待审批的物资采购计划单，单据PL201801110001，请及时审批","isRead":false,"type":"物资采购计划单","receiptId":167,"sendMessageTimeLabel":"2018-01-11 15:51:50"},{"id":67,"messageDetail":"您有一条待审批的供应商审批单，单据SER18000108，请及时审批","isRead":true,"type":"供应商审批单","receiptId":68,"sendMessageTimeLabel":"2018-01-06 04:55:50"},{"id":65,"messageDetail":"您有一条待审批的供应商审批单，单据SER18000108，请及时审批","isRead":false,"type":"供应商审批单","receiptId":67,"sendMessageTimeLabel":"2018-01-06 04:46:38"},{"id":64,"messageDetail":"您有一条待审批的物资采购需求单，单据PR201801040004，请及时审批","isRead":false,"type":"物资采购需求单","receiptId":25,"sendMessageTimeLabel":"2018-01-04 15:55:10"},{"id":63,"messageDetail":"您有一条待审批的物资采购需求单，单据PR201801040004，请及时审批","isRead":false,"type":"物资采购需求单","receiptId":25,"sendMessageTimeLabel":"2018-01-04 09:38:13"},{"id":62,"messageDetail":"您有一条待审批的物资采购计划单，单据PL201801040014，请及时审批","isRead":false,"type":"物资采购计划单","receiptId":116,"sendMessageTimeLabel":"2018-01-04 09:35:43"},{"id":61,"messageDetail":"您有一条待审批的物资采购计划单，单据PL201801040014，请及时审批","isRead":false,"type":"物资采购计划单","receiptId":116,"sendMessageTimeLabel":"2018-01-04 09:35:32"},{"id":60,"messageDetail":"您有一条待审批的服务供应商审批单，单据SER18000093，请及时审批","isRead":false,"type":"服务供应商审批单","receiptId":38,"sendMessageTimeLabel":"2018-01-04 09:32:07"}],"total":53,"pageNumber":1,"pageSize":10}
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
         * list : [{"id":71,"messageDetail":"您有一条待审批的物资采购计划单，单据PL201801110001，请及时审批","isRead":false,"type":"物资采购计划单","receiptId":167,"sendMessageTimeLabel":"2018-01-11 15:51:50"},{"id":70,"messageDetail":"您有一条待审批的物资采购计划单，单据PL201801110001，请及时审批","isRead":false,"type":"物资采购计划单","receiptId":167,"sendMessageTimeLabel":"2018-01-11 15:51:50"},{"id":69,"messageDetail":"您有一条待审批的物资采购计划单，单据PL201801110001，请及时审批","isRead":false,"type":"物资采购计划单","receiptId":167,"sendMessageTimeLabel":"2018-01-11 15:51:50"},{"id":67,"messageDetail":"您有一条待审批的供应商审批单，单据SER18000108，请及时审批","isRead":true,"type":"供应商审批单","receiptId":68,"sendMessageTimeLabel":"2018-01-06 04:55:50"},{"id":65,"messageDetail":"您有一条待审批的供应商审批单，单据SER18000108，请及时审批","isRead":false,"type":"供应商审批单","receiptId":67,"sendMessageTimeLabel":"2018-01-06 04:46:38"},{"id":64,"messageDetail":"您有一条待审批的物资采购需求单，单据PR201801040004，请及时审批","isRead":false,"type":"物资采购需求单","receiptId":25,"sendMessageTimeLabel":"2018-01-04 15:55:10"},{"id":63,"messageDetail":"您有一条待审批的物资采购需求单，单据PR201801040004，请及时审批","isRead":false,"type":"物资采购需求单","receiptId":25,"sendMessageTimeLabel":"2018-01-04 09:38:13"},{"id":62,"messageDetail":"您有一条待审批的物资采购计划单，单据PL201801040014，请及时审批","isRead":false,"type":"物资采购计划单","receiptId":116,"sendMessageTimeLabel":"2018-01-04 09:35:43"},{"id":61,"messageDetail":"您有一条待审批的物资采购计划单，单据PL201801040014，请及时审批","isRead":false,"type":"物资采购计划单","receiptId":116,"sendMessageTimeLabel":"2018-01-04 09:35:32"},{"id":60,"messageDetail":"您有一条待审批的服务供应商审批单，单据SER18000093，请及时审批","isRead":false,"type":"服务供应商审批单","receiptId":38,"sendMessageTimeLabel":"2018-01-04 09:32:07"}]
         * total : 53
         * pageNumber : 1
         * pageSize : 10
         */

        private int total;
        private int pageNumber;
        private int pageSize;
        private List<ListBean> list;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }

        public int getPageNumber() {
            return pageNumber;
        }

        public void setPageNumber(int pageNumber) {
            this.pageNumber = pageNumber;
        }

        public int getPageSize() {
            return pageSize;
        }

        public void setPageSize(int pageSize) {
            this.pageSize = pageSize;
        }

        public List<ListBean> getList() {
            return list;
        }

        public void setList(List<ListBean> list) {
            this.list = list;
        }

        public static class ListBean {
            /**
             * id : 71
             * messageDetail : 您有一条待审批的物资采购计划单，单据PL201801110001，请及时审批
             * isRead : false
             * type : 物资采购计划单
             * receiptId : 167
             * sendMessageTimeLabel : 2018-01-11 15:51:50
             */

            private int id;
            private String messageDetail;
            private boolean isRead;
            private String type;
            private int receiptId;
            private String sendMessageTimeLabel;

            public int getId() {
                return id;
            }

            public void setId(int id) {
                this.id = id;
            }

            public String getMessageDetail() {
                return messageDetail;
            }

            public void setMessageDetail(String messageDetail) {
                this.messageDetail = messageDetail;
            }

            public boolean isIsRead() {
                return isRead;
            }

            public void setIsRead(boolean isRead) {
                this.isRead = isRead;
            }

            public String getType() {
                return type;
            }

            public void setType(String type) {
                this.type = type;
            }

            public int getReceiptId() {
                return receiptId;
            }

            public void setReceiptId(int receiptId) {
                this.receiptId = receiptId;
            }

            public String getSendMessageTimeLabel() {
                return sendMessageTimeLabel;
            }

            public void setSendMessageTimeLabel(String sendMessageTimeLabel) {
                this.sendMessageTimeLabel = sendMessageTimeLabel;
            }
        }
    }
}
