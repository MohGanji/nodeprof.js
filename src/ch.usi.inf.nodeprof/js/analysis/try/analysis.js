/*******************************************************************************
 * Copyright 2018 Dynamic Analysis Group, Università della Svizzera Italiana (USI)
 * Copyright (c) 2018, Oracle and/or its affiliates. All rights reserved.
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *     http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 *******************************************************************************/
//DO NOT INSTRUMENT
(function (sandbox) {
    function MyAnalysis() {

        this.tryPre = function(iid) {
            console.log('tryPre @', iid, "@", J$.iidToLocation(iid))
        }
        this.tryPost = function(iid) {
            console.log('tryPost @', iid, "@", J$.iidToLocation(iid))
        }

        this.throwPre = function(iid) {
            console.log('throwPre @', iid, "@", J$.iidToLocation(iid))
        }
        this.throwPost = function(iid) {
            console.log('throwPost @', iid, "@", J$.iidToLocation(iid))
        } 
    }

    sandbox.analysis = new MyAnalysis();
})(J$);
