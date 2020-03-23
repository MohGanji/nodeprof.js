/* *****************************************************************************
 * Copyright 2018 Dynamic Analysis Group, Università della Svizzera Italiana (USI)
 * Copyright (c) 2018, 2020, Oracle and/or its affiliates. All rights reserved.
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
 * *****************************************************************************/
package ch.usi.inf.nodeprof.jalangi.factory;

import com.oracle.truffle.api.frame.VirtualFrame;
import com.oracle.truffle.api.instrumentation.EventContext;
import com.oracle.truffle.api.interop.InteropException;
import com.oracle.truffle.api.interop.InteropLibrary;
import com.oracle.truffle.api.nodes.Node;
import com.oracle.truffle.api.object.DynamicObject;

import ch.usi.inf.nodeprof.handlers.BaseEventHandlerNode;
import ch.usi.inf.nodeprof.handlers.PropertyWriteEventHandler;

public class PutFieldFactory extends AbstractFactory {

    public PutFieldFactory(Object jalangiAnalysis, DynamicObject pre,
                    DynamicObject post) {
        super("putField", jalangiAnalysis, pre, post);
    }

    @Override
    public BaseEventHandlerNode create(EventContext context) {
        return new PropertyWriteEventHandler(context) {
            @Node.Child private InteropLibrary preDispatch = (pre == null) ? null : createDispatchNode();
            @Node.Child private InteropLibrary postDispatch = (post == null) ? null : createDispatchNode();

            @Override
            public void executePre(VirtualFrame frame, Object[] inputs) throws InteropException {
                if (pre != null) {
                    if (!this.isGlobal(inputs)) {
                        wrappedDispatchExecution(this, preDispatch, pre, getSourceIID(), getReceiver(inputs), getProperty(), getValue(inputs), true, isOpAssign());
                    }
                }
            }

            @Override
            public void executePost(VirtualFrame frame, Object result,
                            Object[] inputs) throws InteropException {
                if (post != null) {
                    if (!this.isGlobal(inputs)) {
                        wrappedDispatchExecution(this, postDispatch, post, getSourceIID(), getReceiver(inputs), getProperty(), getValue(inputs), true, isOpAssign());
                    }
                }
            }
        };
    }
}
