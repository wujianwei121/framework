/*
 * Copyright © 2018 Yan Zhenjie.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
package com.example.framwork.noHttp;

import com.example.framwork.noHttp.Bean.BaseResponseBean;

/**
 * <p>Initialize the save parameters.</p>
 * Created by YanZhenjie on 2017/6/14.
 */
public final class NetworkConfig<Entity extends BaseResponseBean> {

    public static Builder newBuilder() {
        return new Builder();
    }

    private final FilterExecuteLinstener filterExecuteLinstener;
    //request 是否加密
    private boolean isEncryption;

    private Class reponseC;

    private NetworkConfig(Builder builder) {
        this.filterExecuteLinstener = builder.filterExecuteLinstener;
        this.isEncryption = builder.isEncryption;
        this.reponseC = builder.reponseC;
    }

    public FilterExecuteLinstener getFilter() {
        return filterExecuteLinstener;
    }

    public Class getReponseC() {
        return reponseC;
    }

    public boolean isEncryption() {
        return isEncryption;
    }

    public final static class Builder {

        private FilterExecuteLinstener filterExecuteLinstener;
        private boolean isEncryption;
        private Class reponseC;

        private Builder() {
            this.filterExecuteLinstener = null;
            this.isEncryption = false;
            this.reponseC = null;
        }

        /**
         * Set global work thread executor.
         */
        public Builder filterExecuteLinstener(FilterExecuteLinstener executor) {
            this.filterExecuteLinstener = executor;
            return this;
        }

        public Builder isEncryption(boolean isEncryption) {
            this.isEncryption = isEncryption;
            return this;
        }

        public Builder reponseClass(Class reponseC) {
            this.reponseC = reponseC;
            return this;
        }

        public NetworkConfig build() {
            return new NetworkConfig(this);
        }
    }

}