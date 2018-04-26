package com.szy.myapplication.Entity;

/**
 * Created by Administrator on 2018/3/23 0023.
 */

public class Adete {

    /**
     * Code : 0
     * Result : {"icons":{"loversSelected":"","loversNoSelected":"","opSelected":"","selected":"","noSelected":"","loversOpSelected":""}}
     */

    private int Code;
    private ResultBean Result;

    public int getCode() {
        return Code;
    }

    public void setCode(int Code) {
        this.Code = Code;
    }

    public ResultBean getResult() {
        return Result;
    }

    public void setResult(ResultBean Result) {
        this.Result = Result;
    }

    public static class ResultBean {
        /**
         * icons : {"loversSelected":"","loversNoSelected":"","opSelected":"","selected":"","noSelected":"","loversOpSelected":""}
         */

        private IconsBean icons;

        public IconsBean getIcons() {
            return icons;
        }

        public void setIcons(IconsBean icons) {
            this.icons = icons;
        }

        public static class IconsBean {
            /**
             * loversSelected :
             * loversNoSelected :
             * opSelected :
             * selected :
             * noSelected :
             * loversOpSelected :
             */

            private String loversSelected;
            private String loversNoSelected;
            private String opSelected;
            private String selected;
            private String noSelected;
            private String loversOpSelected;

            public String getLoversSelected() {
                return loversSelected;
            }

            public void setLoversSelected(String loversSelected) {
                this.loversSelected = loversSelected;
            }

            public String getLoversNoSelected() {
                return loversNoSelected;
            }

            public void setLoversNoSelected(String loversNoSelected) {
                this.loversNoSelected = loversNoSelected;
            }

            public String getOpSelected() {
                return opSelected;
            }

            public void setOpSelected(String opSelected) {
                this.opSelected = opSelected;
            }

            public String getSelected() {
                return selected;
            }

            public void setSelected(String selected) {
                this.selected = selected;
            }

            public String getNoSelected() {
                return noSelected;
            }

            public void setNoSelected(String noSelected) {
                this.noSelected = noSelected;
            }

            public String getLoversOpSelected() {
                return loversOpSelected;
            }

            public void setLoversOpSelected(String loversOpSelected) {
                this.loversOpSelected = loversOpSelected;
            }
        }
    }
}
